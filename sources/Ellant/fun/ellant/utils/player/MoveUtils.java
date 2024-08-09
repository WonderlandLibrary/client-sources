package fun.ellant.utils.player;

import fun.ellant.events.CancelEvent;
import fun.ellant.events.EventInput;
import fun.ellant.events.MovingEvent;
import fun.ellant.functions.impl.player.FreeCam;
import fun.ellant.utils.client.IMinecraft;
import lombok.experimental.UtilityClass;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;

@UtilityClass
public class MoveUtils implements IMinecraft {

    public boolean isMoving() {
        return mc.player.movementInput.moveForward != 0f || mc.player.movementInput.moveStrafe != 0f;
    }
    public static float getMotions() {
        return (float) Math.sqrt(mc.player.motion.x * mc.player.motion.x + mc.player.motion.z * mc.player.motion.z);
    }
    public static void setSpeed(double speed) {
        float f = mc.player.movementInput.moveForward;
        float f1 = mc.player.movementInput.moveStrafe;
        float f2 = mc.player.rotationYaw;
        if (f == 0.0F && f1 == 0.0F) {
            mc.player.motion.x = 0.0;
            mc.player.motion.z = 0.0;
        } else if (f != 0.0F) {
            if (f1 >= 1.0F) {
                f2 += (float)(f > 0.0F ? -35 : 35);
                f1 = 0.0F;
            } else if (f1 <= -1.0F) {
                f2 += (float)(f > 0.0F ? 35 : -35);
                f1 = 0.0F;
            }

            if (f > 0.0F) {
                f = 1.0F;
            } else if (f < 0.0F) {
                f = -1.0F;
            }
        }

        double d0 = Math.cos(Math.toRadians((double)(f2 + 90.0F)));
        double d1 = Math.sin(Math.toRadians((double)(f2 + 90.0F)));
        mc.player.motion.x = (double)f * speed * d0 + (double)f1 * speed * d1;
        mc.player.motion.z = (double)f * speed * d1 - (double)f1 * speed * d0;
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
    public static void setMotion(final double motion) {
        double forward = mc.player.movementInput.moveForward;
        double strafe = mc.player.movementInput.moveStrafe;
        float yaw = mc.player.rotationYaw;
        if (forward == 0 && strafe == 0) {
            mc.player.motion.x = 0;
            mc.player.motion.z = 0;
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
            mc.player.motion.x = forward * motion * MathHelper.cos((float) Math.toRadians(yaw + 90.0f))
                    + strafe * motion * MathHelper.sin((float) Math.toRadians(yaw + 90.0f));
            mc.player.motion.z = forward * motion * MathHelper.sin((float) Math.toRadians(yaw + 90.0f))
                    - strafe * motion * MathHelper.cos((float) Math.toRadians(yaw + 90.0f));
        }
    }
    public static void setMotion(final double motion, FreeCam player) {
        double forward = mc.player.movementInput.moveForward;
        double strafe = mc.player.movementInput.moveStrafe;
        float yaw = mc.player.rotationYaw;
        if (forward == 0 && strafe == 0) {
            mc.player.motion.x = 0;
            mc.player.motion.z = 0;
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
            mc.player.motion.x = forward * motion * MathHelper.cos((float) Math.toRadians(yaw + 90.0f))
                    + strafe * motion * MathHelper.sin((float) Math.toRadians(yaw + 90.0f));
            mc.player.motion.z = forward * motion * MathHelper.sin((float) Math.toRadians(yaw + 90.0f))
                    - strafe * motion * MathHelper.cos((float) Math.toRadians(yaw + 90.0f));
        }
    }
    public double getMotion() {
        return Math.hypot(mc.player.getMotion().x, mc.player.getMotion().z);
    }

    public void setMotions(final double speed) {
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
    public static double[] forward(final double d) {
        float f = mc.player.movementInput.moveForward;
        float f2 = mc.player.movementInput.moveStrafe;
        float f3 =  mc.player.getRotationYawHead();
        if (f != 0.0f) {
            if (f2 > 0.0f) {
                f3 += ((f > 0.0f) ? -45 : 45);
            } else if (f2 < 0.0f) {
                f3 += ((f > 0.0f) ? 45 : -45);
            }
            f2 = 0.0f;
            if (f > 0.0f) {
                f = 1.0f;
            } else if (f < 0.0f) {
                f = -1.0f;
            }
        }
        final double d2 = Math.sin(Math.toRadians(f3 + 90.0f));
        final double d3 = Math.cos(Math.toRadians(f3 + 90.0f));
        final double d4 = f * d * d3 + f2 * d * d2;
        final double d5 = f * d * d2 - f2 * d * d3;
        return new double[]{d4, d5};
    }
    public static class StrafeMovement {

        public static double oldSpeed, contextFriction;
        public static boolean needSwap;
        public static boolean needSprintState;
        public static int counter, noSlowTicks;

        public static double calculateSpeed(final EventMove move, boolean damageBoost, boolean hasTime, float damageSpeed) {

            final boolean fromGround = mc.player.isOnGround();
            final boolean toGround = move.toGround();
            final boolean jump = move.motion().y > 0;
            final float speedAttributes = getAIMoveSpeed(mc.player);
            final float frictionFactor = getFrictionFactor(mc.player, move);
            float n6 = mc.player.isPotionActive(Effects.JUMP_BOOST) && mc.player.isHandActive() ? 0.88f : 0.91F;

            if (fromGround) {
                n6 = frictionFactor;
            }
            final float n7 = 0.16277136f / (n6 * n6 * n6);
            float n8;
            if (fromGround) {
                n8 = speedAttributes * n7;
                if (jump) {
                    n8 += 0.2f;
                }
            } else {
                n8 = (damageBoost && hasTime && mc.gameSettings.keyBindJump.isKeyDown() ? damageSpeed : 0.0255f);
            }
            boolean noslow = false;
            double max2 = oldSpeed + n8;
            double max = 0.0;
            if (mc.player.isHandActive() && !jump) {
                double n10 = oldSpeed + n8 * 0.25;
                double motionY2 = move.motion().y;
                if (motionY2 != 0.0 && Math.abs(motionY2) < 0.08) {
                    n10 += 0.055;
                }
                if (max2 > (max = Math.max(0.043, n10))) {
                    noslow = true;
                    ++noSlowTicks;
                } else {
                    noSlowTicks = Math.max(noSlowTicks - 1, 0);
                }
            } else {
                noSlowTicks = 0;
            }
            if (noSlowTicks > 3) {
                max2 = max - (mc.player.isPotionActive(Effects.JUMP_BOOST) && mc.player.isHandActive() ? 0.3 : 0.019);
            } else {
                max2 = Math.max(noslow ? 0 : 0.25, max2) - (counter++ % 2 == 0 ? 0.001 : 0.002);
            }
            contextFriction = n6;
            if (!toGround && !fromGround) {
                needSwap = true;
            }
            if (!fromGround && !toGround) {
                needSprintState = !mc.player.serverSprintState;
            }
            if (toGround && fromGround) {
                needSprintState = false;
            }
            return max2;
        }

        public static void postMove(final double horizontal) {
            oldSpeed = horizontal * contextFriction;
        }

        public static float getAIMoveSpeed(final ClientPlayerEntity contextPlayer) {
            boolean prevSprinting = contextPlayer.isSprinting();
            contextPlayer.setSprinting(false);
            float speed = contextPlayer.getAIMoveSpeed() * 1.3f;
            contextPlayer.setSprinting(prevSprinting);
            return speed;
        }

        private static float getFrictionFactor(final ClientPlayerEntity contextPlayer, final EventMove move) {
            BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();
            blockpos$mutable.setPos(move.from().x, move.getAABBFrom().minY - 1.0D, move.from().z);

            return contextPlayer.world.getBlockState(blockpos$mutable).getBlock().slipperiness * 0.91F;
        }
    }
    public class EventMove extends CancelEvent {

        public Vector3d from, to, motion;
        private boolean toGround;
        private AxisAlignedBB aabbFrom;
        public boolean ignoreHorizontal, ignoreVertical, collidedHorizontal, collidedVertical;

        public EventMove(Vector3d from, Vector3d to, Vector3d motion,boolean toGround, boolean isCollidedHorizontal, boolean isCollidedVertical, AxisAlignedBB aabbFrom) {
            this.from = from;
            this.to = to;
            this.motion = motion;
            this.toGround = toGround;
            this.collidedHorizontal = isCollidedHorizontal;
            this.collidedVertical = isCollidedVertical;
            this.aabbFrom = aabbFrom;
        }

        public void setIgnoreHorizontalCollision() {
            this.ignoreHorizontal = true;
        }

        public void setIgnoreVerticalCollision() {
            this.ignoreVertical = true;
        }

        public boolean isIgnoreHorizontal() {
            return this.ignoreHorizontal;
        }

        public AxisAlignedBB getAABBFrom() {
            return this.aabbFrom;
        }

        public boolean isIgnoreVertical() {
            return this.ignoreVertical;
        }

        public boolean isCollidedHorizontal() {
            return this.collidedHorizontal;
        }

        public boolean isCollidedVertical() {
            return this.collidedVertical;
        }

        public boolean toGround() {
            return this.toGround;
        }

        public Vector3d from() {
            return this.from;
        }

        public Vector3d to() {
            return this.to;
        }

        public Vector3d motion() {
            return this.motion;
        }
    }
}