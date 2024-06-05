package gq.vapu.czfclient.Util.Math;

import gq.vapu.czfclient.Util.Helper;
import net.minecraft.potion.Potion;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovementInput;

public class PlayerUtils {
    public static boolean isMoving() {
        if ((!Helper.mc.thePlayer.isCollidedHorizontally) && (!Helper.mc.thePlayer.isSneaking())) {
            return ((MovementInput.moveForward != 0.0F || MovementInput.moveStrafe != 0.0F));
        }
        return false;
    }

    public static float getDirection() {
        float yaw = Helper.mc.thePlayer.rotationYaw;
        final float forward = Helper.mc.thePlayer.moveForward;
        final float strafe = Helper.mc.thePlayer.moveStrafing;
        yaw += ((forward < 0.0f) ? 180 : 0);
        if (strafe < 0.0f) {
            yaw += ((forward < 0.0f) ? -45.0f : ((forward == 0.0f) ? 90.0f : 45.0f));
        }
        if (strafe > 0.0f) {
            yaw -= ((forward < 0.0f) ? -45.0f : ((forward == 0.0f) ? 90.0f : 45.0f));
        }
        return yaw * 0.017453292f;
    }

    public static float getSpeed() {
        final float vel = (float) Math.sqrt(Helper.mc.thePlayer.motionX * Helper.mc.thePlayer.motionX + Helper.mc.thePlayer.motionZ * Helper.mc.thePlayer.motionZ);
        return vel;
    }

    public static void setSpeed(final double speed) {
        Helper.mc.thePlayer.motionX = -MathHelper.sin(getDirection()) * speed;
        Helper.mc.thePlayer.motionZ = MathHelper.cos(getDirection()) * speed;
    }

    public static boolean isMoving2() {
        return ((Helper.mc.thePlayer.moveForward != 0.0F || Helper.mc.thePlayer.moveStrafing != 0.0F));
    }

    public static int getJumpEffect() {
        if (Helper.mc.thePlayer.isPotionActive(Potion.jump))
            return Helper.mc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1;
        else
            return 0;
    }


    public static boolean isOnGround(double height) {
        return !Helper.mc.theWorld.getCollidingBoundingBoxes(Helper.mc.thePlayer, Helper.mc.thePlayer.getEntityBoundingBox().offset(0.0D, -height, 0.0D)).isEmpty();
    }

}
