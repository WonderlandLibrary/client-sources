package eze.modules.movement;

import net.minecraft.client.*;

public class SpeedModifier
{
    public static void setSpeed(final float d) {
        double yaw = Minecraft.getMinecraft().thePlayer.rotationYaw;
        final boolean isMoving = Minecraft.getMinecraft().thePlayer.moveForward != 0.0f || Minecraft.getMinecraft().thePlayer.moveStrafing != 0.0f;
        final boolean isMovingForward = Minecraft.getMinecraft().thePlayer.moveForward > 0.0f;
        final boolean isMovingBackward = Minecraft.getMinecraft().thePlayer.moveForward < 0.0f;
        final boolean isMovingRight = Minecraft.getMinecraft().thePlayer.moveStrafing > 0.0f;
        final boolean isMovingLeft = Minecraft.getMinecraft().thePlayer.moveStrafing < 0.0f;
        final boolean isMovingSideways = isMovingLeft || isMovingRight;
        final boolean isMovingStraight = isMovingForward || isMovingBackward;
        if (isMoving) {
            if (isMovingForward && !isMovingSideways) {
                yaw += 0.0;
            }
            else if (isMovingBackward && !isMovingSideways) {
                yaw += 180.0;
            }
            else if (isMovingForward && isMovingLeft) {
                yaw += 45.0;
            }
            else if (isMovingForward) {
                yaw -= 45.0;
            }
            else if (!isMovingStraight && isMovingLeft) {
                yaw += 90.0;
            }
            else if (!isMovingStraight && isMovingRight) {
                yaw -= 90.0;
            }
            else if (isMovingBackward && isMovingLeft) {
                yaw += 135.0;
            }
            else if (isMovingBackward) {
                yaw -= 135.0;
            }
            yaw = Math.toRadians(yaw);
            Minecraft.getMinecraft().thePlayer.motionX = -Math.sin(yaw) * d;
            Minecraft.getMinecraft().thePlayer.motionZ = Math.cos(yaw) * d;
        }
    }
}
