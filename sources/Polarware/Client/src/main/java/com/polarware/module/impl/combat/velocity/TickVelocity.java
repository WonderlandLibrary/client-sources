package com.polarware.module.impl.combat.velocity;


import com.polarware.module.impl.combat.VelocityModule;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.motion.PreMotionEvent;
import com.polarware.util.player.MoveUtil;
import com.polarware.value.Mode;
import com.polarware.value.impl.NumberValue;

public final class TickVelocity extends Mode<VelocityModule>  {

    private final NumberValue tickVelocity = new NumberValue("Tick Velocity", this, 1, 1, 6, 1);

    public TickVelocity(String name, VelocityModule parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        if ((getParent().onSwing.getValue() || getParent().onSprint.getValue()) && mc.thePlayer.swingProgress == 0) return;

        if (mc.thePlayer.hurtTime == 10 - tickVelocity.getValue().intValue()) {
            MoveUtil.stop();
        }
    };
}
