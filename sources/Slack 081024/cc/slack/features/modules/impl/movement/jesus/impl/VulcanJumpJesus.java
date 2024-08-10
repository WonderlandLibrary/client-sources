package cc.slack.features.modules.impl.movement.jesus.impl;

import cc.slack.events.State;
import cc.slack.events.impl.player.MotionEvent;
import cc.slack.events.impl.player.StrafeEvent;
import cc.slack.features.modules.impl.movement.jesus.IJesus;
import cc.slack.utils.player.MovementUtil;
import net.minecraft.potion.Potion;

public class VulcanJumpJesus implements IJesus {

    private int waterTicks = 0;

    @Override
    public void onMotion(MotionEvent event) {
        if (event.getState() == State.PRE) {
            if (mc.thePlayer.isInWater()) {

                waterTicks = 0;
                MovementUtil.strafe((float) (.3 - Math.random() / 1000));

                if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
                    MovementUtil.strafe((float) (.03 * (1 + (mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier())) + .34 - Math.random() / 1000));
                }
                waterTicks++;
            }

            waterTicks++;
        }
    }

    @Override
    public void onStrafe(StrafeEvent event) {
        if (mc.thePlayer.isInWater() && waterTicks < 10) {
            mc.thePlayer.motionY = .99 - Math.random() / 1000;
            waterTicks++;
        }
    }

    @Override
    public String toString() {
        return "Vulcan Jump";
    }
}
