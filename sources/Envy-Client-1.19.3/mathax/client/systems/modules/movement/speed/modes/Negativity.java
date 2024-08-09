package mathax.client.systems.modules.movement.speed.modes;

import mathax.client.systems.modules.movement.speed.SpeedMode;
import mathax.client.systems.modules.movement.speed.SpeedModes;

public class Negativity extends SpeedMode {

    int ticks;
    public Negativity() {
        super(SpeedModes.Negativity);
    }

    @Override
    public boolean onTick() {
        mc.options.jumpKey.setPressed(false);
        ticks++;
        if (mc.player.forwardSpeed > 0.0F && mc.player.isOnGround()) {
            mc.player.jump();
        }
        if (ticks > 15 && mc.player.forwardSpeed != 0.0F) {
            mc.player.airStrafingSpeed = 0.054F;
            if (mc.options.jumpKey.isPressed()) {
                mc.options.jumpKey.setPressed(false);
            }
            if (ticks > 16 && mc.player.forwardSpeed != 0.0F) {
                mc.player.setVelocity(mc.player.getVelocity().x, 0.15, mc.player.getVelocity().z);
            }
            if (ticks > 17 && mc.player.forwardSpeed != 0.0F) {
                mc.player.setVelocity(mc.player.getVelocity().x, -0.12, mc.player.getVelocity().z);
                mc.player.airStrafingSpeed = 0.09F;
            }
            if (ticks > 18 && mc.player.forwardSpeed != 0.0F) {
                mc.player.setVelocity(mc.player.getVelocity().x, -0.1, mc.player.getVelocity().z);
            }
            if (ticks > 20 && mc.player.forwardSpeed != 0.0F) {
                ticks = 0;
            }
        }

        return false;
    }
}
