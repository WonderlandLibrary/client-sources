package com.polarware.module.impl.movement.noslow;

import com.polarware.module.impl.movement.NoSlowModule;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.motion.SlowDownEvent;
import com.polarware.value.Mode;
import com.polarware.value.impl.NumberValue;

/**
 * @author Alan
 * @since 18/11/2021
 */

public class PredictionNoSlow extends Mode<NoSlowModule> {

    private final NumberValue amount = new NumberValue("Amount", this, 2, 2, 5, 1);

    public PredictionNoSlow(String name, NoSlowModule parent) {
        super(name, parent);
    }

    @EventLink
    public final Listener<SlowDownEvent> onSlowDown = event -> {

        if (mc.thePlayer.onGroundTicks % this.amount.getValue().intValue() != 0) {
            event.setCancelled(true);
        }
    };
}