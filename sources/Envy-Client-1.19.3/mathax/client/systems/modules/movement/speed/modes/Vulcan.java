package mathax.client.systems.modules.movement.speed.modes;

import mathax.client.eventbus.EventHandler;
import mathax.client.systems.modules.Modules;
import mathax.client.systems.modules.movement.speed.Speed;
import mathax.client.systems.modules.movement.speed.SpeedMode;
import mathax.client.systems.modules.movement.speed.SpeedModes;
import mathax.client.utils.algorithms.extra.MovementUtils;
import mathax.client.utils.player.PlayerUtils;

public class Vulcan extends SpeedMode {
    int ticks;

    public Vulcan() {
        super(SpeedModes.Vulcan);
    }

    @EventHandler
    public void onEnable() {
        ticks = 0;
    }

    @Override
    public boolean onTick() {

        mc.options.jumpKey.setPressed(false);
        if (mc.player.isOnGround() && PlayerUtils.isMoving()) {
            ticks = 0;
            mc.player.jump();

            MovementUtils.Vulcanstrafe();
            if (MovementUtils.getSpeed() < 0.5f) {
                MovementUtils.VulcanMoveStrafe(0.484f);
            }
        }

        if (!mc.player.isOnGround()) {
            ticks++;
        }
        if (ticks == 4) {
            mc.player.setVelocity(mc.player.getVelocity().getX(), mc.player.getVelocity().getY() - 0.17, mc.player.getVelocity().getZ());
        }

        if (ticks == 1) {
            MovementUtils.strafe(0.33f);
        }
        return false;
    }
    @Override
    public void onRubberband() {
        if (settings.rubberband.get()) {
            (Modules.get().get(Speed.class)).forceToggle(false);
        }
    }
}

