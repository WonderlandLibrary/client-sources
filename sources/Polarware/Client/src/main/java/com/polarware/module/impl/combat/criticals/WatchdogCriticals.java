package com.polarware.module.impl.combat.criticals;

import com.polarware.module.impl.combat.CriticalsModule;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.motion.PreMotionEvent;
import com.polarware.value.Mode;

public final class WatchdogCriticals extends Mode<CriticalsModule> {

    public WatchdogCriticals(String name, CriticalsModule parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        if (mc.thePlayer.ticksSinceVelocity <= 18 && mc.thePlayer.fallDistance < 1.3) {
            event.setOnGround(false);
        }
    };
}
