package mathax.client.systems.modules.movement.speed.modes;

import mathax.client.events.entity.player.PlayerMoveEvent;
import mathax.client.mixininterface.IVec3d;
import mathax.client.systems.modules.Modules;
import mathax.client.systems.modules.movement.speed.Speed;
import mathax.client.systems.modules.movement.speed.SpeedMode;
import mathax.client.systems.modules.movement.speed.SpeedModes;

public class Weird extends SpeedMode {
    private boolean isBhopEnabled = false;

    public Weird() {
        super(SpeedModes.Weird);
    }

    @Override
    public boolean onMove(PlayerMoveEvent event) {
        if (mc.player.isOnGround() == false) {
            double velX = event.movement.x * 2;
            double velZ = event.movement.z * 2;
            double velY = 0.4;
            mc.player.setVelocity(velX, velY, velZ);
            ((IVec3d) event.movement).set(velX, event.movement.y, velZ);
        }
        return false;
    }
    @Override
    public void onRubberband() {
        (Modules.get().get(Speed.class)).forceToggle(false);
    }
}
