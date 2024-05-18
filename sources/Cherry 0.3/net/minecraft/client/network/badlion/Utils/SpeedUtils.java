package net.minecraft.client.network.badlion.Utils;

import net.minecraft.client.Minecraft;

public class SpeedUtils {
	
	public static Minecraft mc = Minecraft.getMinecraft();
	
    public static void setSpeed(final float speed) {
        double yaw = mc.thePlayer.rotationYaw;
        final boolean isMoving = mc.thePlayer.moveForward != 0.0f || mc.thePlayer.moveStrafing != 0.0f;
        final boolean isMovingForward = mc.thePlayer.moveForward > 0.0f;
        final boolean isMovingBackward = mc.thePlayer.moveForward < 0.0f;
        final boolean isMovingRight = mc.thePlayer.moveStrafing > 0.0f;
        final boolean isMovingLeft = mc.thePlayer.moveStrafing < 0.0f;
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
            mc.thePlayer.motionX = -Math.sin(yaw) * speed;
            mc.thePlayer.motionZ = Math.cos(yaw) * speed;
        }
    }
}
