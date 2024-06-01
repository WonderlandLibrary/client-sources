package com.polarware.module.impl.player.nofall;

import com.polarware.component.impl.player.FallDistanceComponent;
import com.polarware.module.impl.player.NoFallModule;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.motion.PreMotionEvent;
import com.polarware.util.player.MoveUtil;
import com.polarware.util.player.PlayerUtil;
import com.polarware.value.Mode;

/**
 * @author Alan
 * @since 3/02/2022
 */
public class MatrixNoFall extends Mode<NoFallModule> {

    public MatrixNoFall(String name, NoFallModule parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {

        float distance = FallDistanceComponent.distance;

        if (PlayerUtil.isBlockUnder()) {
            if (distance > 2) {
                MoveUtil.strafe(0.19);
            }

            if (distance > 3 && MoveUtil.speed() < 0.2) {
                event.setOnGround(true);
                distance = 0;
            }
        }

        FallDistanceComponent.distance = distance;
    };
}