// 
// Decompiled by Procyon v0.5.30
// 

package com.klintos.twelve.mod;

import com.klintos.twelve.mod.events.EventPostUpdate;
import com.klintos.twelve.mod.events.EventPreUpdate;
import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.network.Packet;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import com.klintos.twelve.mod.events.EventPacketSend;

public class Sneak extends Mod
{
    public Sneak() {
        super("Sneak", 44, ModCategory.PLAYER);
    }
    
    @EventTarget
    public void onPacketSend(final EventPacketSend event) {
        final Packet packet = event.getPacket();
        if (packet instanceof C08PacketPlayerBlockPlacement) {
            if (Minecraft.getMinecraft().thePlayer.isSneaking()) {
                return;
            }
            final C08PacketPlayerBlockPlacement packetBlockPlacement = (C08PacketPlayerBlockPlacement)packet;
            if (packetBlockPlacement.getPlacedBlockDirection() == 255) {
                return;
            }
            Sneak.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C0BPacketEntityAction((Entity)Minecraft.getMinecraft().thePlayer, C0BPacketEntityAction.Action.STOP_SNEAKING));
        }
    }
    
    @EventTarget
    public void onPreUpdate(final EventPreUpdate event) {
        Sneak.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C0BPacketEntityAction((Entity)Minecraft.getMinecraft().thePlayer, C0BPacketEntityAction.Action.STOP_SNEAKING));
    }
    
    @EventTarget
    public void onPostUpdate(final EventPostUpdate event) {
        Sneak.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C0BPacketEntityAction((Entity)Minecraft.getMinecraft().thePlayer, C0BPacketEntityAction.Action.START_SNEAKING));
    }
    
    @Override
    public void onDisable() {
        Sneak.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C0BPacketEntityAction((Entity)Minecraft.getMinecraft().thePlayer, C0BPacketEntityAction.Action.STOP_SNEAKING));
    }
}
