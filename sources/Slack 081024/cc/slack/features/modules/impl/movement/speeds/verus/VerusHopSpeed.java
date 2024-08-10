// Slack Client (discord.gg/slackclient)

package cc.slack.features.modules.impl.movement.speeds.verus;

import cc.slack.events.impl.player.MoveEvent;
import cc.slack.features.modules.impl.movement.speeds.ISpeed;
import cc.slack.utils.player.MovementUtil;
import cc.slack.utils.player.PlayerUtil;

public class VerusHopSpeed implements ISpeed {

    double moveSpeed;
    int airTicks;

    @Override
    public void onEnable() {
        moveSpeed = 0;
        airTicks = 0;
    }

    @Override
    public void onMove(MoveEvent event) {
        if (mc.thePlayer.onGround) {
            if (MovementUtil.isMoving()) event.setY(0.42F);
            moveSpeed = 0.66f;
            airTicks = 0;
        } else {
            if (airTicks == 0)
                moveSpeed *= PlayerUtil.BASE_GROUND_FRICTION * 1.01;

            airTicks++;
        }

        MovementUtil.setSpeed(event, moveSpeed);
        moveSpeed *= PlayerUtil.MOVE_FRICTION;
    }

    @Override
    public String toString() {
        return "Verus Hop";
    }
}
