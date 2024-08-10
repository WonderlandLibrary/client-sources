// Slack Client (discord.gg/slackclient)

package cc.slack.features.modules.impl.movement.speeds.vulcan;

import cc.slack.events.impl.player.UpdateEvent;
import cc.slack.features.modules.impl.movement.speeds.ISpeed;
import cc.slack.utils.other.MathUtil;
import cc.slack.utils.player.MovementUtil;

public class VulcanLowSpeed implements ISpeed {


    double launchY = 0.0D;

    @Override
    public void onEnable() {
    launchY = mc.thePlayer.motionY;
    }

    @Override
    public void onUpdate(UpdateEvent event) {
        if (mc.thePlayer.onGround && MovementUtil.isMoving()) {
            mc.thePlayer.jump();
            MovementUtil.strafe((float) MathUtil.getRandomInRange(0.54F, 0.56F));

            launchY = mc.thePlayer.posY;
        } else if(mc.thePlayer.motionY > 0.2) {
            mc.thePlayer.motionY = -0.0784000015258789;
        }
    }

    @Override
    public String toString() {
        return "Vulcan Low";
    }
}
