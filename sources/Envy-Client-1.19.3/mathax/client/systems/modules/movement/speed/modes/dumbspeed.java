package mathax.client.systems.modules.movement.speed.modes;

import mathax.client.eventbus.EventHandler;
import mathax.client.systems.modules.Modules;
import mathax.client.systems.modules.movement.speed.Speed;
import mathax.client.systems.modules.movement.speed.SpeedMode;
import mathax.client.systems.modules.movement.speed.SpeedModes;
import mathax.client.utils.player.PlayerUtils;

public class dumbspeed extends SpeedMode {

    public dumbspeed() {
        super(SpeedModes.dumbspeed);
    }
    @EventHandler
    public boolean onTick() {
        double velx = mc.player.getVelocity().x * settings.dumbspeed.get();
        double velz = mc.player.getVelocity().z * settings.dumbspeed.get();
        double xlim = mc.player.getVelocity().x;
        double zlim = mc.player.getVelocity().z;



        if (mc.player.isOnGround() && PlayerUtils.isMoving()) {
            mc.player.jump();

            mc.player.setVelocity(velx, mc.player.getVelocity().y, velz);
        }
        if (mc.player.fallDistance >= 4 && !mc.player.isSprinting()) {

            mc.player.setVelocity(mc.player.getVelocity().x, mc.player.getVelocity().y -4, mc.player.getVelocity().z);

        }
        if (xlim > 1 || zlim > 1) {
            mc.player.setVelocity(1, mc.player.getVelocity().y, 1);
        }
        if (xlim > 1 && zlim > 1) {
            mc.player.setVelocity(1, mc.player.getVelocity().y, 1);
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
