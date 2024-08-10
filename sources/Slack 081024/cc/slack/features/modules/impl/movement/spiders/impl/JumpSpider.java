package cc.slack.features.modules.impl.movement.spiders.impl;

import cc.slack.events.impl.player.MotionEvent;
import cc.slack.features.modules.impl.movement.spiders.ISpider;
import cc.slack.utils.other.TimeUtil;
import cc.slack.utils.player.MovementUtil;

public class JumpSpider implements ISpider {

    private final TimeUtil timer = new TimeUtil();

    @Override
    public void onMotion(MotionEvent event) {
        if (mc.thePlayer.isCollidedHorizontally && timer.hasReached(500L)) {
            mc.thePlayer.motionY = MovementUtil.getJumpMotion(0.42F);
            event.setGround(true);
            timer.reset();
        }
    }

    @Override
    public void onDisable() {
        timer.reset();
    }

    @Override
    public String toString() {
        return "Jump";
    }
}
