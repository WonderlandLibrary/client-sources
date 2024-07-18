package com.alan.clients.component.impl.player;

import com.alan.clients.component.Component;
import com.alan.clients.event.Listener;
import com.alan.clients.event.Priorities;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.PreMotionEvent;

public final class FallDistanceComponent extends Component {

    public static float distance;

    @EventLink(value = Priorities.VERY_LOW)
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        final double fallDistance = mc.thePlayer.lastTickPosY - mc.thePlayer.posY;

        if (fallDistance > 0) {
            distance += fallDistance;
        }

        if (event.isOnGround()) {
            distance = 0;
        }
    };
}
