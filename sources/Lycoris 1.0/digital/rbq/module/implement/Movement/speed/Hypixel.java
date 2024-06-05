package digital.rbq.module.implement.Movement.speed;


import com.darkmagician6.eventapi.EventTarget;
import digital.rbq.event.MoveEvent;
import digital.rbq.event.PreUpdateEvent;
import digital.rbq.module.ModuleManager;
import digital.rbq.module.SubModule;
import digital.rbq.module.implement.Combat.KillAura;
import digital.rbq.module.implement.Combat.TargetStrafe;
import digital.rbq.module.implement.Movement.Speed;
import digital.rbq.utility.MoveUtils;
import digital.rbq.utility.PlayerUtils;

public class Hypixel extends SubModule {
    public Hypixel() {
        super("Hypixel", "Speed");
    }

    @EventTarget
    public void onPre(PreUpdateEvent e) {
        if (PlayerUtils.isMoving()) {
            if (MoveUtils.isOnGround(0.01) && mc.thePlayer.isCollidedVertically) {
                Speed.setMotion(null, Math.max(0.275, MoveUtils.defaultSpeed() * 0.9));
                mc.thePlayer.jump();
            } else if(!Speed.onGround.getValue()){
                if ((ModuleManager.targetStrafeModule.isEnabled() && (!TargetStrafe.jumpKey.getValueState() || mc.gameSettings.keyBindJump.isKeyDown()) && KillAura.target != null && KillAura.target.getDistanceToEntity(mc.thePlayer) < TargetStrafe.range.getValue()) || PlayerUtils.getSpeed() < PlayerUtils.getBaseMoveSpeed() * 0.7) {
                    Speed.setMotion(null, MoveUtils.defaultSpeed() * 0.9);
                } else if(!Speed.onGround.getValue()){
                    Speed.setMotion(null, PlayerUtils.getSpeed());
                }
            }
        }
    }

    @EventTarget
    public void onMove(MoveEvent e) {
        if (PlayerUtils.isMoving()) {

        }
    }
}
