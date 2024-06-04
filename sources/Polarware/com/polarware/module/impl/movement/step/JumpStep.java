package com.polarware.module.impl.movement.step;

import com.polarware.module.impl.movement.StepModule;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.motion.PreMotionEvent;
import com.polarware.value.Mode;

/**
 * @author Alan
 * @since 22/3/2022
 */

public class JumpStep extends Mode<StepModule> {

    public JumpStep(String name, StepModule parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        if (mc.thePlayer.onGround && mc.thePlayer.isCollidedHorizontally) {
            mc.thePlayer.jump();
        }
    };
}