package com.polarware.module.impl.movement.noslow;

import com.polarware.module.impl.movement.NoSlowModule;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.motion.SlowDownEvent;
import com.polarware.value.Mode;

/**
 * @author Strikeless
 * @since 13.03.2022
 */
public class VanillaNoSlow extends Mode<NoSlowModule> {

    public VanillaNoSlow(String name, NoSlowModule parent) {
        super(name, parent);
    }

    @EventLink
    public final Listener<SlowDownEvent> onSlowDown = event -> {
        event.setCancelled(true);
    };
}