package dev.excellent.impl.util.player;

import dev.excellent.api.event.impl.player.MoveEvent;
import dev.excellent.api.event.impl.player.MoveInputEvent;
import dev.excellent.api.interfaces.game.IMinecraft;
import dev.excellent.client.rotation.FreeLookHandler;
import lombok.experimental.UtilityClass;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;

@UtilityClass
public class MoveUtil implements IMinecraft {
    public boolean isBlockAboveHead() {
        AxisAlignedBB axisAlignedBB = new AxisAlignedBB(mc.player.getPosX() - 0.3, mc.player.getPosY() + mc.player.getEyeHeight(),
                mc.player.getPosZ() + 0.3, mc.player.getPosX() + 0.3, mc.player.getPosY() + (!mc.player.isOnGround() ? 1.5 : 2.5),
                mc.player.getPosZ() - 0.3);
        return mc.world.getCollisionShapes(mc.player, axisAlignedBB).findAny().isEmpty();
    }

    public boolean isBlockAbove(double x, double y, double z) {
        AxisAlignedBB axisAlignedBB = new AxisAlignedBB(
                mc.player.getPosX() - x,
                mc.player.getPosY() + mc.player.getEyeHeight(),
                mc.player.getPosZ() + z,
                mc.player.getPosX() + x,
                mc.player.getPosY() + mc.player.getEyeHeight() + y,
                mc.player.getPosZ() - z);
        return mc.world.getCollisionShapes(mc.player, axisAlignedBB).findAny().isPresent();
    }

    public boolean isBlockUnder(float under) {
        if (mc.player.getPosY() < 0.0) {
            return false;
        } else {
            AxisAlignedBB aab = mc.player.getBoundingBox().offset(0.0, -under, 0.0);
            return mc.world.getCollisionShapes(mc.player, aab).toList().isEmpty();
        }
    }

    public void strafe(final double speed) {
        if (!isMoving()) {
            return;
        }

        final double yaw = direction();
        mc.player.motion.x = -MathHelper.sin((float) yaw) * speed;
        mc.player.motion.z = MathHelper.cos((float) yaw) * speed;
    }

    public void strafe(final double speed, float yaw) {
        if (!isMoving()) {
            return;
        }

        yaw = (float) Math.toRadians(yaw);
        mc.player.motion.x = -MathHelper.sin(yaw) * speed;
        mc.player.motion.z = MathHelper.cos(yaw) * speed;
    }

    public void stop() {
        mc.player.motion.x = 0;
        mc.player.motion.z = 0;
    }

    public void strafe() {
        strafe(speed());
    }

    public boolean isOnBlockEdge() {
        final LivingEntity entity = mc.player;

        return (mc.world.getCollisionShapes(entity, entity.getBoundingBox().offset(-0.0005D, -0.5D, -0.0005D).expand(0.001D, 0.0D, 0.001D)).findAny().isEmpty() && entity.isOnGround());
    }

    public boolean isMoving() {
        return mc.player.moveForward != 0 || mc.player.moveStrafing != 0;
    }

