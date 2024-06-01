package com.polarware.module.impl.movement.sneak;

import com.polarware.module.impl.movement.SneakModule;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.motion.PreMotionEvent;
import com.polarware.value.Mode;

/**
 * @author Auth
 * @since 25/06/2022
 */

public class StandardSneak extends Mode<SneakModule> {

    public StandardSneak(String name, SneakModule parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {

        mc.thePlayer.movementInput.sneak = mc.thePlayer.sendQueue.doneLoadingTerrain;
    };
}