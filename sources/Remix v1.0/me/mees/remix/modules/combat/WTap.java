package me.mees.remix.modules.combat;

import me.satisfactory.base.module.*;
import me.satisfactory.base.events.*;
import net.minecraft.world.*;
import net.minecraft.network.play.client.*;
import net.minecraft.entity.*;
import net.minecraft.network.*;
import pw.stamina.causam.scan.method.model.*;

public class WTap extends Module
{
    public WTap() {
        super("WTap", 0, Category.COMBAT);
    }
    
    @Subscriber
    public void readPacket(final EventPacketReceive eventPacketReceive) {
        if (WTap.mc.theWorld != null && WTap.mc.thePlayer != null && eventPacketReceive.packet instanceof C02PacketUseEntity) {
            final C02PacketUseEntity packet = (C02PacketUseEntity)eventPacketReceive.packet;
            if (packet.getAction() == C02PacketUseEntity.Action.ATTACK && packet.getEntityFromWorld(WTap.mc.theWorld) != WTap.mc.thePlayer && WTap.mc.thePlayer.getFoodStats().getFoodLevel() > 6) {
                WTap.mc.thePlayer.setSprinting(false);
                WTap.mc.getNetHandler().addToSendQueue(new C0BPacketEntityAction(WTap.mc.thePlayer, C0BPacketEntityAction.Action.STOP_SPRINTING));
                WTap.mc.thePlayer.setSprinting(true);
                WTap.mc.getNetHandler().addToSendQueue(new C0BPacketEntityAction(WTap.mc.thePlayer, C0BPacketEntityAction.Action.START_SPRINTING));
            }
        }
    }
}
