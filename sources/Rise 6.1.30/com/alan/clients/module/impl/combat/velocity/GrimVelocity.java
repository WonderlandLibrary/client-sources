package com.alan.clients.module.impl.combat.velocity;

import com.alan.clients.event.Listener;
import com.alan.clients.event.Priorities;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.PreUpdateEvent;
import com.alan.clients.event.impl.packet.PacketReceiveEvent;
import com.alan.clients.module.impl.combat.Velocity;
import com.alan.clients.util.packet.PacketUtil;
import com.alan.clients.value.Mode;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S19PacketEntityStatus;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;

public final class GrimVelocity extends Mode<Velocity> {

    public GrimVelocity(String name, Velocity parent) {
        super(name, parent);
    }

    private boolean realVelocity, velocity;

    @EventLink(value = Priorities.VERY_LOW)
    public final Listener<PreUpdateEvent> onPreUpdate = event -> {
        if (velocity) {
            PacketUtil.send(new C07PacketPlayerDigging((mc.objectMouseOver != null && mc.thePlayer.isSwingInProgress && mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK ? C07PacketPlayerDigging.Action.START_DESTROY_BLOCK : C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK),
                    new BlockPos(mc.thePlayer), EnumFacing.UP));

            velocity = false;
        }
    };
    @EventLink(value = Priorities.VERY_LOW)
    public final Listener<PacketReceiveEvent> onReceiveLow = event -> {
        final Packet<?> packet = event.getPacket();
        if (event.isCancelled()) return;

        if(packet instanceof S19PacketEntityStatus) {
            final S19PacketEntityStatus wrapper = (S19PacketEntityStatus) event.getPacket();

            if(wrapper.getEntity(mc.theWorld) != mc.thePlayer || wrapper.getOpCode() != 2) {
                return;
            }

            realVelocity = true;
        }

        if (packet instanceof S12PacketEntityVelocity && realVelocity) {
            final S12PacketEntityVelocity wrapper = (S12PacketEntityVelocity) packet;

            if (wrapper.getEntityID() == mc.thePlayer.getEntityId()) {
                event.setCancelled();

                realVelocity = false;
                velocity = true;
            }
        }
    };
}
