// Slack Client (discord.gg/slackclient)

package cc.slack.features.modules.impl.movement.speeds.vanilla;

import cc.slack.start.Slack;
import cc.slack.events.impl.player.UpdateEvent;
import cc.slack.features.modules.impl.movement.Speed;
import cc.slack.features.modules.impl.movement.speeds.ISpeed;
import cc.slack.utils.player.MovementUtil;

public class VanillaSpeed implements ISpeed {

    @Override
    public void onUpdate(UpdateEvent event) {
        if (mc.thePlayer.onGround && MovementUtil.isMoving()) {
            mc.thePlayer.jump();
            MovementUtil.strafe(Slack.getInstance().getModuleManager().getInstance(Speed.class).vanillaspeed.getValue());
        }
        if (MovementUtil.isMoving()) {
            MovementUtil.strafe();
            if (!Slack.getInstance().getModuleManager().getInstance(Speed.class).vanillaGround.getValue()) {
                MovementUtil.strafe(Slack.getInstance().getModuleManager().getInstance(Speed.class).vanillaspeed.getValue());
            }
        } else {
            MovementUtil.resetMotion(false);
        }

    }

    @Override
    public String toString() {
        return "Vanilla Hop";
    }
}
