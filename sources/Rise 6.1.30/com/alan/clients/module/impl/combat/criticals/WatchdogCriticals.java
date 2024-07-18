package com.alan.clients.module.impl.combat.criticals;

import com.alan.clients.component.impl.player.FallDistanceComponent;
import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.PreMotionEvent;
import com.alan.clients.module.impl.combat.Criticals;
import com.alan.clients.value.Mode;

public final class WatchdogCriticals extends Mode<Criticals> {

    public WatchdogCriticals(String name, Criticals parent) {
        super(name, parent);
    }

    @EventLink
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
//        Entity target = Client.INSTANCE.getTargetManager().getTarget(6);
//
//        if (FallDistanceComponent.distance < 1.8 && target != null) {
//            event.setOnGround(false);
//        }
        if (mc.thePlayer.ticksSinceVelocity <= 80 && FallDistanceComponent.distance < 1.8) {
            event.setOnGround(false);
        }
    };
}
