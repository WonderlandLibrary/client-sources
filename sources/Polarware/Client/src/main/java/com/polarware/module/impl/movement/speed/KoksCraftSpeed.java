package com.polarware.module.impl.movement.speed;

import com.polarware.module.impl.movement.SpeedModule;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.motion.PreMotionEvent;
import com.polarware.util.player.MoveUtil;
import com.polarware.value.Mode;

/**
 * @author Alan
 * @since 18/11/2022
 */

public class KoksCraftSpeed extends Mode<SpeedModule> {

    int jumps;

    public KoksCraftSpeed(String name, SpeedModule parent) {
        super(name, parent);
    }

    @Override
    public void onEnable() {
        jumps = 0;
    }

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {

        if (mc.thePlayer.onGround) {
            MoveUtil.strafe(0.7);

            mc.thePlayer.motionY = 0.00001f;

            jumps++;
        } else {
            MoveUtil.strafe(0.81);
        }
    };

}
