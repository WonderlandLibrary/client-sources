package cc.slack.features.modules.impl.movement.spiders.impl;

import cc.slack.start.Slack;
import cc.slack.events.impl.player.MotionEvent;

import cc.slack.features.modules.impl.movement.Spider;
import cc.slack.features.modules.impl.movement.spiders.ISpider;

public class NormalSpider implements ISpider {

    private boolean isMotionStop = true;

    @Override
    public void onMotion(MotionEvent event) {
        Spider spider = Slack.getInstance().getModuleManager().getInstance(Spider.class);
        if (mc.thePlayer.isCollidedHorizontally) {
            mc.thePlayer.motionY = spider.spiderSpeedValue.getValue();
            isMotionStop = false;
        } else if (!isMotionStop) {
            mc.thePlayer.motionY = 0;
            isMotionStop = true;
        }
    }

    @Override
    public void onDisable() {
        isMotionStop = true;
    }


    @Override
    public String toString() {
        return "Normal";
    }
}
