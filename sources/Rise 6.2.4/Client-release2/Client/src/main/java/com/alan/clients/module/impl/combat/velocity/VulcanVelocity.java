package com.alan.clients.module.impl.combat.velocity;

import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.packet.PacketReceiveEvent;
import com.alan.clients.module.impl.combat.Velocity;
import com.alan.clients.module.impl.movement.Speed;
import com.alan.clients.module.impl.movement.Sprint;
import com.alan.clients.util.packet.PacketUtil;
import com.alan.clients.util.player.MoveUtil;
import com.alan.clients.value.Mode;
import com.alan.clients.value.impl.NumberValue;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;
import net.minecraft.network.play.server.S32PacketConfirmTransaction;

public final class VulcanVelocity extends Mode<Velocity> {

    private final NumberValue horizontal = new NumberValue("Horizontal", this, 0, 0, 100, 1);
    private final NumberValue vertical = new NumberValue("Vertical", this, 0, 0, 100, 1);
    private boolean transaction = false;

    public VulcanVelocity(String name, Velocity parent) {
        super(name, parent);
    }

    @EventLink
    public final Listener<PacketReceiveEvent> onPacketReceiveEvent = event -> {
        if (getParent().onSwing.getValue() && !mc.thePlayer.isSwingInProgress || event.isCancelled()) return;

        final Packet<?> p = event.getPacket();
        final double horizontal = this.horizontal.getValue().doubleValue();
        final double vertical = this.vertical.getValue().doubleValue();


        if (p instanceof S12PacketEntityVelocity) {
            final S12PacketEntityVelocity wrapper = (S12PacketEntityVelocity) p;

            if (wrapper.getEntityID() == mc.thePlayer.getEntityId()) {
                if (horizontal == 0 && vertical == 0) {
if(!(getModule(Speed.class).mode.getValue().getName()=="Vulcan") || !getModule(Speed.class).isEnabled() || !MoveUtil.isMoving()){
    event.setCancelled();
                    } else {

    wrapper.motionX *= 1.9;
    wrapper.motionY *= 1;
    wrapper.motionZ *=1.9;
}

                    return;
                }

                wrapper.motionX *= 1;
                wrapper.motionY *= 1;
                wrapper.motionZ *=1;

                event.setPacket(wrapper);
            }
        }


        if (p instanceof S27PacketExplosion) {
            final S27PacketExplosion wrapper = (S27PacketExplosion) p;

            if (horizontal == 0 && vertical == 0) {
                event.setCancelled();
                return;
            }

            wrapper.posX *= horizontal / 100;
            wrapper.posY *= vertical / 100;
            wrapper.posZ *= horizontal / 100;

            event.setPacket(wrapper);
        }


        if (p instanceof S32PacketConfirmTransaction && mc.thePlayer.hurtTime == 10) {
            event.setCancelled(true);
            PacketUtil.send(new C0FPacketConfirmTransaction((short) (transaction ? 1 : -1), (short) (transaction ? -1 : 1), transaction));
            transaction = !transaction;


        }
    };
}
