package mathax.client.systems.modules.movement.speed.modes;

import mathax.client.systems.modules.Modules;
import mathax.client.systems.modules.movement.speed.Speed;
import mathax.client.systems.modules.movement.speed.SpeedMode;
import mathax.client.systems.modules.movement.speed.SpeedModes;

public class LegitHop  extends SpeedMode {

    public LegitHop() {
        super(SpeedModes.LegitHop);
    }

    @Override
    public boolean onTick() {
        mc.options.jumpKey.setPressed(false);
        if (mc.player.isOnGround()) {
            mc.player.jump();
            mc.player.setSprinting(true);
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
