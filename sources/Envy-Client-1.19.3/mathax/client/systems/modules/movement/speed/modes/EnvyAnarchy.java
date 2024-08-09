package mathax.client.systems.modules.movement.speed.modes;

import mathax.client.events.entity.player.PlayerMoveEvent;
import mathax.client.mixininterface.IVec3d;
import mathax.client.systems.modules.Modules;
import mathax.client.systems.modules.movement.speed.Speed;
import mathax.client.systems.modules.movement.speed.SpeedMode;
import mathax.client.systems.modules.movement.speed.SpeedModes;
import mathax.client.utils.player.PlayerUtils;
import net.minecraft.util.math.Vec3d;

public class EnvyAnarchy extends SpeedMode {

    public EnvyAnarchy() {
        super(SpeedModes.EnvyAnarchy);
    }

    @Override
    public boolean onMove(PlayerMoveEvent event) {
        Vec3d vel = PlayerUtils.getHorizontalVelocity(settings.EnvyAnarchy.get());
        double velX = vel.getX() * 1.5;
        double velZ = vel.getZ() * 1.5;
        double velY = vel.getY() - 1;

        if (mc.options.jumpKey.isPressed()) {
            mc.options.jumpKey.setPressed(false);
        }
        mc.player.setVelocity(velX, velY, velZ);


        ((IVec3d) event.movement).set(velX, event.movement.y, velZ);
        return false;
    }
    @Override
    public void onRubberband() {
        if (settings.rubberband.get()) {
            (Modules.get().get(Speed.class)).forceToggle(false);
        }
    }
}

