package me.teus.eclipse.utils;

import net.minecraft.potion.Potion;

public class MoveUtils implements Utils{
    public static boolean isWalking() {
        return mc.thePlayer.moveForward != 0 || mc.thePlayer.moveStrafing != 0;
    }

    public static void setSpeed(double speed) {
        if(isWalking()) {
            mc.thePlayer.motionX = -Math.sin(getDirection()) * speed;
            mc.thePlayer.motionZ = Math.cos(getDirection()) * speed;
        } else {
            mc.thePlayer.motionX = 0;
            mc.thePlayer.motionZ = 0;
        }
    }

    public static double getBaseMoveSpeed() {
        double baseSpeed = 0.2873;
        if (mc.thePlayer != null && mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
            final int amplifier = mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
            baseSpeed *= 1.0 + 0.2 * (amplifier + 1);
        }
        return baseSpeed;
    }

    public static double getDirection() {

        boolean movingForward = mc.thePlayer.moveForward > 0.0F;
        boolean movingBackward = mc.thePlayer.moveForward < 0.0F;
        boolean movingRight = mc.thePlayer.moveStrafing > 0.0F;
        boolean movingLeft = mc.thePlayer.moveStrafing < 0.0F;

        boolean isMovingSideways = movingLeft || movingRight;
        boolean isMovingStraight = movingForward || movingBackward;

        double direction = mc.thePlayer.rotationYaw;

        if(movingForward && !isMovingSideways) {

        } else if(movingBackward && !isMovingSideways) {
            direction += 180;
        } else if(movingForward && movingLeft) {
            direction += 45;
        } else if(movingForward) {
            direction -= 45;
        } else if(!isMovingStraight && movingLeft) {
            direction += 90;
        } else if(!isMovingStraight && movingRight) {
            direction -= 90;
        } else if(movingBackward && movingRight) {
            direction -= 135;
        } else if(movingBackward) {
            direction += 135;
        }

        return Math.toRadians(direction);
    }
}
