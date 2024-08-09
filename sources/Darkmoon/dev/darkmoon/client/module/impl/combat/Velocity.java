package dev.darkmoon.client.module.impl.combat;

import com.darkmagician6.eventapi.EventTarget;
import dev.darkmoon.client.module.setting.impl.ModeSetting;
import dev.darkmoon.client.event.packet.EventReceivePacket;
import dev.darkmoon.client.event.player.EventUpdate;
import dev.darkmoon.client.module.Category;
import dev.darkmoon.client.module.Module;
import dev.darkmoon.client.module.ModuleAnnotation;
import net.minecraft.network.play.server.SPacketConfirmTransaction;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.network.play.server.SPacketExplosion;

@ModuleAnnotation(name = "Velocity", category = Category.COMBAT)
public class Velocity extends Module {
    public static ModeSetting mode = new ModeSetting("Mode", "Cancel", "Cancel", "Grim");
    public int cancelPacket = 6;
    public int resetPersecute = 8;
    public  int grimTCancel = 0;
    public int updates = 0;

    @EventTarget
    public void onReceivePacket(EventReceivePacket event) {
        if (mode.get().equals("Cancel")) {
            if (event.getPacket() instanceof SPacketEntityVelocity) {
                SPacketEntityVelocity packet = (SPacketEntityVelocity) event.getPacket();
                if (packet.getEntityID() == mc.player.getEntityId()) {
                    event.setCancelled(true);
                }
            }
            if (event.getPacket() instanceof SPacketExplosion) {
                event.setCancelled(true);
            }
        } else if (mode.get().equals("Grim")) {
            if (event.getPacket() instanceof SPacketEntityVelocity) {
                SPacketEntityVelocity packet = (SPacketEntityVelocity) event.getPacket();
                if (packet.getEntityID() == mc.player.getEntityId()) {
                    event.setCancelled(true);
                    grimTCancel = cancelPacket;
                }
            }
            if (event.getPacket() instanceof SPacketConfirmTransaction) {
                if (grimTCancel > 0) {
                    event.setCancelled(true);
                    grimTCancel--;
                }
            } else if (mode.get().equals("SunRise New")) {
                SPacketEntityVelocity velocity;

            }
        }
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        if (mode.get().equals("Grim")) {
            updates++;
            if (resetPersecute > 0) {
                if (updates >= 0 || updates >= resetPersecute) {
                    updates = 0;
                    if (grimTCancel > 0) {
                        grimTCancel--;
                    }
                }
            }
        }
    }

    @Override
    public void onEnable() {
        super.onEnable();
        grimTCancel = 0;
    }
}
