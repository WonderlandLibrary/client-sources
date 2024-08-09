package mathax.client.systems.modules.movement.speed.modes;

import mathax.client.eventbus.EventHandler;
import mathax.client.systems.modules.movement.speed.SpeedMode;
import mathax.client.systems.modules.movement.speed.SpeedModes;
import mathax.client.utils.player.PlayerUtils;

public class Inn3rstellarSpeed extends SpeedMode {

    public Inn3rstellarSpeed() {
        super(SpeedModes.Inn3rstellarSpeed);
    }

    @EventHandler
    public boolean onTick() {
        if(mc.player.isOnGround() && PlayerUtils.isMoving()) {

            mc.player.jump();
            mc.player.setSprinting(true);
            mc.player.airStrafingSpeed = 0.225f;

        }


     return false;
    }

}

