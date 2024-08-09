package im.expensive.utils.player;

import im.expensive.events.EventInput;
import im.expensive.events.MovingEvent;
import im.expensive.utils.client.IMinecraft;
import lombok.experimental.UtilityClass;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;

@UtilityClass
public class MoveUtils implements IMinecraft {

    public boolean isMoving() {
        return mc.player.movementInput.moveForward != 0f || mc.player.movementInput.moveStrafe != 0f;
    }
    public static void fixMovement(final EventInput event, float yaw) {
        final float forward = event.getForward();
        final float strafe = event.getStrafe();

        final double angle = MathHelper.wrapDegrees(Math.toDegrees(direction(mc.player.isElytraFlying() ? yaw : mc.player.rotationYaw, forward, strafe)));

        if (forward == 0 && strafe == 0) {
            return;
        }

        float closestForward = 0, closestStrafe = 0, closestDifference = Float.MAX_VALUE;

        for (float predictedForward = -1F; predictedForward <= 1F; predictedForward += 1F) {
            for (float predictedStrafe = -1F; predictedStrafe <= 1F; predictedStrafe += 1F) {
                if (predictedStrafe == 0 && predictedForward == 0) continue;

                final double predictedAngle = MathHelper.wrapDegrees(Math.toDegrees(direction(yaw, predictedForward, predictedStrafe)));
                final double difference = Math.abs(angle - predictedAngle);

                if (difference < closestDifference) {
                    closestDifference = (float) difference;
                    closestForward = predictedForward;
                    closestStrafe = predictedStrafe;
                }
            }
        }

        event.setForward(closestForward);
        event.setStrafe(closestStrafe);
    }
    public static double direction(float rotationYaw, final double moveForward, final double moveStrafing) {
        if (moveForward < 0F) rotationYaw += 180F;

        float forward = 1F;

        if (moveForward < 0F) forward = -0.5F;
        else if (moveForward > 0F) forward = 0.5F;

        if (moveStrafing > 0F) rotationYaw -= 90F * forward;
        if (moveStrafing < 0F) rotationYaw += 90F * forward;

        return Math.toRadians(rotationYaw);
    }
    public double getMotion() {
        return Math.hypot(mc.player.getMotion().x, mc.player.getMotion().z);
    }

    public void setMotion(final double speed) {
        if (!isMoving())
            return;

        final double yaw = getDirection(true);
        mc.player.setMotion(-Math.sin(yaw) * speed, mc.player.motion.y, Math.cos(yaw) * speed);
    }

    public static boolean isBlockUnder(float under) {
        if (mc.player.getPosY() < 0.0) {
            return false;
        } else {
            AxisAlignedBB aab = mc.player.getBoundingBox().offset(0.0, -under, 0.0);
            return mc.world.getCollisionShapes(mc.player, aab).toList().isEmpty();
        }
    }

    public double getDirection(final boolean toRadians) {
        float rotationYaw = mc.player.rotationYaw;
        if (mc.player.moveForward < 0F)
            rotationYaw += 180F;
        float forward = 1F;
        if (mc.player.moveForward < 0F)
            forward = -0.5F;
        else if (mc.player.moveForward > 0F)
            forward = 0.5F;

        if (mc.player.moveStrafing > 0F)
            rotationYaw -= 90F * forward;
        if (mc.player.moveStrafing < 0F)
            rotationYaw += 90F * forward;

        return toRadians ? Math.toRadians(rotationYaw) : rotationYaw;
    }

    public static class MoveEvent {
        public static void setMoveMotion(final MovingEvent move, final double motion) {
            double forward = mc.player.movementInput.moveForward;
            double strafe = mc.player.movementInput.moveStrafe;
            float yaw = mc.player.rotationYaw;
            if (forward == 0 && strafe == 0) {
                move.getMotion().x = 0;
                move.getMotion().z = 0;
            } else {
                if (forward != 0) {
                    if (strafe > 0) {
                        yaw += (float) (forward > 0 ? -45 : 45);
                    } else if (strafe < 0) {
                        yaw += (float) (forward > 0 ? 45 : -45);
                    }
                    strafe = 0;
                    if (forward > 0) {
                        forward = 1;
                    } else if (forward < 0) {
                        forward = -1;
                    }
                }
                move.getMotion().x = forward * motion * MathHelper.cos((float) Math.toRadians(yaw + 90.0f))
                        + strafe * motion * MathHelper.sin((float) Math.toRadians(yaw + 90.0f));
                move.getMotion().z = forward * motion * MathHelper.sin((float) Math.toRadians(yaw + 90.0f))
                        - strafe * motion * MathHelper.cos((float) Math.toRadians(yaw + 90.0f));
            }
        }
    }
}
