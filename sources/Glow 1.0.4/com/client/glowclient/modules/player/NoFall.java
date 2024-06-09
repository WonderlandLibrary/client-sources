package com.client.glowclient.modules.player;

import com.client.glowclient.events.*;
import net.minecraft.network.*;
import net.minecraft.network.play.client.*;
import com.client.glowclient.*;
import com.client.glowclient.utils.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.client.glowclient.modules.*;

public class NoFall extends ModuleContainer
{
    private float b;
    
    @SubscribeEvent
    public void M(final EventClientPacket eventClientPacket) {
        try {
            if (eventClientPacket.getPacket() instanceof CPacketPlayer && !(eventClientPacket.getPacket() instanceof CPacketPlayer$Rotation) && !pc.M((Packet)eventClientPacket.getPacket())) {
                if (((CPacketPlayer)eventClientPacket.getPacket()).onGround && this.b >= 4.0f) {
                    final CPacketPlayer$PositionRotation cPacketPlayer$PositionRotation = new CPacketPlayer$PositionRotation(((CPacketPlayer)eventClientPacket.getPacket()).getX(0.0), 1337.0 + ((CPacketPlayer)eventClientPacket.getPacket()).getY(0.0), ((CPacketPlayer)eventClientPacket.getPacket()).getZ(0.0), ((CPacketPlayer)eventClientPacket.getPacket()).getYaw(0.0f), ((CPacketPlayer)eventClientPacket.getPacket()).getPitch(0.0f), true);
                    final CPacketPlayer$PositionRotation cPacketPlayer$PositionRotation2 = new CPacketPlayer$PositionRotation(((CPacketPlayer)eventClientPacket.getPacket()).getX(0.0), ((CPacketPlayer)eventClientPacket.getPacket()).getY(0.0), ((CPacketPlayer)eventClientPacket.getPacket()).getZ(0.0), ((CPacketPlayer)eventClientPacket.getPacket()).getYaw(0.0f), ((CPacketPlayer)eventClientPacket.getPacket()).getPitch(0.0f), true);
                    final float b = 0.0f;
                    final Packet packet = (Packet)cPacketPlayer$PositionRotation2;
                    pc.M((Packet)cPacketPlayer$PositionRotation);
                    pc.M(packet);
                    Ob.M().sendPacket((Packet)cPacketPlayer$PositionRotation);
                    Ob.M().sendPacket((Packet)cPacketPlayer$PositionRotation2);
                    this.b = b;
                    return;
                }
                this.b = Wrapper.mc.player.fallDistance;
            }
        }
        catch (Exception ex) {}
    }
    
    public NoFall() {
        final float b = 0.0f;
        super(Category.PLAYER, "NoFall", false, -1, "Removes fall damage");
        this.b = b;
    }
}
