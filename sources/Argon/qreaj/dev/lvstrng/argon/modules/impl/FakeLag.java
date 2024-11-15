package dev.lvstrng.argon.modules.impl;

import com.google.common.collect.Queues;
import dev.lvstrng.argon.event.events.PacketReceiveEvent;
import dev.lvstrng.argon.event.events.PacketSendEvent;
import dev.lvstrng.argon.event.listeners.PacketReceiveListener;
import dev.lvstrng.argon.event.listeners.PacketSendListener;
import dev.lvstrng.argon.event.listeners.PlayerTickListener;
import dev.lvstrng.argon.modules.Category;
import dev.lvstrng.argon.modules.Module;
import dev.lvstrng.argon.modules.setting.Setting;
import dev.lvstrng.argon.modules.setting.settings.BooleanSetting;
import dev.lvstrng.argon.modules.setting.settings.IntSetting;
import dev.lvstrng.argon.utils.Timer;
import net.minecraft.item.Items;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.ClickSlotC2SPacket;
import net.minecraft.network.packet.c2s.play.HandSwingC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.network.packet.s2c.play.ExplosionS2CPacket;
import net.minecraft.util.math.Vec3d;

import java.util.Queue;

public final class FakeLag extends Module implements PlayerTickListener, PacketReceiveListener, PacketSendListener {
    public final Queue<Packet<?>> packets;
    private final IntSetting lagDelay;
    private final BooleanSetting cancelOnElytra;
    public boolean preventOverflow;
    public Vec3d playerPosition;
    public Timer timer;

    public FakeLag() {
        super("Fake Lag", "Makes it impossible to aim at you by creating a lagging effect", 0, Category.COMBAT);
        this.packets = Queues.newConcurrentLinkedQueue();
        this.playerPosition = Vec3d.ZERO;
        this.timer = new Timer();
        this.lagDelay = new IntSetting("Lag Delay", 0.0, 1000.0, 100.0, 1.0);
        this.cancelOnElytra = new BooleanSetting("Cancel on Elytra", false).setDescription("Cancel the lagging effect when you're wearing an elytra");
        this.addSettings(new Setting[]{this.lagDelay, this.cancelOnElytra});
    }

    @Override
    public void onEnable() {
        this.eventBus.registerPriorityListener(PlayerTickListener.class, this);
        this.eventBus.registerPriorityListener(PacketSendListener.class, this);
        this.eventBus.registerPriorityListener(PacketReceiveListener.class, this);
        this.timer.reset();
        if (this.mc.player != null) {
            this.playerPosition = this.mc.player.getPos();
        }
        super.onEnable();
    }

    @Override
    public void onDisable() {
        this.eventBus.unregister(PlayerTickListener.class, this);
        this.eventBus.unregister(PacketSendListener.class, this);
        this.eventBus.unregister(PacketReceiveListener.class, this);
        this.resetPackets();
        super.onDisable();
    }

    @Override
    public void onPacketReceive(final PacketReceiveEvent event) {
        if (this.mc.world == null) {
            return;
        }
        if (event.packet instanceof ExplosionS2CPacket) {
            this.resetPackets();
        }
    }

    @Override
    public void onPacketSend(final PacketSendEvent event) {
        if (this.mc.world == null || this.mc.player.isUsingItem()) {
            return;
        }
        if (event.packet instanceof PlayerInteractEntityC2SPacket ||
                event.packet instanceof HandSwingC2SPacket ||
                event.packet instanceof PlayerInteractBlockC2SPacket ||
                event.packet instanceof ClickSlotC2SPacket) {
            this.resetPackets();
            return;
        }
        if (this.cancelOnElytra.getValue() && this.mc.player.getInventory().getArmorStack(2).getItem() == Items.ELYTRA) {
            this.resetPackets();
            return;
        }
        if (!this.preventOverflow) {
            this.packets.add(event.packet);
            event.cancelEvent();
        }
    }

    @Override
    public void onPlayerTick() {
        if (this.timer.hasPassedDelay(this.lagDelay.getValueFloat()) && this.mc.player != null && !this.mc.player.isUsingItem()) {
            this.resetPackets();
        }
    }

    private void resetPackets() {
        if (this.mc.player == null || this.mc.world == null) {
            return;
        }
        this.preventOverflow = true;
        synchronized (this.packets) {
            while (!this.packets.isEmpty()) {
                this.mc.getNetworkHandler().getConnection().send(this.packets.poll(), null, false);
            }
        }
        this.preventOverflow = false;
        this.timer.reset();
        this.playerPosition = this.mc.player.getPos();
    }
}