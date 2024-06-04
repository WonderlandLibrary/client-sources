package com.polarware.module.impl.combat.velocity;

import com.polarware.module.impl.combat.VelocityModule;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.motion.PreMotionEvent;
import com.polarware.event.impl.network.PacketReceiveEvent;
import com.polarware.value.Mode;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;

public final class MMCVelocity extends Mode<VelocityModule> {

    private int velocity;

    public MMCVelocity(String name, VelocityModule parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        this.velocity++;
    };


    @EventLink()
    public final Listener<PacketReceiveEvent> onPacketReceiveEvent = event -> {
        if ((getParent().onSwing.getValue() || getParent().onSprint.getValue()) && mc.thePlayer.swingProgress == 0) return;

        final Packet<?> packet = event.getPacket();

        if (this.velocity > 20) {
            if (packet instanceof S12PacketEntityVelocity) {
                final S12PacketEntityVelocity wrapper = (S12PacketEntityVelocity) packet;

                if (wrapper.getEntityID() == mc.thePlayer.getEntityId()) {
                    event.setCancelled(true);
                    this.velocity = 0;
                }
            } else if (packet instanceof S27PacketExplosion) {
                event.setCancelled(true);
                this.velocity = 0;
            }
        }
    };
}