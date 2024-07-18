package com.alan.clients.component.impl.event;

import com.alan.clients.component.Component;
import com.alan.clients.event.Listener;
import com.alan.clients.event.Priorities;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.other.AttackEvent;
import com.alan.clients.event.impl.packet.PacketReceiveEvent;
import com.alan.clients.event.impl.packet.PacketSendEvent;
import com.alan.clients.util.vector.Vector3d;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.network.play.server.S12PacketEntityVelocity;

public class EntityTickComponent extends Component {

    @EventLink(value = Priorities.VERY_LOW)
    public final Listener<PacketSendEvent> onPacketSend = event -> {
        if (mc == null || mc.theWorld == null || event.isCancelled()) return;

        Packet<?> packet = event.getPacket();

        if (packet instanceof C08PacketPlayerBlockPlacement && !((C08PacketPlayerBlockPlacement) packet).getPosition().equalsVector(new Vector3d(-1, -1, -1))) {
            mc.thePlayer.ticksSincePlace = 0;
        }
    };

    @EventLink(value = Priorities.VERY_LOW)
    public final Listener<AttackEvent> onAttack = event -> {
        mc.thePlayer.ticksSinceAttack = 0;
    };

    @EventLink(value = Priorities.VERY_LOW)
    public final Listener<PacketReceiveEvent> onPacketReceiveEvent = event -> {
        if (mc == null || mc.theWorld == null || event.isCancelled()) return;

        Packet<?> packet = event.getPacket();

        if (packet instanceof S12PacketEntityVelocity) {
            final S12PacketEntityVelocity wrapper = (S12PacketEntityVelocity) packet;

            Entity entity = mc.theWorld.getEntityByID(wrapper.getEntityID());

            if (entity == null) {
                return;
            }

            entity.lastVelocityDeltaX = wrapper.motionX / 8000.0D;
            entity.lastVelocityDeltaY = wrapper.motionY / 8000.0D;
            entity.lastVelocityDeltaZ = wrapper.motionZ / 8000.0D;
            entity.ticksSinceVelocity = 0;
            if (wrapper.motionY / 8000.0D > 0.1 && Math.hypot(wrapper.motionZ / 8000.0D, wrapper.motionX / 8000.0D) > 0.2) {
                entity.ticksSincePlayerVelocity = 0;
            }
        } else if (packet instanceof S08PacketPlayerPosLook) {
            mc.thePlayer.ticksSinceTeleport = 0;
        }
    };
}
