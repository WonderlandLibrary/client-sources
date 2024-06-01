package com.polarware.module.impl.player.nofall;

import com.polarware.module.impl.player.NoFallModule;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.motion.PreMotionEvent;
import com.polarware.value.Mode;

/**
 * @author Alan
 * @since 3/02/2022
 */
public class NoGroundNoFall extends Mode<NoFallModule> {

    public NoGroundNoFall(String name, NoFallModule parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        event.setOnGround(false);
        event.setPosY(event.getPosY() + Math.random() / 100000000000000000000f);
    };
}