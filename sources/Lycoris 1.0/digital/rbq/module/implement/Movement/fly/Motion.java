package digital.rbq.module.implement.Movement.fly;

import com.darkmagician6.eventapi.EventTarget;
import digital.rbq.event.UpdateEvent;
import digital.rbq.module.ModuleManager;
import digital.rbq.module.SubModule;
import digital.rbq.module.implement.Combat.KillAura;
import digital.rbq.module.implement.Combat.TargetStrafe;
import digital.rbq.utility.PlayerUtils;
import digital.rbq.module.value.FloatValue;

public class Motion extends SubModule {
    public static FloatValue speed = new FloatValue("Fly", "Motion Speed", 0.5f, 0.1f, 3.0f, 0.1f);

    public Motion() {
        super("Motion", "Fly");
    }

    @EventTarget
    public void onUpdate(UpdateEvent e) {
        mc.thePlayer.motionX = 0;
            mc.thePlayer.motionY = 0;
        mc.thePlayer.motionZ = 0;

        if (mc.gameSettings.keyBindJump.isKeyDown()) {
            mc.thePlayer.motionY = speed.getValueState();
        }

        if (mc.gameSettings.keyBindSneak.isKeyDown()) {
            mc.thePlayer.motionY = -speed.getValueState();
        }

        if (PlayerUtils.isMoving()) {
            if (KillAura.target != null) {
                mc.timer.timerSpeed = 1;
                TargetStrafe.move(null, speed.getValueState() * 3, KillAura.target);
            } else {
                PlayerUtils.setSpeed(speed.getValueState() * 3);
            }
            PlayerUtils.setSpeed(speed.getValueState() * 3);
        }
    }

    @Override
    public void onDisable() {
        mc.thePlayer.motionX = 0;
        mc.thePlayer.motionY = 0;
        mc.thePlayer.motionZ = 0;
        super.onDisable();
    }
}