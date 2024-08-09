package mathax.client.systems.modules.movement.speed.modes;

import mathax.client.eventbus.EventHandler;
import mathax.client.systems.modules.movement.speed.SpeedMode;
import mathax.client.systems.modules.movement.speed.SpeedModes;
import mathax.client.utils.player.MoveUtilV;
import mathax.client.utils.player.PlayerUtils;

public class Hypixel extends SpeedMode {

    public Hypixel() {
        super(SpeedModes.Hypixel);
    }

    @EventHandler
    public boolean onTick() {
        mc.options.jumpKey.setPressed(false);
        if (PlayerUtils.isMoving()) {
            if (mc.player.isUsingItem()) return false;
            if (mc.player.isBlocking()) return false;
            if (mc.player.isSneaking()) return false;
            if (mc.player.isCrawling()) return false;
            if (mc.player.isSwimming()) return false;
            if (mc.player.isOnGround()) {
                mc.player.jump();
                MoveUtilV.strafe(0.485);
            }
        }
        return false;
    }
}
