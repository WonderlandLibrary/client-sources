package cc.slack.features.modules.impl.movement.speeds.ncp;

import cc.slack.events.impl.player.UpdateEvent;
import cc.slack.features.modules.impl.movement.speeds.ISpeed;
import cc.slack.utils.player.MovementUtil;

public class OldNCPSpeed implements ISpeed {

    @Override
    public void onUpdate(UpdateEvent event) {
        if(MovementUtil.isMoving()) {
            if(mc.thePlayer.onGround) {
                if(mc.thePlayer.hurtTime != 0)
                    MovementUtil.setMotionSpeed(MovementUtil.getSpeed()*1.075);
                    MovementUtil.setMotionSpeed(MovementUtil.getSpeed()*1.08);
                    MovementUtil.setMotionSpeed(MovementUtil.getSpeed()*MovementUtil.getSpeedPotMultiplier(0.1));
                    mc.thePlayer.jump();
            } else {
                mc.thePlayer.jumpMovementFactor = 0.0265f*MovementUtil.getSpeedPotMultiplier(0.15);
                MovementUtil.setMotionSpeed(MovementUtil.getSpeed());
            }
        } else {
            mc.thePlayer.motionX = mc.thePlayer.motionZ = 0.0;
        }
    }

    @Override
    public String toString() {
        return "Old NCP";
    }

}
