package digital.rbq.module.implement.Movement;

import com.darkmagician6.eventapi.EventTarget;
import digital.rbq.event.PreUpdateEvent;
import digital.rbq.module.Category;
import digital.rbq.module.Module;
import digital.rbq.module.ModuleManager;
import digital.rbq.module.implement.Misc.disabler.Hypixel;
import digital.rbq.utility.MoveUtils;

public class Strafe extends Module {
    public Strafe() {
        super("Strafe", Category.Movement, false);
    }

    @EventTarget
    private void onPre(PreUpdateEvent event) {
        if (ModuleManager.speedMod.isEnabled() || ModuleManager.longJumpMod.isEnabled())
            return;

		if (!mc.thePlayer.onGround) {
			if (mc.gameSettings.keyBindJump.pressed) {
                if ((mc.gameSettings.keyBindBack.pressed || mc.gameSettings.keyBindRight.pressed || mc.gameSettings.keyBindLeft.pressed) && Math.abs(Hypixel.yawDiff) > 20) {
                    MoveUtils.strafe(MoveUtils.getSpeed() * 0.85);
                } else {
                    MoveUtils.strafe();
                }
			}
		}
    }
}
