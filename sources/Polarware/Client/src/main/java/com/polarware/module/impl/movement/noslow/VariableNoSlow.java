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

public class VariableNoSlow extends Mode<NoSlowModule> {

    private final NumberValue multiplier = new NumberValue("Multiplier", this, 0.8, 0.2, 1, 0.05);

    public VariableNoSlow(String name, NoSlowModule parent) {
        super(name, parent);
    }

    @EventLink
    public final Listener<SlowDownEvent> onSlowDown = event -> {
        event.setForwardMultiplier(multiplier.getValue().floatValue());
        event.setStrafeMultiplier(multiplier.getValue().floatValue());
    };
}