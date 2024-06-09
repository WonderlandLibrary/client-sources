package ez.cloudclient.module.modules.movement;

import ez.cloudclient.module.Module;
import ez.cloudclient.util.MathUtil;

public class ElytraFlight extends Module {
    public float speed = 1;

    public ElytraFlight() {
        super("ElytraFlight", Module.Category.MOVEMENT, "Fly using Elytra");
    }

    @Override
    protected void onEnable() {
        if (mc.player != null) {
            mc.player.capabilities.isFlying = true;
        }
    }

    @Override
    protected void onDisable() {
        if (mc.player != null) {
            mc.player.capabilities.isFlying = false;
        }
    }

    @Override
    public void onTick() {
        if (mc.player.isElytraFlying()) {
            final double[] directionSpeed = MathUtil.directionSpeed(this.speed);
            mc.player.motionY = 0;
            mc.player.motionX = 0;
            mc.player.motionZ = 0;
            if (mc.player.movementInput.jump) {
                mc.player.motionY = this.speed / 2;
            } else if (mc.player.movementInput.sneak) {
                mc.player.motionY = -this.speed / 2;
            }
            if (mc.player.movementInput.moveStrafe != 0 || mc.player.movementInput.moveForward != 0) {
                mc.player.motionX = directionSpeed[0];
                mc.player.motionZ = directionSpeed[1];
            }
        }
    }
}
