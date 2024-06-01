package com.polarware.module.impl.player.flagdetector;

import com.polarware.module.impl.player.FlagDetectorModule;
import com.polarware.event.bus.Listener;
import com.polarware.event.Priority;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.motion.PreMotionEvent;
import com.polarware.value.Mode;


public class MathGround extends Mode<FlagDetectorModule> {

    public MathGround(String name, FlagDetectorModule parent) {
        super(name, parent);
    }

    @EventLink(value = Priority.VERY_LOW)
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {

        if (event.isOnGround() && event.getPosY() % (1 / 64f) != 0) {
            getParent().fail("Math Ground");
        }

    };
}
