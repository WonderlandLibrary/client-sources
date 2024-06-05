package me.superskidder.lune.modules.player;

import me.superskidder.lune.modules.Mod;
import me.superskidder.lune.modules.ModCategory;
import me.superskidder.lune.events.EventPacketSend;
import me.superskidder.lune.events.EventUpdate;
import me.superskidder.lune.manager.event.EventTarget;
import me.superskidder.lune.utils.player.PlayerUtil;
import me.superskidder.lune.utils.timer.TimerUtil;
import net.minecraft.network.play.client.C03PacketPlayer;

public class PacketMonitor extends Mod {
    private long time = 0L;
    private long lastTime;
    private int times;
    private TimerUtil timer = new TimerUtil();

    public PacketMonitor() {
        super("PacketMonitor", ModCategory.Player,"Monitor packets");
    }

    @EventTarget
    public void onUpdate(EventUpdate e) {
        if (this.timer.delay(500L)) {
            if (this.times > 40) {
                PlayerUtil.sendMessage("Too Many Packets:" + times * 2 + " (WANRING)");
            }
            this.setDisplayName(times * 2 + "/t");

            this.times = 0;
            this.timer.reset();
        }

    }

    @EventTarget
    public void onPacket(EventPacketSend e) {

        if (e.getPacket() instanceof C03PacketPlayer) {
            this.times++;
        }

        if (time > 40) {
            e.setCancelled(true);
        }


    }
}