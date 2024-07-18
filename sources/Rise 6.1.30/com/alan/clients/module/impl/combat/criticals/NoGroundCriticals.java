package com.alan.clients.module.impl.combat.criticals;

import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.PreMotionEvent;
import com.alan.clients.module.impl.combat.Criticals;
import com.alan.clients.value.Mode;

public final class NoGroundCriticals extends Mode<Criticals> {

    public NoGroundCriticals(String name, Criticals parent) {
        super(name, parent);
    }

    @EventLink
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        event.setOnGround(false);
    };
}
