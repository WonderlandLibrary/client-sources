package dev.darkmoon.client.module.impl.util;

import com.darkmagician6.eventapi.EventTarget;
import dev.darkmoon.client.event.packet.EventSendPacket;
import dev.darkmoon.client.module.Category;
import dev.darkmoon.client.module.Module;
import dev.darkmoon.client.module.ModuleAnnotation;
import dev.darkmoon.client.module.setting.impl.NumberSetting;
import net.minecraft.network.play.client.CPacketConfirmTransaction;
import net.minecraft.network.play.client.CPacketKeepAlive;

@ModuleAnnotation(name = "PingSpoof", category = Category.UTIL)
public class PingSpoof extends Module {
    long id;
    short tsid;
    int twid;
    int ticks;
    NumberSetting spoof = new NumberSetting("Spoof-Delay", 25000.0F, 50.0F, 30000.0F, 5.0F);



    @EventTarget
    public void onSend(EventSendPacket event) {
        long delay = (long)spoof.get();
        if (!mc.isSingleplayer()) {
            if (event.getPacket() instanceof CPacketConfirmTransaction) {
                if (this.tsid == ((CPacketConfirmTransaction)event.getPacket()).getUid() && this.twid == ((CPacketConfirmTransaction)event.getPacket()).getWindowId()) {
                    return;
                }

                event.setCancelled(true);
                (new Thread(() -> {
                    try {
                        Thread.sleep(delay);
                    } catch (InterruptedException var5) {
                        var5.printStackTrace();
                    }

                    this.tsid = ((CPacketConfirmTransaction)event.getPacket()).getUid();
                    this.twid = ((CPacketConfirmTransaction)event.getPacket()).getWindowId();
                    if (mc.player != null) {
                        if (mc.player.connection != null) {
                            mc.player.connection.sendPacket(event.getPacket());
                        }
                    }
                })).start();
            }

            if (event.getPacket() instanceof CPacketKeepAlive) {
                if (this.id == ((CPacketKeepAlive)event.getPacket()).getKey()) {
                    return;
                }

                event.setCancelled(true);;
                (new Thread(() -> {
                    try {
                        Thread.sleep(delay);
                    } catch (InterruptedException var5) {
                        var5.printStackTrace();
                    }

                    this.id = ((CPacketKeepAlive)event.getPacket()).getKey();
                    mc.player.connection.sendPacket(event.getPacket());
                })).start();
            }
        }
    }
}
