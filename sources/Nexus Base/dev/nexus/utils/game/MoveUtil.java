package dev.nexus.utils.game;

import dev.nexus.utils.Utils;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;

public class MoveUtil implements Utils {

    public static double jumpMotion() {
        return 0.42F;
    }

    public static int getSpeedAmplifier() {
        if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
            return 1 + mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
        }

        return 0;
    }

    public static boolean isOnGround(double height) {
        return !mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.getEntityBoundingBox().offset(0.0D, -height, 0.0D)).isEmpty();
    }

    public static void setMotion2(double d, float f) {
        mc.thePlayer.motionX = -Math.sin(Math.toRadians(f)) * d;
        mc.thePlayer.motionZ = Math.cos(Math.toRadians(f)) * d;
    }

    public double speedPotionAmp(final double amp) {
        return mc.thePlayer.isPotionActive(Potion.moveSpeed) ? ((mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1) * amp) : 0;
    }

    public static float getMovingYaw() {
        return (float) (getPlayerDirection() * 180f / Math.PI);
    }

    public static double movingyaw() {
        float rotationYaw = mc.thePlayer.rotationYaw;

        if (mc.thePlayer.movementInput.moveForward < 0f) {
            rotationYaw += 180f;
        }

        float forward = 1f;

        if (mc.thePlayer.movementInput.moveForward < 0f) {
            forward = -0.5f;
        } else if (mc.thePlayer.movementInput.moveForward > 0f) {
            forward = 0.5f;
        }

        if (mc.thePlayer.movementInput.moveStrafe > 0f) {
            rotationYaw -= 90f * forward;
        }

        if (mc.thePlayer.movementInput.moveStrafe < 0f) {
            rotationYaw += 90f * forward;
        }

        return Math.toRadians(rotationYaw);
    }

    public static float getPlayerDirection() {
        // start with our current yaw
        float yaw = mc.thePlayer.rotationYaw;
        float strafe = 45;
        // add 180 to the yaw to strafe backwards
        if (mc.thePlayer.moveForward < 0) {
            // invert our strafe to -45
            strafe = -45;
            yaw += 180;
        }
        if (mc.thePlayer.moveStrafing > 0) {
            // subtract 45 to strafe left forward
            yaw -= strafe;
            // subtract an additional 45 if we do not press W in order to get to -90
            if (mc.thePlayer.moveForward == 0) {
                yaw -= 45;
            }
        } else if (mc.thePlayer.moveStrafing < 0) {
            // add 45 to strafe right forward
            yaw += strafe;
            // add 45 if we do not press W in order to get to 90
            if (mc.thePlayer.moveForward == 0) {
                yaw += 45;
            }
        }
        return yaw;
    }

    public static float getStrafe() {
        float direction = mc.thePlayer.rotationYaw;

        if (mc.thePlayer.moveForward > 0) {
            if (mc.thePlayer.moveStrafing > 0) {
                direction -= 45;
            } else if (mc.thePlayer.moveStrafing < 0) {
                direction += 45;
            }
        } else if (mc.thePlayer.moveForward < 0) {
            if (mc.thePlayer.moveStrafing > 0) {
                direction -= 135;
            } else if (mc.thePlayer.moveStrafing < 0) {
                direction += 135;
            } else {
                direction -= 180;
            }
        } else {
            if (mc.thePlayer.moveStrafing > 0) {
                direction -= 90;
            } else if (mc.thePlayer.moveStrafing < 0) {
                direction += 90;
            }
        }

        return direction;
    }


    /**
     * Rounds the players' position to a valid ground position
     *
     * @return valid ground position
     */
    public static double roundToGround(final double posY) {
        return Math.round(posY / 0.015625) * 0.015625;
    }

    public static double getSpeed() {
        // nigga hypot heavy
        return Math.hypot(mc.thePlayer.motionX, mc.thePlayer.motionZ);
    }

    public static void setMotion(double speed) {
        mc.thePlayer.motionX *= speed;
        mc.thePlayer.motionZ *= speed;
    }

    /**
     * Sets current speed to itself make strafe
     */
    public static void strafe() {
        strafe(getSpeed());
    }

    /**
     * Checks if the player is moving
     */
    public static boolean isMoving() {
        return mc.thePlayer != null && (mc.thePlayer.movementInput.moveForward != 0F || mc.thePlayer.movementInput.moveStrafe != 0F);
    }

    public static void stop() {
        mc.thePlayer.motionX = mc.thePlayer.motionZ = 0;
    }

    public static void setMotion(double x, double y, double z) {
        mc.thePlayer.motionX = x;
        mc.thePlayer.motionY = y;
        mc.thePlayer.motionZ = z;
    }

    public static void clipForward(final double length) {
        final double yaw = Math.toRadians(mc.thePlayer.rotationYaw);
        mc.thePlayer.setPosition(mc.thePlayer.posX + (-Math.sin(yaw) * length), mc.thePlayer.posY, mc.thePlayer.posZ + (Math.cos(yaw) * length));
    }

    public static float[] incrementMoveDirection(float forward, float strafe) {
        if (forward != 0 || strafe != 0) {
            float value = forward != 0 ? Math.abs(forward) : Math.abs(strafe);

            if (forward > 0) {
                if (strafe > 0) {
                    strafe = 0;
                } else if (strafe == 0) {
                    strafe = -value;
                } else if (strafe < 0) {
                    forward = 0;
                }
            } else if (forward == 0) {
                if (strafe > 0) {
                    forward = value;
                } else {
                    forward = -value;
                }
            } else {
                if (strafe < 0) {
                    strafe = 0;
                } else if (strafe == 0) {
                    strafe = value;
                } else if (strafe > 0) {
                    forward = 0;
                }
            }
        }

        return new float[]{forward, strafe};
    }
    public static void setSpeed(double moveSpeed, float yaw, double strafe, double forward) {
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
        mc.thePlayer.motionX = forward * moveSpeed * mx + strafe * moveSpeed * mz;
        mc.thePlayer.motionZ = forward * moveSpeed * mz - strafe * moveSpeed * mx;
    }

    public static void setSpeed(double moveSpeed) {
        setSpeed(moveSpeed, mc.thePlayer.rotationYaw, mc.thePlayer.movementInput.moveStrafe, mc.thePlayer.movementInput.moveForward);
    }
    /**
     * Sets players speed, with floats
     */
    public static void strafe(final float speed) {
        if (!isMoving()) {
            return;
        }

        final double yaw = getDirection();

        mc.thePlayer.motionX = -MathHelper.sin((float) yaw) * speed;
        mc.thePlayer.motionZ = MathHelper.cos((float) yaw) * speed;
    }

    /**
     * Used to get the players speed, with doubles
     */
    public static void strafe(final double speed) {
        if (!isMoving()) {
            return;
        }

        final double yaw = getDirection();
        mc.thePlayer.motionX = -MathHelper.sin((float) yaw) * speed;
        mc.thePlayer.motionZ = MathHelper.cos((float) yaw) * speed;
    }


    /**
     * Used to get the players speed with a custom yaw
     */
    public void strafe(final double speed, float yaw) {
        if (!isMoving()) {
            return;
        }


        mc.thePlayer.motionX = -MathHelper.sin(yaw) * speed;
        mc.thePlayer.motionZ = MathHelper.cos(yaw) * speed;
    }

    public static double getBaseMoveSpeed() {
        double baseSpeed = 0.28746801192563104D; //last airtick speed, 0.21 lowest value when falling straight down
        if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
            final double fixedSpeedMotion = 0.20000000298023224D; //Potion.java#moveSpeed = (new Potion(
            baseSpeed *= 1.0D + fixedSpeedMotion * (mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1);
        }
        return baseSpeed;
    }

    /**
     * Gets the direction of were the player is looking
     */
    public static double getDirection() {
        float rotationYaw = mc.thePlayer.rotationYaw;

        if (mc.thePlayer.moveForward < 0F) {
            rotationYaw += 180F;
        }

        float forward = 1F;

        if (mc.thePlayer.moveForward < 0F) {
            forward = -0.5F;
        } else if (mc.thePlayer.moveForward > 0F) {
            forward = 0.5F;
        }

        if (mc.thePlayer.moveStrafing > 0F) {
            rotationYaw -= 90F * forward;
        }
        if (mc.thePlayer.moveStrafing < 0F) {
            rotationYaw += 90F * forward;
        }

        return Math.toRadians(rotationYaw);
    }

    public double getDirectionWrappedTo90() {
        float rotationYaw = mc.thePlayer.rotationYaw;

        if (mc.thePlayer.moveForward < 0F && mc.thePlayer.moveStrafing == 0F) {
            rotationYaw += 180F;
        }

        final float forward = 1F;

        if (mc.thePlayer.moveStrafing > 0F) {
            rotationYaw -= 90F * forward;
        }
        if (mc.thePlayer.moveStrafing < 0F) {
            rotationYaw += 90F * forward;
        }

        return Math.toRadians(rotationYaw);
    }

    public double getDirection(final float yaw) {
        float rotationYaw = yaw;

        if (mc.thePlayer.moveForward < 0F) {
            rotationYaw += 180F;
        }

        float forward = 1F;

        if (mc.thePlayer.moveForward < 0F) {
            forward = -0.5F;
        } else if (mc.thePlayer.moveForward > 0F) {
            forward = 0.5F;
        }

        if (mc.thePlayer.moveStrafing > 0F) {
            rotationYaw -= 90F * forward;
        }
        if (mc.thePlayer.moveStrafing < 0F) {
            rotationYaw += 90F * forward;
        }

        return Math.toRadians(rotationYaw);
    }

    /**
     * Used to get base movement speed
     */

    public double getPredictedMotionY(final double motionY) {
        return (motionY - 0.08) * 0.98F;
    }

    public static boolean overAir(double distance) {
        return mc.theWorld.isAirBlock(new BlockPos(MathHelper.floor_double(mc.thePlayer.posX), MathHelper.floor_double(mc.thePlayer.posY - distance), MathHelper.floor_double(mc.thePlayer.posZ)));
    }

}