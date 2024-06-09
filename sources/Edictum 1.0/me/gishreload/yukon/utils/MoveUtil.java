package me.gishreload.yukon.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;

public class MoveUtil {

	public static void setSpeed(final float speed) {
        final EntityPlayerSP player = Minecraft.getMinecraft().getPlayer();
        double yaw = (double) player.rotationYaw;
        final boolean isMoving = player.moveForward != 0.0f || player.moveStrafing != 0.0f;
        final boolean isMovingForward = player.moveForward > 0.0f;
        final boolean isMovingBackward = player.moveForward < 0.0f;
        final boolean isMovingRight = player.moveStrafing > 0.0f;
        final boolean isMovingLeft = player.moveStrafing < 0.0f;
        final boolean isMovingSideways = isMovingLeft || isMovingRight;
        final boolean isMovingStraight = isMovingForward || isMovingBackward;
        if (isMoving) {
            if (isMovingForward && !isMovingSideways) {
                yaw += 0.0;
            } else if (isMovingBackward && !isMovingSideways) {
                yaw += 180.0;
            } else if (isMovingForward && isMovingLeft) {
                yaw += 45.0;
            } else if (isMovingForward) {
                yaw -= 45.0;
            } else if (!isMovingStraight && isMovingLeft) {
                yaw += 90.0;
            } else if (!isMovingStraight && isMovingRight) {
                yaw -= 90.0;
            } else if (isMovingBackward && isMovingLeft) {
                yaw += 135.0;
            } else if (isMovingBackward) {
                yaw -= 135.0;
            }
            yaw = Math.toRadians(yaw);
            player.motionX = -Math.sin(yaw) * speed;
            player.motionZ = Math.cos(yaw) * speed;
        }
    }
}
