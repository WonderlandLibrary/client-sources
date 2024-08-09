package mathax.client.systems.modules.movement.speed.modes;

import mathax.client.events.entity.player.PlayerMoveEvent;
import mathax.client.mixin.ClientPlayNetworkHandlerMixin;
import mathax.client.mixininterface.IVec3d;
import mathax.client.systems.modules.Module;
import mathax.client.systems.modules.Modules;
import mathax.client.systems.modules.movement.AutoJump;
import mathax.client.systems.modules.movement.speed.Speed;
import mathax.client.systems.modules.movement.speed.SpeedMode;
import mathax.client.systems.modules.movement.speed.SpeedModes;
import mathax.client.systems.modules.world.Timer;
import mathax.client.utils.algorithms.extra.MovementUtils;
import mathax.client.utils.player.PlayerUtils;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.border.WorldBorder;

import static mathax.client.utils.misc.ChatUtils.info;

public class _5b5t extends SpeedMode {


    int ticks;

    public _5b5t() {
        super(SpeedModes._5b5t);
    }

    @Override
    public boolean onMove(PlayerMoveEvent event) {

        if (PlayerUtils.isMoving()) {
            (Modules.get().get(AutoJump.class)).toggle();
        }
        if (!PlayerUtils.isMoving()) {
            (Modules.get().get(AutoJump.class)).forceToggle(false);
        }

        if (mc.player.isOnGround() && PlayerUtils.isMoving()) {
            ticks = 0;

            MovementUtils.Vulcanstrafe();
/*            if (MovementUtils.getSpeed() < 0.5f) {
                MovementUtils.VulcanMoveStrafe(0.8f);
            }*/
            if (mc.options.forwardKey.isPressed()) {
                MovementUtils.strafe(0.33f);
            }
        }

        if (!mc.player.isOnGround()) {
            ticks++;
            Vec3d vel = PlayerUtils.getHorizontalVelocity(settings.speed5b5t.get());
            double velX = vel.getX();
            double velZ = vel.getZ();
            ((IVec3d) event.movement).set(velX, event.movement.y, velZ);
        }
        if (ticks == 4) {
            mc.player.setVelocity(mc.player.getVelocity().getX(), mc.player.getVelocity().getY() - 0.17, mc.player.getVelocity().getZ());
        }

        if (ticks == 1) {
            MovementUtils.strafe(0.33f);
        }

        if (mc.player.fallDistance <= 0.1)
            Modules.get().get(Timer.class).setOverride(1.7f);
        else if (mc.player.fallDistance < 1.3)
            Modules.get().get(Timer.class).setOverride(0.8f);
        else
            Modules.get().get(Timer.class).setOverride(1.0f);
        return false;
    }

    @Override
    public void onDeactivate() {
        (Modules.get().get(AutoJump.class)).forceToggle(false);
    }

    @Override
    public void onActivate() {
        info(Formatting.RED + "This Speed Mode is Still in BETA and might get patched || 5b5t Speed might also work on other Servers");
    }

    @Override
    public void onRubberband() {
        (Modules.get().get(Speed.class)).forceToggle(false);
        info(Formatting.WHITE + "Speed was toggled off due to rubberbanding.");
    }
}
