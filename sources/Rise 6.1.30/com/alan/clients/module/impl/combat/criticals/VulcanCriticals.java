package com.alan.clients.module.impl.combat.criticals;

import com.alan.clients.component.impl.player.FallDistanceComponent;
import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.PreMotionEvent;
import com.alan.clients.module.impl.combat.Criticals;
import com.alan.clients.value.Mode;

public final class VulcanCriticals extends Mode<Criticals> {

    public VulcanCriticals(String name, Criticals parent) {
        super(name, parent);
    }

    @EventLink
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        if (mc.thePlayer.ticksSinceVelocity <= 18 && FallDistanceComponent.distance < 1.8) {
            event.setOnGround(false);
        }
    };
}