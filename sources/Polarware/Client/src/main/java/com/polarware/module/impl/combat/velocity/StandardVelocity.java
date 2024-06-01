package com.polarware.module.impl.combat.velocity;

import com.polarware.module.impl.combat.VelocityModule;
import com.polarware.module.impl.movement.FlightModule;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.network.PacketReceiveEvent;
import com.polarware.value.Mode;
import com.polarware.value.impl.NumberValue;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;

public final class StandardVelocity extends Mode<VelocityModule> {

    private final NumberValue horizontal = new NumberValue("Horizontal", this, 0, 0, 100, 1);
    private final NumberValue vertical = new NumberValue("Vertical", this, 0, 0, 100, 1);

    private FlightModule fly;

    public StandardVelocity(String name, VelocityModule parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<PacketReceiveEvent> onPacketReceiveEvent = event -> {

        if (fly == null) {
            fly = getModule(FlightModule.class);
        }

        if ((getParent().onSwing.getValue() || getParent().onSprint.getValue()) && mc.thePlayer.swingProgress == 0 || fly.isEnabled()) return;
        final Packet<?> p = event.getPacket();

        final double horizontal = this.horizontal.getValue().doubleValue();
        final double vertical = this.vertical.getValue().doubleValue();

        if (p instanceof S12PacketEntityVelocity) {
            final S12PacketEntityVelocity wrapper = (S12PacketEntityVelocity) p;

            if (wrapper.getEntityID() == mc.thePlayer.getEntityId()) {
                if (horizontal == 0) {
                    event.setCancelled(true);

                    if (vertical != 0) {
                        mc.thePlayer.motionY = wrapper.getMotionY() / 8000.0D;
                    }
                    return;
                }

                wrapper.motionX *= horizontal / 100;
                wrapper.motionY *= vertical / 100;
                wrapper.motionZ *= horizontal / 100;

                event.setPacket(wrapper);

            }
        } else if (p instanceof S27PacketExplosion) {
            final S27PacketExplosion wrapper = (S27PacketExplosion) p;

            if (horizontal == 0 && vertical == 0) {
                event.setCancelled(true);
                return;
            }

            wrapper.posX *= horizontal / 100;
            wrapper.posY *= vertical / 100;
            wrapper.posZ *= horizontal / 100;

            event.setPacket(wrapper);
        }
    };
}
