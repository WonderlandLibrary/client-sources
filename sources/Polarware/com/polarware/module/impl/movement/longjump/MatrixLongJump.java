package com.polarware.module.impl.movement.longjump;

import com.polarware.component.impl.player.BlinkComponent;
import com.polarware.module.impl.movement.LongJumpModule;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.motion.PreMotionEvent;
import com.polarware.util.interfaces.InstanceAccess;
import com.polarware.value.Mode;

/**
 * @author Alan, (Nicklas Implemtentet cause yes)
 * @since 08.04.2022
 */
public class MatrixLongJump extends Mode<LongJumpModule> {

    private double lastMotion;
    private int ticks;

    public MatrixLongJump(String name, LongJumpModule parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {

        if (!InstanceAccess.mc.thePlayer.onGround)
            ticks++;
        if (InstanceAccess.mc.thePlayer.onGround)
            InstanceAccess.mc.thePlayer.jump();
        if (ticks % 12 == 0 || InstanceAccess.mc.thePlayer.isCollidedVertically)
            lastMotion = InstanceAccess.mc.thePlayer.motionY;
        InstanceAccess.mc.thePlayer.motionY = lastMotion;
        if (InstanceAccess.mc.thePlayer.motionY < 0.1)
            getModule(LongJumpModule.class).setEnabled(false);
    };

    @Override
    public void onEnable() {
        this.ticks = 0;
        this.lastMotion = 0;
        BlinkComponent.blinking = true;
    }

    @Override
    public void onDisable() {
        BlinkComponent.blinking = false;
    }
}