package cc.slack.features.modules.impl.movement.spiders.impl;

import cc.slack.events.State;
import cc.slack.events.impl.player.MotionEvent;
import cc.slack.features.modules.impl.movement.spiders.ISpider;
import cc.slack.utils.player.PlayerUtil;
import net.minecraft.block.BlockAir;

public class VerusSpider implements ISpider {

    @Override
    public void onMotion(MotionEvent event) {
        if (event.getState() == State.PRE) {
            if (mc.thePlayer.isCollidedHorizontally) {
                if (mc.thePlayer.ticksExisted % 2 == 0) {
                    mc.thePlayer.jump();
                }
            }
        }
    }

    @Override
    public String toString() {
        return "Verus";
    }
}
