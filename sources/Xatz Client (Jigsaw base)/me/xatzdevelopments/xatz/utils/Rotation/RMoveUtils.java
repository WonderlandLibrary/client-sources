package me.xatzdevelopments.xatz.utils.Rotation;

import net.minecraft.client.Minecraft;

public final class RMoveUtils
{
    public static Minecraft mc;
    
    static {
        RMoveUtils.mc = Minecraft.getMinecraft();
    }
    
    public static float getSpeed() {
        return (float)Math.sqrt(RMoveUtils.mc.thePlayer.motionX * RMoveUtils.mc.thePlayer.motionX + RMoveUtils.mc.thePlayer.motionZ * RMoveUtils.mc.thePlayer.motionZ);
    }
    
    public static void strafe() {
        strafe(getSpeed());
    }
    
    public static boolean isMoving() {
        return RMoveUtils.mc.thePlayer != null && (RMoveUtils.mc.thePlayer.movementInput.moveForward != 0.0f || RMoveUtils.mc.thePlayer.movementInput.moveStrafe != 0.0f);
    }
    
    public static void strafe(final float speed) {
        if (!isMoving()) {
            return;
        }
        final double yaw = getDirection();
        RMoveUtils.mc.thePlayer.motionX = -Math.sin(yaw) * speed;
        RMoveUtils.mc.thePlayer.motionZ = Math.cos(yaw) * speed;
    }
    
    public static void strafe(final double speed) {
        if (!isMoving()) {
            return;
        }
        final double yaw = getDirection();
        RMoveUtils.mc.thePlayer.motionX = -Math.sin(yaw) * speed;
        RMoveUtils.mc.thePlayer.motionZ = Math.cos(yaw) * speed;
    }
    
    public static void forward(final double length) {
        final double yaw = Math.toRadians(RMoveUtils.mc.thePlayer.rotationYaw);
        RMoveUtils.mc.thePlayer.setPosition(RMoveUtils.mc.thePlayer.posX + -Math.sin(yaw) * length, RMoveUtils.mc.thePlayer.posY, RMoveUtils.mc.thePlayer.posZ + Math.cos(yaw) * length);
    }
    
    public static double getDirection() {
        float rotationYaw = RMoveUtils.mc.thePlayer.rotationYaw;
        if (RMoveUtils.mc.thePlayer.moveForward < 0.0f) {
            rotationYaw += 180.0f;
        }
        float forward = 1.0f;
        if (RMoveUtils.mc.thePlayer.moveForward < 0.0f) {
            forward = -0.5f;
        }
        else if (RMoveUtils.mc.thePlayer.moveForward > 0.0f) {
            forward = 0.5f;
        }
        if (RMoveUtils.mc.thePlayer.moveStrafing > 0.0f) {
            rotationYaw -= 90.0f * forward;
        }
        if (RMoveUtils.mc.thePlayer.moveStrafing < 0.0f) {
            rotationYaw += 90.0f * forward;
        }
        return Math.toRadians(rotationYaw);
    }
    
    public static double getDirectionWithYaw(final float yaw) {
        float rotationYaw = yaw;
        if (RMoveUtils.mc.thePlayer.moveForward < 0.0f) {
            rotationYaw += 180.0f;
        }
        float forward = 1.0f;
        if (RMoveUtils.mc.thePlayer.moveForward < 0.0f) {
            forward = -0.5f;
        }
        else if (RMoveUtils.mc.thePlayer.moveForward > 0.0f) {
            forward = 0.5f;
        }
        if (RMoveUtils.mc.thePlayer.moveStrafing > 0.0f) {
            rotationYaw -= 90.0f * forward;
        }
        if (RMoveUtils.mc.thePlayer.moveStrafing < 0.0f) {
            rotationYaw += 90.0f * forward;
        }
        return Math.toRadians(rotationYaw);
    }
}
