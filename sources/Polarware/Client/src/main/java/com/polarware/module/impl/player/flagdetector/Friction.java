package com.polarware.module.impl.player.flagdetector;

import com.polarware.module.impl.player.FlagDetectorModule;
import com.polarware.event.bus.Listener;
import com.polarware.event.Priority;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.motion.PreMotionEvent;
import com.polarware.value.Mode;


public class Friction extends Mode<FlagDetectorModule> {
    public Friction(String name, FlagDetectorModule parent) {
        super(name, parent);
    }

    @EventLink(value = Priority.VERY_LOW)
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {

        if (mc.thePlayer.ticksSinceVelocity <= 20 || mc.thePlayer.isCollidedHorizontally ||
                mc.thePlayer.offGroundTicks <= 1 || event.isOnGround() || mc.thePlayer.capabilities.isFlying ||
                mc.thePlayer.ticksSinceTeleport == 1) return;

        double moveFlying = 0.02599999835384377;
        double friction = 0.9100000262260437;

        double speed = Math.hypot(mc.thePlayer.motionX, mc.thePlayer.motionZ);
        double lastSpeed = Math.hypot(mc.thePlayer.lastMotionX, mc.thePlayer.lastMotionZ);

        if (speed > lastSpeed * friction + moveFlying) {
            getParent().fail("Friction");
        }
    };
}
