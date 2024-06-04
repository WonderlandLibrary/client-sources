package com.polarware.module.impl.player.nofall;

import com.polarware.module.impl.player.NoFallModule;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.motion.PreMotionEvent;
import com.polarware.value.Mode;

/**
 * @author Auth
 * @since 3/02/2022
 */
public class SpoofNoFall extends Mode<NoFallModule> {

    public SpoofNoFall(String name, NoFallModule parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {

        event.setOnGround(true);

    };
}