package com.polarware.module.impl.player.nofall;

import com.polarware.component.impl.player.FallDistanceComponent;
import com.polarware.module.impl.player.NoFallModule;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.motion.PreMotionEvent;
import com.polarware.util.player.MoveUtil;
import com.polarware.value.Mode;

/**
 * @author Auth
 * @since 3/02/2022
 */
public class RoundNoFall extends Mode<NoFallModule> {

    public RoundNoFall(String name, NoFallModule parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {

        final double rounded = MoveUtil.roundToGround(event.getPosY());
        final float distance = FallDistanceComponent.distance;

        if (distance > 3 && Math.abs(rounded - event.getPosY()) < 0.005) {
            mc.thePlayer.setPosition(mc.thePlayer.posX, rounded, mc.thePlayer.posZ);
            event.setOnGround(true);
            event.setPosY(rounded);
        }
    };
}