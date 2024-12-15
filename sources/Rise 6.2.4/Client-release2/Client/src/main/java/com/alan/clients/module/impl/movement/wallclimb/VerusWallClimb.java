package com.alan.clients.module.impl.movement.wallclimb;

import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.PreMotionEvent;
import com.alan.clients.module.impl.movement.WallClimb;
import com.alan.clients.value.Mode;

public class VerusWallClimb extends Mode<WallClimb> {

    public VerusWallClimb(String name, WallClimb parent) {
        super(name, parent);
    }

    @EventLink
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        if (mc.thePlayer.isCollidedHorizontally) {
            if (mc.thePlayer.ticksExisted % 2 == 0) {
                mc.thePlayer.jump();
            }
        }
    };
}