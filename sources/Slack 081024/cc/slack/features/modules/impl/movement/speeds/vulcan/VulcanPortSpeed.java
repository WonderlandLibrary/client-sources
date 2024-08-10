package cc.slack.features.modules.impl.movement.speeds.vulcan;

import cc.slack.events.impl.player.UpdateEvent;
import cc.slack.features.modules.impl.movement.speeds.ISpeed;
import cc.slack.utils.player.MovementUtil;

public class VulcanPortSpeed implements ISpeed {


    @Override
    public void onUpdate(UpdateEvent event) {
        mc.thePlayer.jumpMovementFactor = 0.0245f;
        if (!mc.thePlayer.onGround && mc.thePlayer.offGroundTicks > 3 && mc.thePlayer.motionY > 0) {
            mc.thePlayer.motionY = -0.27;
        }

        if (MovementUtil.getSpeed() < 0.215f && !mc.thePlayer.onGround) {
            MovementUtil.strafe(0.215f);
        }

        if (mc.thePlayer.onGround && MovementUtil.isMoving()) {
            mc.thePlayer.jump();
            if (MovementUtil.getSpeed() < 0.48f) {
                MovementUtil.strafe(0.48f);
            } else {
                MovementUtil.strafe();
            }
        } else if (!MovementUtil.isMoving()) {
            MovementUtil.resetMotion();
        }
    }

    @Override
    public String toString() {
        return "Vulcan Port";
    }
}
