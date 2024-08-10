// Slack Client (discord.gg/slackclient)

package cc.slack.features.modules.impl.movement.speeds.vulcan;

import cc.slack.events.impl.player.UpdateEvent;
import cc.slack.features.modules.impl.movement.speeds.ISpeed;
import cc.slack.utils.player.MovementUtil;

public class VulcanHopSpeed implements ISpeed {

    boolean modifiedTimer;

    @Override
    public void onUpdate(UpdateEvent event) {
        if (modifiedTimer) {
            mc.timer.timerSpeed = 1.00f;
            modifiedTimer = false;
        }

        if (MovementUtil.getSpeed() < 0.215f && !mc.thePlayer.onGround) {
            MovementUtil.strafe(0.215f);
        }

        if (mc.thePlayer.onGround && MovementUtil.isMoving()) {
            mc.thePlayer.jump();

            mc.timer.timerSpeed = 1.25f;
            modifiedTimer = true;

            MovementUtil.minLimitStrafe(0.4849f);

        } else if (!MovementUtil.isMoving()) {
            mc.timer.timerSpeed = 1.00f;
            MovementUtil.resetMotion();
        }
    }

    @Override
    public String toString() {
        return "Vulcan Hop";
    }
}
