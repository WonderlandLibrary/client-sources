package mathax.client.systems.modules.movement.speed.modes;

import mathax.client.systems.modules.Modules;
import mathax.client.systems.modules.movement.speed.Speed;
import mathax.client.systems.modules.movement.speed.SpeedMode;
import mathax.client.systems.modules.movement.speed.SpeedModes;

public class OnGround extends SpeedMode {
    public OnGround() {
        super(SpeedModes.OnGround);
    }

    public boolean onTick() {
        if (mc.player.isOnGround() == false) {
            mc.player.setVelocity(mc.player.getVelocity().x, -0.9, mc.player.getVelocity().z);
            mc.player.setPos(mc.player.getX(), mc.player.prevY, mc.player.getZ());
            mc.player.setOnGround(true);
        }

        if (mc.player.isOnGround() == false) {
            mc.player.setVelocity(mc.player.getVelocity().x, -3, mc.player.getVelocity().z);
        }

        if (mc.player.forwardSpeed != 0.0F && !mc.player.horizontalCollision) {
            if (mc.player.verticalCollision) {
                mc.player.setVelocity(mc.player.getVelocity().x, mc.player.getVelocity().y, mc.player.getVelocity().z);
                mc.player.jump();
                // 1.0379
            }

            if (mc.player.isOnGround() == false && mc.player.getY() >= mc.player.prevY + 0.399994D) {
                mc.player.setVelocity(mc.player.getVelocity().x, -100, mc.player.getVelocity().z);
                mc.player.setOnGround(true);
            }
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
