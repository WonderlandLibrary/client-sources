package com.alan.clients.module.impl.movement.wallclimb;

import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.PreMotionEvent;
import com.alan.clients.module.impl.movement.WallClimb;
import com.alan.clients.util.player.MoveUtil;
import com.alan.clients.value.Mode;

public class MineMenClubWallClimb extends Mode<WallClimb> {

    private boolean hitHead;

    public MineMenClubWallClimb(String name, WallClimb parent) {
        super(name, parent);
    }

    @EventLink
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        if (mc.thePlayer.isCollidedHorizontally && !hitHead && mc.thePlayer.ticksExisted % 3 == 0) {
            mc.thePlayer.motionY = MoveUtil.jumpMotion();
        }

        if (mc.thePlayer.isCollidedVertically) {
            hitHead = !mc.thePlayer.onGround;
        }
    };
}