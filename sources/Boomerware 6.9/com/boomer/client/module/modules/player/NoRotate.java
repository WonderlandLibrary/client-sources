package com.boomer.client.module.modules.player;

import com.boomer.client.event.bus.Handler;
import com.boomer.client.event.events.world.PacketEvent;
import com.boomer.client.module.Module;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;

import java.awt.*;

/**
 * made by oHare for BoomerWare
 *
 * @since 7/19/2019
 **/
public class NoRotate extends Module {
    public NoRotate() {
        super("NoRotate", Category.PLAYER, new Color(0x9D9798).getRGB());
        setRenderlabel("No Rotate");
        setDescription("Cancel ncp rotation flags.");
    }
    
    @Handler
    public void handle(PacketEvent event) {
        if (!event.isSending() && event.getPacket() instanceof S08PacketPlayerPosLook) {
            S08PacketPlayerPosLook packet = (S08PacketPlayerPosLook) event.getPacket();
            if (mc.thePlayer != null && mc.theWorld != null && mc.thePlayer.rotationYaw != -180 && mc.thePlayer.rotationPitch != 0) {
                packet.yaw = mc.thePlayer.rotationYaw;
                packet.pitch = mc.thePlayer.rotationPitch;
            }
        }
    }
}
