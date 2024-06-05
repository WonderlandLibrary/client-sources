package zamorozka.ui;

import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import net.minecraft.util.MovementInput;
import net.minecraft.util.math.MathHelper;
import zamorozka.event.events.EventRawMove;

public class SpeedUtils {
    private static final Minecraft mc = Minecraft.getMinecraft();

    public static double getBaseMoveSpeed() {
        double baseSpeed = 0.2873;
        if (Minecraft.player != null && Minecraft.player.isPotionActive(Potion.getPotionById(1))) {
            int amplifier = Minecraft.player.getActivePotionEffect(Potion.getPotionById(1)).getAmplifier();
            baseSpeed *= 1.0 + 0.2 * (double) (amplifier + 1);
        }
        return baseSpeed;
    }

    public static void setMoveSpeed(EventRawMove aab, double moveSpeed) {
        float forward = MovementInput.moveForward;
        float strafe = MovementInput.moveStrafe;
        float yaw = Minecraft.player.rotationYaw;
        if ((double) forward == 0.0 && (double) strafe == 0.0) {
            Minecraft.player.motionX = 0.0;
            Minecraft.player.motionZ = 0.0;
        } else {
            if ((double) forward != 0.0) {
                if (strafe > 0.0f) {
                    yaw += (float) ((double) forward > 0.0 ? -45 : 45);
                } else if (strafe < 0.0f) {
                    yaw += (float) ((double) forward > 0.0 ? 45 : -45);
                }
                strafe = 0.0f;
                if (forward > 0.0f) {
                    forward = 1.0f;
                } else if (forward < 0.0f) {
                    forward = -1.0f;
                }
            }
            double xDist = (double) forward * moveSpeed * Math.cos(Math.toRadians(yaw + 90.0f)) + (double) strafe * moveSpeed * Math.sin(Math.toRadians(yaw + 90.0f));
            double zDist = (double) forward * moveSpeed * Math.sin(Math.toRadians(yaw + 90.0f)) - (double) strafe * moveSpeed * Math.cos(Math.toRadians(yaw + 90.0f));
            aab.setX(xDist);
            aab.setZ(zDist);
        }
    }

    public static void setMoveSpeed(double moveSpeed) {
        float forward = MovementInput.moveForward;
        float strafe = MovementInput.moveStrafe;
        float yaw = Minecraft.player.rotationYaw;
        if ((double) forward == 0.0 && (double) strafe == 0.0) {
            Minecraft.player.motionX = 0.0;
            Minecraft.player.motionZ = 0.0;
        }
        int d = 45;
        if ((double) forward != 0.0) {
            if ((double) strafe > 0.0) {
                yaw += (float) ((double) forward > 0.0 ? -d : d);
            } else if ((double) strafe < 0.0) {
                yaw += (float) ((double) forward > 0.0 ? d : -d);
            }
            strafe = 0.0f;
            if ((double) forward > 0.0) {
                forward = 1.0f;
            } else if ((double) forward < 0.0) {
                forward = -1.0f;
            }
        }
        double xDist = (double) forward * moveSpeed * Math.cos(Math.toRadians(yaw + 90.0f)) + (double) strafe * moveSpeed * Math.sin(Math.toRadians(yaw + 90.0f));
        double zDist = (double) forward * moveSpeed * Math.sin(Math.toRadians(yaw + 90.0f)) - (double) strafe * moveSpeed * Math.cos(Math.toRadians(yaw + 90.0f));
        Minecraft.player.motionX = xDist;
        Minecraft.player.motionZ = zDist;
    }

    public static void setSpeed(double speed) {
        if (Minecraft.player.isMoving()) {
            Minecraft.player.motionX = (double) (-MathHelper.sin(SpeedUtils.getDirection())) * speed;
            Minecraft.player.motionZ = (double) MathHelper.cos(SpeedUtils.getDirection()) * speed;
        } else {
            Minecraft.player.motionX = 0.0;
            Minecraft.player.motionZ = 0.0;
        }
    }

    public static float getDirection() {
        float yaw = Minecraft.player.rotationYaw;
        float forward = Minecraft.player.moveForward;
        float strafe = Minecraft.player.moveStrafing;
        yaw += (float) (forward < 0.0f ? 180 : 0);
        if (strafe < 0.0f) {
            yaw += (float) (forward == 0.0f ? 90 : (forward < 0.0f ? -45 : 45));
        }
        if (strafe > 0.0f) {
            yaw -= (float) (forward == 0.0f ? 90 : (forward < 0.0f ? -45 : 45));
        }
        return yaw * ((float) Math.PI / 180);
    }

    public static long getBaseMoveSpeedLong() {
        double baseSpeed = 32.0;
        if (Minecraft.player.isPotionActive(Potion.getPotionById(1))) {
            int amplifier = Minecraft.player.getActivePotionEffect(Potion.getPotionById(1)).getAmplifier();
            baseSpeed *= 1.0 + 0.2 * (double) (amplifier + 1);
        }
        return (long) (baseSpeed / 32.0);
    }

    public static float getBaseMoveSpeedFloat() {
        double baseSpeed = 0.6;
        if (Minecraft.player.isPotionActive(Potion.getPotionById(1))) {
            int amplifier = Minecraft.player.getActivePotionEffect(Potion.getPotionById(1)).getAmplifier();
            baseSpeed *= 1.0 + 0.2 * (double) (amplifier + 1);
        }
        return (float) baseSpeed;
    }

    public static double getBaseSpeed() {
        return Math.sqrt(Minecraft.player.motionX * Minecraft.player.motionX + Minecraft.player.motionZ * Minecraft.player.motionZ);
    }

    public static boolean setMoveSpeed(long val) {
        float f = (float) val * 0.1f;
        SpeedUtils.setSpeed(f);
        return true;
    }

    public static boolean setBaseMoveSpeed() {
        float f = SpeedUtils.getBaseMoveSpeedFloat();
        SpeedUtils.setMoveSpeed(f);
        return true;
    }
}

