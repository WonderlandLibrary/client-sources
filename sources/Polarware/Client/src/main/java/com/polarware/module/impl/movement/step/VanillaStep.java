package com.polarware.module.impl.movement.step;

import com.polarware.module.impl.movement.StepModule;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.motion.PreMotionEvent;
import com.polarware.event.impl.other.StepEvent;
import com.polarware.util.player.PlayerUtil;
import com.polarware.value.Mode;
import com.polarware.value.impl.BooleanValue;
import com.polarware.value.impl.NumberValue;

/**
 * @author Auth
 * @since 22/3/2022
 */

public class VanillaStep extends Mode<StepModule> {

    private final NumberValue height = new NumberValue("Height", this, 1, 1, 10, 0.1);
    private final BooleanValue reverse = new BooleanValue("Reverse", this, false);
    private final NumberValue timer = new NumberValue("Timer", this, 0.5, 0.1, 1, 0.1);

    public VanillaStep(String name, StepModule parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {

        mc.thePlayer.stepHeight = this.height.getValue().floatValue();

        if (!reverse.getValue() || !PlayerUtil.isBlockUnder(height.getValue().floatValue() + mc.thePlayer.getEyeHeight()) || PlayerUtil.inLiquid()) {
            return;
        }

        if (mc.thePlayer.posY < mc.thePlayer.lastGroundY) {
            if (!mc.thePlayer.onGround && mc.thePlayer.offGroundTicks <= 1) {
                mc.thePlayer.motionY = -height.getValue().doubleValue();
            }
        }

        if (mc.thePlayer.offGroundTicks == 1 && mc.thePlayer.posY < mc.thePlayer.lastLastGroundY) {
            mc.timer.timerSpeed = timer.getValue().floatValue();
        }
    };

    @Override
    public void onDisable() {
        mc.thePlayer.stepHeight = 0.6F;
    }

    @EventLink()
    public final Listener<StepEvent> onStep = event -> {

        if (event.getHeight() > 0.6) {
            mc.timer.timerSpeed = timer.getValue().floatValue();
        }
    };
}