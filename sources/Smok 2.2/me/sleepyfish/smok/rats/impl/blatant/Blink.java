package me.sleepyfish.smok.rats.impl.blatant;

import java.util.ArrayList;

import maxstats.weave.event.EventSendPacket;
import me.sleepyfish.smok.rats.Rat;
import me.sleepyfish.smok.rats.event.SmokEvent;
import me.sleepyfish.smok.rats.settings.BoolSetting;
import me.sleepyfish.smok.rats.settings.DoubleSetting;
import me.sleepyfish.smok.rats.settings.SpaceSetting;
import me.sleepyfish.smok.utils.misc.Timer;
import me.sleepyfish.smok.utils.entities.Utils;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;

// Class from SMok Client by SleepyFish
public class Blink extends Rat {

    BoolSetting cancelAllPackets;
    BoolSetting cancelBlockPackets;
    BoolSetting sendPacketsOnDisable;

    BoolSetting onGround;
    BoolSetting spawnNpc;
    BoolSetting pulse;

    DoubleSetting pulseDelay;
    DoubleSetting maxSendPackets;

    private boolean allowSpawn;
    private boolean allowListen;
    private ArrayList<Packet<?>> packets;
    private int count;

    Timer timer = new Timer();

    public Blink() {
        super("Blink", Rat.Category.Blatant, "Makes you lag server sided");
    }

    @Override
    public void setup() {
        this.addSetting(this.cancelAllPackets = new BoolSetting("Cancel all packets", false));
        this.addSetting(this.cancelBlockPackets = new BoolSetting("Cancel block packets", false));
        this.addSetting(new SpaceSetting());
        this.addSetting(this.sendPacketsOnDisable = new BoolSetting("Send packets on disable", true));
        this.addSetting(this.maxSendPackets = new DoubleSetting("Max send packets", 300, 1, 500, 1));
        this.addSetting(new SpaceSetting());
        this.addSetting(this.onGround = new BoolSetting("Set on Ground", "Force setting you on ground", false));
        this.addSetting(this.spawnNpc = new BoolSetting("Spawn blink entity", "Also called 'Blink Entity'", true));
        this.addSetting(new SpaceSetting());
        this.addSetting(this.pulse = new BoolSetting("Pulse", "Maybe fixed", false));
        this.addSetting(this.pulseDelay = new DoubleSetting("Pulse delay (MS)", 650.0, 50.0, 2500.0, 50.0));
        this.allowSpawn = false;
        this.allowListen = false;
        this.packets = new ArrayList<>();
    }

    @Override
    public void onEnableEvent() {
        this.enable();
    }

    @Override
    public void onDisableEvent() {
        this.disable();
    }

    @SmokEvent
    public void onPacket(EventSendPacket e) {
        if (Utils.canLegitWork()) {
            if (!this.spawnNpc.isEnabled() && Utils.Npc.getNpc() != null) {
                Utils.Npc.kill();
            }

            Packet<?> packet = e.getPacket();
            if (this.cancelAllPackets.isEnabled()) {
                if (packet != null) {
                    if (this.onGround.isEnabled()) {
                        mc.thePlayer.onGround = true;
                    }

                    this.packets.add(packet);
                    this.count++;
                    e.setCancelled(true);
                }
            } else {
                if (this.cancelBlockPackets.isEnabled() && (this.allowListen && packet instanceof C08PacketPlayerBlockPlacement || packet instanceof C07PacketPlayerDigging)) {
                    if (this.onGround.isEnabled()) {
                        mc.thePlayer.onGround = true;
                    }

                    this.packets.add(packet);
                    this.count++;
                    e.setCancelled(true);
                }

                if (this.allowListen && packet instanceof C03PacketPlayer) {
                    if (this.onGround.isEnabled()) {
                        mc.thePlayer.onGround = true;
                    }

                    this.packets.add(packet);
                    this.count++;
                    e.setCancelled(true);
                }
            }

            if (this.pulse.isEnabled()) {
                if (this.allowListen && packet != null) {
                    if (this.onGround.isEnabled()) {
                        mc.thePlayer.onGround = true;
                    }

                    e.setCancelled(true);
                }

                if (this.timer.delay(this.pulseDelay.getValueToLong())) {
                    if (this.allowSpawn) {
                        this.enable();
                        this.allowSpawn = false;
                    } else {
                        this.disable();
                        this.allowSpawn = true;
                        this.timer.reset();
                    }
                }
            }
        }
    }

    private void enable() {
        this.packets.clear();
        this.allowSpawn = true;
        this.allowListen = true;
        this.count = 0;

        if (this.spawnNpc.isEnabled()) {
            Utils.Npc.spawn();
        }
    }

    private void disable() {
        this.allowSpawn = true;
        this.allowListen = false;

        if (this.spawnNpc.isEnabled()) {
            Utils.Npc.kill();
        }

        if (this.sendPacketsOnDisable.isEnabled() && !this.pulse.isEnabled()) {
            for (Packet<?> p : this.packets) {
                if (this.count > this.maxSendPackets.getValueToInt()) {
                    mc.thePlayer.sendQueue.addToSendQueue(p);
                }
            }

            this.packets.clear();
            this.count = 0;
        }
    }

}