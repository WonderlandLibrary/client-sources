package com.polarware.module.impl.combat.velocity;

import com.polarware.module.impl.combat.VelocityModule;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.network.PacketReceiveEvent;
import com.polarware.value.Mode;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;

public final class UniversoCraftVelocity extends Mode<VelocityModule> {

    public UniversoCraftVelocity(String name, VelocityModule parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<PacketReceiveEvent> onPacketReceiveEvent = event -> {
        if ((getParent().onSwing.getValue() || getParent().onSprint.getValue()) && mc.thePlayer.swingProgress == 0) return;

        final Packet<?> p = event.getPacket();

        if (p instanceof S12PacketEntityVelocity) {
            final S12PacketEntityVelocity wrapper = (S12PacketEntityVelocity) p;

            if (wrapper.getEntityID() == mc.thePlayer.getEntityId()) {
                event.setCancelled(true);
                mc.thePlayer.motionY += 0.1 - Math.random() / 100f;
            }
        }

        if (p instanceof S27PacketExplosion) {
            event.setCancelled(true);
            mc.thePlayer.motionY += 0.1 - Math.random() / 100f;
        }
    };
}
