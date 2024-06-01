package com.polarware.module.impl.combat.criticals;

import com.polarware.module.impl.combat.CriticalsModule;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.motion.PreMotionEvent;
import com.polarware.value.Mode;

public final class NoGroundCriticals extends Mode<CriticalsModule> {

    public NoGroundCriticals(String name, CriticalsModule parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        event.setOnGround(false);
    };
}
