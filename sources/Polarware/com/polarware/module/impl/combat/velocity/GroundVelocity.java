package com.polarware.module.impl.combat.velocity;


import com.polarware.module.impl.combat.VelocityModule;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.input.MoveInputEvent;
import com.polarware.event.impl.motion.PreMotionEvent;
import com.polarware.value.Mode;
import com.polarware.value.impl.NumberValue;

public final class GroundVelocity extends Mode<VelocityModule>  {

    private final NumberValue delay = new NumberValue("Delay", this, 1, 0, 20, 1);

    private int ticks;

    public GroundVelocity(String name, VelocityModule parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        if ((getParent().onSwing.getValue() || getParent().onSprint.getValue()) && mc.thePlayer.swingProgress == 0) return;

        if (ticks == delay.getValue().intValue()) {
            mc.thePlayer.onGround = true;
        }

        ticks++;
    };

    @EventLink()
    public final Listener<MoveInputEvent> onMove = event -> {
        if (ticks == delay.getValue().intValue() + 1) {
            event.setJump(false);
        }
    };
}
