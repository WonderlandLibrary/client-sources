package com.boomer.client.module.modules.player;

import com.boomer.client.event.bus.Handler;
import com.boomer.client.event.events.world.PacketEvent;
import com.boomer.client.module.Module;

import net.minecraft.network.play.client.C0BPacketEntityAction;

public class Sneak extends Module {

    public Sneak() {
        super("Sneak", Category.PLAYER, 0x0A921E);
        setDescription("Sneak around like a black person robbing a store");
    }

    @Handler
    public void onPacket(PacketEvent event) {
        if (event.isSending() && event.getPacket() instanceof C0BPacketEntityAction && ((C0BPacketEntityAction)event.getPacket()).getAction() == C0BPacketEntityAction.Action.STOP_SNEAKING) {
            event.setCanceled(true);
        }
    }

    @Override
    public void onEnable() {
        if (mc.thePlayer != null) {
            mc.thePlayer.sendQueue.getNetworkManager().sendPacket(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.START_SNEAKING));
        }
    }

    @Override
    public void onDisable() {
        if (mc.thePlayer != null) {
            mc.thePlayer.sendQueue.getNetworkManager().sendPacket(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.STOP_SNEAKING));
        }
    }
}