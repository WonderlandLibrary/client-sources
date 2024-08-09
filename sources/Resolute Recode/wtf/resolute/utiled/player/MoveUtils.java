package wtf.resolute.utiled.player;

import wtf.resolute.evented.EventInput;
import wtf.resolute.evented.MovingEvent;
import wtf.resolute.utiled.client.IMinecraft;
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

    public static void setSpeed(double speed) {
        float f = mc.player.movementInput.moveForward;
        float f1 = mc.player.movementInput.moveStrafe;
        float f2 = mc.player.rotationYaw;
        if (f == 0.0F && f1 == 0.0F) {
            mc.player.motion.x = 0.0;
            mc.player.motion.z = 0.0;
            if (-(-((-89 | 77 | -42) ^ 56)) != -(-((-102 | -77 | 52) ^ 88))) {
            }
        } else if (f != 0.0F) {
            int var10001;
            if (f1 >= 1.0F) {
                if (f > 0.0F) {
                    var10001 = -(-((-99 | -15 | 80) ^ 32));
                    if (-(-((-39 | 37 | 85) ^ 55)) != -(-((52 | -124 | -55) ^ 51))) {
                    }
                } else {
                    var10001 = -(-((-103 | -90 | 70) ^ -36));
                }

                f2 += (float)var10001;
                f1 = 0.0F;
                if (-(-((-6 | 34 | -39) ^ 46)) != -(-((-83 | -31 | 88) ^ -117))) {
                }
            } else if (f1 <= -1.0F) {
                if (f > 0.0F) {
                    var10001 = -(-((96 | -47 | -55) ^ -38));
                    if (-(-((-52 | -90 | -39) ^ 111)) != -(-((-7 | 71 | -32) ^ -17))) {
                    }
                } else {
                    var10001 = -(-((55 | 73 | 59) ^ -94));
                }

                f2 += (float)var10001;
                f1 = 0.0F;
            }

            if (f > 0.0F) {
                f = 1.0F;
                if (-(-((43 | -19 | -67) ^ -104)) != -(-((102 | 3 | 97) ^ -45))) {
                }
            } else if (f < 0.0F) {
                f = -1.0F;
            }
        }

        double d0 = Math.cos(Math.toRadians((double)(f2 + 90.0F)));
        double d1 = Math.sin(Math.toRadians((double)(f2 + 90.0F)));
        mc.player.motion.x = (double)f * speed * d0 + (double)f1 * speed * d1;
        mc.player.motion.z = (double)f * speed * d1 - (double)f1 * speed * d0;
    }

    public static class StrafeMovement {
        public static double oldSpeed, contextFriction;
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
    public static float getDirection2() {
        float rotationYaw = mc.player.rotationYaw;

        float strafeFactor = 0f;

        if (mc.player.movementInput.moveForward > 0)
            strafeFactor = 1;
        if (mc.player.movementInput.moveForward < 0)
            strafeFactor = -1;

        if (strafeFactor == 0) {
            rotationYaw = mc.player.movementInput.moveStrafe > 0 ? -90 : 90;
        } else {
            rotationYaw = mc.player.movementInput.moveStrafe > 0 ? -(45 * strafeFactor) : 45 * strafeFactor;
        }

        if (strafeFactor < 0)
            rotationYaw -= 180;

        return (float) Math.toRadians(rotationYaw);
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
