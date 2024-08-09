package dev.darkmoon.client.module.impl.player;

import com.darkmagician6.eventapi.EventTarget;
import dev.darkmoon.client.event.packet.EventReceivePacket;
import dev.darkmoon.client.module.Category;
import dev.darkmoon.client.module.Module;
import dev.darkmoon.client.module.ModuleAnnotation;
import net.minecraft.network.play.server.SPacketPlayerPosLook;

@ModuleAnnotation(name = "NoRotate", category = Category.PLAYER)
public class NoRotate extends Module {
    @EventTarget
    public void onPacket(EventReceivePacket eventPacket) {
        if (eventPacket.getPacket() instanceof SPacketPlayerPosLook) {
            SPacketPlayerPosLook packet = (SPacketPlayerPosLook) eventPacket.getPacket();
            packet.yaw = mc.player.rotationYaw;
            packet.pitch = mc.player.rotationPitch;
        }
    }
}
