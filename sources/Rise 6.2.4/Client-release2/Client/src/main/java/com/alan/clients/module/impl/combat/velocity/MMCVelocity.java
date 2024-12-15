package com.alan.clients.module.impl.combat.velocity;

import com.alan.clients.component.impl.player.BadPacketsComponent;
import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.PreMotionEvent;
import com.alan.clients.event.impl.packet.PacketReceiveEvent;
import com.alan.clients.module.impl.combat.Velocity;
import com.alan.clients.value.Mode;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S12PacketEntityVelocity;

public final class MMCVelocity extends Mode<Velocity> {

    private boolean velocity;

    public MMCVelocity(String name, Velocity parent) {
        super(name, parent);
    }

    @EventLink
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        //if (this.velocity == 0) {
        //  mc.thePlayer.jump();
        // }

        if (mc.thePlayer.hurtTime > 0 && !BadPacketsComponent.bad(false, true, false, false, false)) {
            mc.thePlayer.motionX *= 0.6D;
            mc.thePlayer.motionZ *= 0.6D;
        }

        this.velocity = true;
//        this.velocity++;
    };

    @EventLink
    public final Listener<PacketReceiveEvent> onPacketReceiveEvent = event -> {
        if (getParent().onSwing.getValue() && !mc.thePlayer.isSwingInProgress || event.isCancelled()) return;

        final Packet<?> packet = event.getPacket();

        if (packet instanceof S12PacketEntityVelocity) {
            S12PacketEntityVelocity velocity = ((S12PacketEntityVelocity) packet);

            if (velocity.getEntityID() != mc.thePlayer.getEntityId()) return;

            //this is likea  60% velocity
            if (mc.thePlayer.isSprinting()) {
                mc.gameSettings.keyBindSprint.setPressed(false);
            }
        }
    };
}
