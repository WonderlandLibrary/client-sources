package mathax.client.systems.modules.movement.speed.modes;

import mathax.client.events.entity.player.PlayerMoveEvent;
import mathax.client.mixininterface.IVec3d;
import mathax.client.systems.modules.Modules;
import mathax.client.systems.modules.movement.AutoJump;
import mathax.client.systems.modules.movement.speed.Speed;
import mathax.client.systems.modules.movement.speed.SpeedMode;
import mathax.client.systems.modules.movement.speed.SpeedModes;
import mathax.client.utils.player.PlayerUtils;
import net.minecraft.util.math.Vec3d;

public class VelocityHop extends SpeedMode {
    public VelocityHop() {
        super(SpeedModes.VelocityHop);
    }
    @Override
    public boolean onMove(PlayerMoveEvent event) {
        Vec3d vel = PlayerUtils.getHorizontalVelocity(settings.VelocityHop.get());
        double velX = vel.getX() * 1.5;
        double velZ = vel.getZ() * 1.5;
        double velY = vel.getY() - 0.5;
        mc.options.jumpKey.setPressed(false);
        if (PlayerUtils.isMoving()) {
            (Modules.get().get(AutoJump.class)).toggle();
        }


        mc.player.setVelocity(velX, velY, velZ);


        ((IVec3d) event.movement).set(velX, event.movement.y, velZ);
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
