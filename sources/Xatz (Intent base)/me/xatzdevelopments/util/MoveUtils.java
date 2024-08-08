package me.xatzdevelopments.util;

import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;

public final class MoveUtils
{
    public static Minecraft mc;
    
    static {
        MoveUtils.mc = Minecraft.getMinecraft();
    }
    
    public static float getSpeed() {
        return (float)Math.sqrt(MoveUtils.mc.thePlayer.motionX * MoveUtils.mc.thePlayer.motionX + MoveUtils.mc.thePlayer.motionZ * MoveUtils.mc.thePlayer.motionZ);
    }
    
    public static int getJumpEffect() {
        if (mc.thePlayer.isPotionActive(Potion.jump))
            return mc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1;
        else
            return 0;
    }
    
    public static void strafe() {
        strafe(getSpeed());
    }
    
    public static boolean isMoving() {
        return MoveUtils.mc.thePlayer != null && (MoveUtils.mc.thePlayer.movementInput.moveForward != 0.0f || MoveUtils.mc.thePlayer.movementInput.moveStrafe != 0.0f);
    }
    
    public static void strafe(final float speed) {
        if (!isMoving()) {
            return;
        }
        final double yaw = getDirection();
        MoveUtils.mc.thePlayer.motionX = -Math.sin(yaw) * speed;
        MoveUtils.mc.thePlayer.motionZ = Math.cos(yaw) * speed;
    }
    
    public static void strafe(final double speed) {
        if (!isMoving()) {
            return;
        }
        final double yaw = getDirection();
        MoveUtils.mc.thePlayer.motionX = -Math.sin(yaw) * speed;
        MoveUtils.mc.thePlayer.motionZ = Math.cos(yaw) * speed;
    }
    
    public static void forward(final double length) {
        final double yaw = Math.toRadians(MoveUtils.mc.thePlayer.rotationYaw);
        MoveUtils.mc.thePlayer.setPosition(MoveUtils.mc.thePlayer.posX + -Math.sin(yaw) * length, MoveUtils.mc.thePlayer.posY, MoveUtils.mc.thePlayer.posZ + Math.cos(yaw) * length);
    }
    
    public static double getDirection() {
        float rotationYaw = MoveUtils.mc.thePlayer.rotationYaw;
        if (MoveUtils.mc.thePlayer.moveForward < 0.0f) {
            rotationYaw += 180.0f;
        }
        float forward = 1.0f;
        if (MoveUtils.mc.thePlayer.moveForward < 0.0f) {
            forward = -0.5f;
        }
        else if (MoveUtils.mc.thePlayer.moveForward > 0.0f) {
            forward = 0.5f;
        }
        if (MoveUtils.mc.thePlayer.moveStrafing > 0.0f) {
            rotationYaw -= 90.0f * forward;
        }
        if (MoveUtils.mc.thePlayer.moveStrafing < 0.0f) {
            rotationYaw += 90.0f * forward;
        }
        return Math.toRadians(rotationYaw);
    }
    
    public static double getDirectionWithYaw(final float yaw) {
        float rotationYaw = yaw;
        if (MoveUtils.mc.thePlayer.moveForward < 0.0f) {
            rotationYaw += 180.0f;
        }
        float forward = 1.0f;
        if (MoveUtils.mc.thePlayer.moveForward < 0.0f) {
            forward = -0.5f;
        }
        else if (MoveUtils.mc.thePlayer.moveForward > 0.0f) {
            forward = 0.5f;
        }
        if (MoveUtils.mc.thePlayer.moveStrafing > 0.0f) {
            rotationYaw -= 90.0f * forward;
        }
        if (MoveUtils.mc.thePlayer.moveStrafing < 0.0f) {
            rotationYaw += 90.0f * forward;
        }
        return Math.toRadians(rotationYaw);
    }
}
