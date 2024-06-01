package com.polarware.module.impl.movement.wallclimb;

import com.polarware.module.impl.movement.WallClimbModule;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.motion.PreMotionEvent;
import com.polarware.util.interfaces.InstanceAccess;
import com.polarware.value.Mode;

/**
 * @author Nicklas
 * @since 05.06.2022
 */
public class VerusWallClimb extends Mode<WallClimbModule> {

    public VerusWallClimb(String name, WallClimbModule parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {

        if (InstanceAccess.mc.thePlayer.isCollidedHorizontally) {
            if (InstanceAccess.mc.thePlayer.ticksExisted % 2 == 0) {
                InstanceAccess.mc.thePlayer.jump();
            }
        }
    };
}