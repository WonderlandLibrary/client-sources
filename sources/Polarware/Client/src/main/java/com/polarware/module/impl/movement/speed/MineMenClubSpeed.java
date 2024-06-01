package com.polarware.module.impl.movement.speed;

import com.polarware.module.impl.movement.SpeedModule;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.motion.PreMotionEvent;
import com.polarware.event.impl.motion.StrafeEvent;
import com.polarware.util.player.MoveUtil;
import com.polarware.value.Mode;

/**
 * @author Alan
 * @since 18/11/2022
 */

public class MineMenClubSpeed extends Mode<SpeedModule> {

    public MineMenClubSpeed(String name, SpeedModule parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<StrafeEvent> onStrafe = event -> {

        if (mc.thePlayer.hurtTime <= 6) {
            MoveUtil.strafe();
        }
    };

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {

        if (mc.thePlayer.onGround) {
            mc.thePlayer.jump();
        }
    };
}