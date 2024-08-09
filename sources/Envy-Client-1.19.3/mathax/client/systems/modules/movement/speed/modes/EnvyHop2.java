package mathax.client.systems.modules.movement.speed.modes;

import mathax.client.eventbus.EventHandler;
import mathax.client.systems.modules.Modules;
import mathax.client.systems.modules.movement.speed.SpeedMode;
import mathax.client.systems.modules.movement.speed.SpeedModes;
import mathax.client.systems.modules.world.Timer;
import mathax.client.utils.player.PlayerUtils;

public class EnvyHop2 extends SpeedMode {
    int ticks;
    public EnvyHop2() {
        super(SpeedModes.EnvyHop2);
    }

    @EventHandler
    public void onActivate() {
        ticks = 0;
    }

    @Override
    public boolean onTick() {
        mc.options.jumpKey.setPressed(false);
        if (mc.player.isOnGround() && PlayerUtils.isMoving()) {
            mc.player.jump();
            ticks = 0;
        }
        if (!mc.player.isOnGround()) {
            ticks++;
        }
        if (ticks == 3) {
            mc.player.jump();
        }
        if (ticks >= 5) {
            mc.player.setVelocity(mc.player.getVelocity().getX(), mc.player.getVelocity().getY() - 0.17, mc.player.getVelocity().getZ());
        }
        if (ticks == 1 || ticks == 2) {
            mc.player.airStrafingSpeed = 0.05f;
        }
        if (ticks == 4 || ticks > 8) {
            Modules.get().get(Timer.class).setOverride(2.2f);
        }
        return false;
    }
}
