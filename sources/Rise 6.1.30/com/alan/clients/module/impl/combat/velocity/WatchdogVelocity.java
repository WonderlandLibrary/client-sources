package com.alan.clients.module.impl.combat.velocity;

import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.PreMotionEvent;
import com.alan.clients.event.impl.packet.PacketReceiveEvent;
import com.alan.clients.module.impl.combat.Velocity;
import com.alan.clients.module.impl.movement.Speed;
import com.alan.clients.value.Mode;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S12PacketEntityVelocity;

public final class WatchdogVelocity extends Mode<Velocity> {

    public WatchdogVelocity(String name, Velocity parent) {
        super(name, parent);
    }

    private Speed speed = null;
    @EventLink
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
//        if (getParent().onSwing.getValue() && !mc.thePlayer.isSwingInProgress) return;

        if (speed != null) {
            speed = null;
        }
    };

    @EventLink
    public final Listener<PacketReceiveEvent> onPacketReceiveEvent = event -> {
        if (getParent().onSwing.getValue() && !mc.thePlayer.isSwingInProgress || event.isCancelled()) return;

        final Packet<?> p = event.getPacket();

        if (p instanceof S12PacketEntityVelocity) {
            final S12PacketEntityVelocity wrapper = (S12PacketEntityVelocity) p;

            if (wrapper.getEntityID() == mc.thePlayer.getEntityId()) {

                event.setCancelled();

                if (mc.thePlayer.posY < mc.thePlayer.lastGroundY + 0.5) {
                    mc.thePlayer.motionY = wrapper.getMotionY() / 8000.0D;
                }

            }
        }
    };
}
