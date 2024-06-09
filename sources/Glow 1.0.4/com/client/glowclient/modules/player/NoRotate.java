package com.client.glowclient.modules.player;

import com.client.glowclient.events.*;
import net.minecraft.network.play.server.*;
import com.client.glowclient.utils.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.client.glowclient.modules.*;

public class NoRotate extends ModuleContainer
{
    @Override
    public boolean A() {
        return false;
    }
    
    @SubscribeEvent
    public void M(final EventServerPacket eventServerPacket) {
        try {
            if (eventServerPacket.getPacket() instanceof SPacketPlayerPosLook) {
                final SPacketPlayerPosLook sPacketPlayerPosLook = (SPacketPlayerPosLook)eventServerPacket.getPacket();
                if (Wrapper.mc.player != null && Wrapper.mc.player.rotationYaw != -180.0f && Wrapper.mc.player.rotationPitch != 0.0f) {
                    final SPacketPlayerPosLook sPacketPlayerPosLook2 = sPacketPlayerPosLook;
                    sPacketPlayerPosLook2.yaw = Wrapper.mc.player.rotationYaw;
                    sPacketPlayerPosLook2.pitch = Wrapper.mc.player.rotationPitch;
                }
            }
        }
        catch (Exception ex) {}
    }
    
    public NoRotate() {
        super(Category.PLAYER, "NoRotate", false, -1, "Don't take SPacketRotation");
    }
}
