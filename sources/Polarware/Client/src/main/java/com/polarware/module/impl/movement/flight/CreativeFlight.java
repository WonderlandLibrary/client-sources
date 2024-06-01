package com.polarware.module.impl.movement.flight;

import com.polarware.module.impl.movement.FlightModule;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.motion.PreMotionEvent;
import com.polarware.event.impl.network.PacketReceiveEvent;
import com.polarware.value.Mode;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S39PacketPlayerAbilities;

/**
 * @author Auth
 * @since 18/11/2021
 */

public class CreativeFlight extends Mode<FlightModule> {

    public CreativeFlight(String name, FlightModule parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        mc.thePlayer.capabilities.allowFlying = true;
    };

    @EventLink()
    public final Listener<PacketReceiveEvent> onPacketReceiveEvent = event -> {

        final Packet<?> p = event.getPacket();

        if (p instanceof S39PacketPlayerAbilities) {
            final S39PacketPlayerAbilities wrapper = (S39PacketPlayerAbilities) p;
            wrapper.setFlying(mc.thePlayer.capabilities.isFlying);
        }
    };

    @Override
    public void onEnable() {
        if (mc.thePlayer.onGround) {
            mc.thePlayer.jump();
        }
        mc.thePlayer.capabilities.isFlying = true;
    }

    @Override
    public void onDisable() {
        mc.thePlayer.capabilities.allowFlying = mc.playerController.getCurrentGameType().isCreative();
        mc.thePlayer.capabilities.isFlying = false;
    }
}