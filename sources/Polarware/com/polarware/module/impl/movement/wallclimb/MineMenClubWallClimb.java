package com.polarware.module.impl.movement.wallclimb;

import com.polarware.module.impl.movement.WallClimbModule;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.motion.PreMotionEvent;
import com.polarware.util.interfaces.InstanceAccess;
import com.polarware.util.player.MoveUtil;
import com.polarware.value.Mode;

/**
 * @author Alan
 * @since 05.06.2022
 */

public class MineMenClubWallClimb extends Mode<WallClimbModule> {

    private boolean hitHead;

    public MineMenClubWallClimb(String name, WallClimbModule parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {

        if (InstanceAccess.mc.thePlayer.isCollidedHorizontally && !hitHead && InstanceAccess.mc.thePlayer.ticksExisted % 3 == 0) {
            InstanceAccess.mc.thePlayer.motionY = MoveUtil.jumpMotion();
        }

        if (InstanceAccess.mc.thePlayer.isCollidedVertically) {
            hitHead = !InstanceAccess.mc.thePlayer.onGround;
        }
    };
}