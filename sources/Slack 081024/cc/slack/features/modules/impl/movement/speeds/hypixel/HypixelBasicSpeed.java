// Slack Client (discord.gg/slackclient)

package cc.slack.features.modules.impl.movement.speeds.hypixel;

import cc.slack.events.impl.player.UpdateEvent;
import cc.slack.features.modules.impl.movement.speeds.ISpeed;
import cc.slack.utils.player.MovementUtil;
import cc.slack.utils.player.PlayerUtil;
import net.minecraft.potion.Potion;


public class HypixelBasicSpeed implements ISpeed {

    int jumpTick = 0;

    @Override
    public void onUpdate(UpdateEvent event) {
        if (mc.thePlayer.onGround) {
            if (MovementUtil.isMoving()) {
                if (jumpTick > 6) jumpTick = 4;
                mc.thePlayer.jump();
                MovementUtil.strafe(0.48f + jumpTick * 0.0080f);
                if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
                    float amplifier = mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
                    MovementUtil.strafe(0.47f + jumpTick * 0.005f + 0.024f * (amplifier + 1));
                }
                mc.thePlayer.motionY = PlayerUtil.getJumpHeight();
            } else {
                jumpTick = 0;
            }
        } else {
            mc.thePlayer.motionX *= 1.0005;
            mc.thePlayer.motionZ *= 1.0005;
        }

    }

    @Override
    public String toString() {
        return "Hypixel Basic";
    }
}
