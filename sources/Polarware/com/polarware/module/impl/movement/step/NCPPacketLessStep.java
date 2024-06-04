package com.polarware.module.impl.movement.step;

import com.polarware.component.impl.render.SmoothCameraComponent;
import com.polarware.module.impl.movement.StepModule;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.motion.PreMotionEvent;
import com.polarware.util.player.MoveUtil;
import com.polarware.value.Mode;
import net.minecraft.potion.Potion;

/**
 * @author Alan
 * @since 22/3/2022
 */

public class NCPPacketLessStep extends Mode<StepModule> {

    private boolean step;

    public NCPPacketLessStep(String name, StepModule parent) {
        super(name, parent);
    }

    @Override
    public void onDisable() {
        mc.thePlayer.stepHeight = 0.6F;
    }

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {

        if (
                !mc.thePlayer.isPotionActive(Potion.jump) &&
                mc.thePlayer.isCollidedHorizontally &&
                mc.thePlayer.onGround
        ) {
            mc.thePlayer.jump();
            MoveUtil.stop();
            step = true;
        }
        if(step) {
            SmoothCameraComponent.setY();
        }
        if (mc.thePlayer.offGroundTicks == 3 && step) {
            mc.thePlayer.motionY = MoveUtil.predictedMotion(mc.thePlayer.motionY, 2);
            MoveUtil.strafe(MoveUtil.WALK_SPEED);
            step = false;
        }
    };
}