package mathax.client.systems.modules.movement.speed.modes;

import mathax.client.events.entity.player.PlayerMoveEvent;
import mathax.client.systems.modules.Modules;
import mathax.client.systems.modules.movement.AutoJump;
import mathax.client.systems.modules.movement.speed.Speed;
import mathax.client.systems.modules.movement.speed.SpeedMode;
import mathax.client.systems.modules.movement.speed.SpeedModes;
import mathax.client.systems.modules.world.Timer;
import mathax.client.utils.player.PlayerUtils;

public class MineBerry extends SpeedMode {
    public MineBerry() {
        super(SpeedModes.MineBerry);
    }

    @Override
    public boolean onMove(PlayerMoveEvent event) {
        mc.options.jumpKey.setPressed(false);
        if (PlayerUtils.isMoving()) {
            (Modules.get().get(AutoJump.class)).toggle();
        }

        if (mc.player.fallDistance <= 0.1)
            Modules.get().get(Timer.class).setOverride(1.2f);
        else if (mc.player.fallDistance < 1.3)
            Modules.get().get(Timer.class).setOverride(0.7f);
        else
            Modules.get().get(Timer.class).setOverride(1.0f);
        return false;
    }

    @Override
    public void onDeactivate() {
        (Modules.get().get(AutoJump.class)).forceToggle(false);
    }

    @Override
    public void onRubberband() {
        (Modules.get().get(Speed.class)).forceToggle(false);
    }
}
