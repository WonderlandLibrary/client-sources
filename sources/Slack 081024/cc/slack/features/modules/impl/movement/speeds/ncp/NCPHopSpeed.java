// Slack Client (discord.gg/slackclient)

package cc.slack.features.modules.impl.movement.speeds.ncp;

import cc.slack.events.impl.player.UpdateEvent;
import cc.slack.features.modules.impl.movement.speeds.ISpeed;
import cc.slack.utils.player.MovementUtil;
import cc.slack.utils.player.PlayerUtil;
import net.minecraft.potion.Potion;

public class NCPHopSpeed implements ISpeed {



    @Override
    public void onUpdate(UpdateEvent event) {
        if (mc.thePlayer.onGround) {
            if (MovementUtil.isMoving()) {
                mc.thePlayer.jump();
                double baseSpeed = 0.484;
                if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
                    double amplifier = mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
                    baseSpeed *= 1.0 + 0.13 * (amplifier + 1);
                }
                MovementUtil.minLimitStrafe((float) baseSpeed);

            }
        } else {
            mc.thePlayer.motionX *= 1.001;
            mc.thePlayer.motionZ *= 1.001;

            if (mc.thePlayer.offGroundTicks == 5) {
                mc.thePlayer.motionY = PlayerUtil.HEAD_HITTER_MOTIONY;
            }
            MovementUtil.strafe();
        }

    }

    @Override
    public String toString() {
        return "NCP Hop";
    }
}
