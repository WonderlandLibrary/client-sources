package com.polarware.module.impl.combat.velocity;

import com.polarware.module.impl.combat.VelocityModule;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.motion.PreMotionEvent;
import com.polarware.value.Mode;

public final class MatrixVelocity extends Mode<VelocityModule> {

    public MatrixVelocity(String name, VelocityModule parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        if ((getParent().onSwing.getValue() || getParent().onSprint.getValue()) && mc.thePlayer.swingProgress == 0) return;

        if (mc.thePlayer.hurtTime > 0) {
            mc.thePlayer.motionX *= 0.6D;
            mc.thePlayer.motionZ *= 0.6D;
        }
    };
}
