package digital.rbq.module.implement.Movement.fly;

import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityMinecart;
import digital.rbq.event.PreUpdateEvent;
import digital.rbq.event.UpdateEvent;
import digital.rbq.module.SubModule;
import digital.rbq.module.implement.Movement.Fly;
import digital.rbq.module.implement.Movement.Speed;
import digital.rbq.utility.PlayerUtils;

public class Boat extends SubModule {
    public Boat() {
        super("Boat", "Fly");
    }

    @EventTarget
    public void onPre(UpdateEvent e) {
        Entity ridingEntity = mc.thePlayer.ridingEntity;
        if (ridingEntity != null) {
            if (mc.gameSettings.keyBindJump.pressed) {
                ridingEntity.motionY = Fly.speed.getValue() * 0.5;
            } else {
                if (ridingEntity.motionY < 0)
                    ridingEntity.motionY = -ridingEntity.motionY / 2;
            }
        }

        ridingEntity.rotationYaw = mc.thePlayer.rotationYaw;
        ridingEntity.motionX += 10;
    }

    /**
     * Set Entity Move Speed
     */
    public void setMotion(Entity entity, double speed) {
        double forward = mc.thePlayer.movementInput.moveForward;
        double strafe = mc.thePlayer.movementInput.moveStrafe;
        float yaw = entity.rotationYaw;
        if ((forward == 0.0D) && (strafe == 0.0D)) {
            entity.motionX = entity.motionZ = 0;
        } else {
            if (forward != 0.0D) {
                if (strafe > 0.0D) {
                    yaw += (forward > 0.0D ? -45 : 45);
                } else if (strafe < 0.0D) {
                    yaw += (forward > 0.0D ? 45 : -45);
                }
                strafe = 0.0D;
                if (forward > 0.0D) {
                    forward = 1;
                } else if (forward < 0.0D) {
                    forward = -1;
                }
            }

            double cos = Math.cos(Math.toRadians(yaw + 90.0F));
            double sin = Math.sin(Math.toRadians(yaw + 90.0F));
            entity.motionX = forward * speed * cos + strafe * speed * sin;
            entity.motionZ = forward * speed * sin - strafe * speed * cos;
        }
    }
}