    public void fixMovement(final MoveInputEvent event, final float yaw) {
        final float forward = event.getForward();
        final float strafe = event.getStrafe();

        final double angle = MathHelper.wrapDegrees(Math.toDegrees(MoveUtil.direction(FreeLookHandler.getFreeYaw(), forward, strafe)));

        if (forward == 0 && strafe == 0) {
            return;
        }

        float closestForward = 0, closestStrafe = 0, closestDifference = Float.MAX_VALUE;

        for (float predictedForward = -1F; predictedForward <= 1F; predictedForward += 1F) {
            for (float predictedStrafe = -1F; predictedStrafe <= 1F; predictedStrafe += 1F) {
                if (predictedStrafe == 0 && predictedForward == 0) continue;

                final double predictedAngle = MathHelper.wrapDegrees(Math.toDegrees(MoveUtil.direction(yaw, predictedForward, predictedStrafe)));
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

    public double direction() {
        float rotationYaw = mc.player.rotationYaw;

        if (mc.player.moveForward < 0) {
            rotationYaw += 180;
        }

        float forward = 1;

        if (mc.player.moveForward < 0) {
            forward = -0.5F;
        } else if (mc.player.moveForward > 0) {
            forward = 0.5F;
        }

        if (mc.player.moveStrafing > 0) {
            rotationYaw -= 90 * forward;
        }

        if (mc.player.moveStrafing < 0) {
            rotationYaw += 90 * forward;
        }

        return Math.toRadians(rotationYaw);
    }

    public double wrappedDirection() {
        float rotationYaw = mc.player.rotationYaw;

        if (mc.player.moveForward < 0 && mc.player.moveStrafing == 0) {
            rotationYaw += 180;
        }

        if (mc.player.moveStrafing > 0) {
            rotationYaw -= 90;
        }

        if (mc.player.moveStrafing < 0) {
            rotationYaw += 90;
        }

        return Math.toRadians(rotationYaw);
    }

    public double direction(float rotationYaw, final double moveForward, final double moveStrafing) {
        if (moveForward < 0F) rotationYaw += 180F;

        float forward = 1F;

        if (moveForward < 0F) forward = -0.5F;
        else if (moveForward > 0F) forward = 0.5F;

        if (moveStrafing > 0F) rotationYaw -= 90F * forward;
        if (moveStrafing < 0F) rotationYaw += 90F * forward;

        return Math.toRadians(rotationYaw);
    }

    public void setSpeed(double moveSpeed, float yaw, double strafe, double forward) {
        if (forward != 0.0D) {
            if (strafe > 0.0D) {
                yaw += ((forward > 0.0D) ? -45 : 45);
            } else if (strafe < 0.0D) {
                yaw += ((forward > 0.0D) ? 45 : -45);
            }
            strafe = 0.0D;
            if (forward > 0.0D) {
                forward = 1.0D;
            } else if (forward < 0.0D) {
                forward = -1.0D;
            }
        }
        if (strafe > 0.0D) {
            strafe = 1.0D;
        } else if (strafe < 0.0D) {
            strafe = -1.0D;
        }
        double mx = Math.cos(Math.toRadians((yaw + 90.0F)));
        double mz = Math.sin(Math.toRadians((yaw + 90.0F)));
        mc.player.getMotion().x = forward * moveSpeed * mx + strafe * moveSpeed * mz;
        mc.player.getMotion().z = forward * moveSpeed * mz - strafe * moveSpeed * mx;
    }

    public void setSpeed(double moveSpeed) {
        setSpeed(moveSpeed, mc.player.rotationYaw, mc.player.movementInput.moveStrafe, mc.player.movementInput.moveForward);
    }

    public void setSpeed(double moveSpeed, float yaw) {
        setSpeed(moveSpeed, yaw, mc.player.movementInput.moveStrafe, mc.player.movementInput.moveForward);
    }

    public double speed() {
        return Math.hypot(mc.player.motion.x, mc.player.motion.z);
    }

    public double speedSqrt() {
        return Math.sqrt(mc.player.motion.x * mc.player.motion.x + mc.player.motion.z * mc.player.motion.z);
    }

    public double getEntityBps(Entity entity) {
        double xDif = entity.getPosX() - entity.prevPosX;
        double zDif = entity.getPosZ() - entity.prevPosZ;
        return Math.sqrt(xDif * xDif + zDif * zDif) * 15F;
    }

    public double getMotion() {
        return Math.hypot(mc.player.getMotion().x, mc.player.getMotion().z);
    }

    public void setMoveMotion(final MoveEvent move, final double motion) {
        double forward = mc.player.movementInput.moveForward;
        double strafe = mc.player.movementInput.moveStrafe;
        float yaw = mc.player.rotationYaw;
        if (forward == 0 && strafe == 0) {
            move.motion.x = 0;
            move.motion.z = 0;
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
            move.motion.x = forward * motion * MathHelper.cos((float) Math.toRadians(yaw + 90.0f))
                    + strafe * motion * MathHelper.sin((float) Math.toRadians(yaw + 90.0f));
            move.motion.z = forward * motion * MathHelper.sin((float) Math.toRadians(yaw + 90.0f))
                    - strafe * motion * MathHelper.cos((float) Math.toRadians(yaw + 90.0f));
        }
    }
}
