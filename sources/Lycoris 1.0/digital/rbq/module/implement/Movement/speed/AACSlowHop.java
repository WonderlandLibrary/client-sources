package digital.rbq.module.implement.Movement.speed;

import com.darkmagician6.eventapi.EventTarget;
import digital.rbq.module.implement.Movement.Speed;
import digital.rbq.event.MoveEvent;
import digital.rbq.event.PostUpdateEvent;
import digital.rbq.module.SubModule;
import digital.rbq.utility.BlockUtils;
import digital.rbq.utility.PlayerUtils;

public class AACSlowHop extends SubModule {
    public AACSlowHop() {
        super("AAC4", "Speed");
    }

    public void onEnable() {
        super.onEnable();
    }

    @Override
    public void onDisable() {
        mc.timer.timerSpeed = 1;
        mc.thePlayer.speedInAir = 0.02f;
        super.onDisable();
    }

    @EventTarget
    public void onUpdate(PostUpdateEvent event) {
        if(mc.thePlayer.isInWater())
            return;

        if(PlayerUtils.isMoving()) {
            if(mc.thePlayer.onGround) {
                mc.thePlayer.jump();
                mc.thePlayer.speedInAir = 0.02f;
            }
            if(mc.thePlayer.fallDistance > 0.7 && mc.thePlayer.fallDistance < 1.3) {
                mc.thePlayer.speedInAir = 0.02f;
                mc.timer.timerSpeed = 1.04f;
            }
        }else{
            mc.thePlayer.motionX = 0D;
            mc.thePlayer.motionZ = 0D;
        }
    }

    @EventTarget
    public void onMove(MoveEvent event) {

    }
}
