package mathax.client.systems.modules.movement.speed.modes;

import mathax.client.eventbus.EventHandler;
import mathax.client.systems.modules.Modules;
import mathax.client.systems.modules.movement.speed.Speed;
import mathax.client.systems.modules.movement.speed.SpeedMode;
import mathax.client.systems.modules.movement.speed.SpeedModes;
import mathax.client.systems.modules.world.Timer;
import mathax.client.utils.player.PlayerUtils;

public class ChonkyChineseSped extends SpeedMode {
    public ChonkyChineseSped() {super(SpeedModes.ChonkyChineseSped);}

    @EventHandler
    public boolean onTick(){
        if (mc.player.isOnGround() && PlayerUtils.isMoving()) {
            mc.player.jump();
        }if (mc.player.fallDistance <= 0.4){
            mc.player.airStrafingSpeed = 2;
        }if (mc.player.fallDistance <= 0.1)
            Modules.get().get(Timer.class).setOverride(1.7f);
        else if (mc.player.fallDistance < 1.3)
            Modules.get().get(Timer.class).setOverride(0.8f);
        else
            Modules.get().get(Timer.class).setOverride(1.0f);


        return false;
    }
    @Override
    public void onRubberband() {
        if (settings.rubberband.get()) {
            (Modules.get().get(Speed.class)).forceToggle(false);
        }
    }
}
