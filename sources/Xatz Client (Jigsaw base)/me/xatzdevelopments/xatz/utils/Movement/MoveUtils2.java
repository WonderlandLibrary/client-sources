package me.xatzdevelopments.xatz.utils.Movement;

import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;

public final class MoveUtils2
{
    public static Minecraft mc;
    
    static {
        MoveUtils2.mc = Minecraft.getMinecraft();
    }
    
    public static float getSpeed() {
        return (float)Math.sqrt(MoveUtils2.mc.thePlayer.motionX * MoveUtils2.mc.thePlayer.motionX + MoveUtils2.mc.thePlayer.motionZ * MoveUtils2.mc.thePlayer.motionZ);
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
        return MoveUtils2.mc.thePlayer != null && (MoveUtils2.mc.thePlayer.movementInput.moveForward != 0.0f || MoveUtils2.mc.thePlayer.movementInput.moveStrafe != 0.0f);
    }
    
    public static void strafe(final float speed) {
        if (!isMoving()) {
            return;
        }
        final double yaw = getDirection();
        MoveUtils2.mc.thePlayer.motionX = -Math.sin(yaw) * speed;
        MoveUtils2.mc.thePlayer.motionZ = Math.cos(yaw) * speed;
    }
    
    public static void strafe(final double speed) {
        if (!isMoving()) {
            return;
        }
        final double yaw = getDirection();
        MoveUtils2.mc.thePlayer.motionX = -Math.sin(yaw) * speed;
        MoveUtils2.mc.thePlayer.motionZ = Math.cos(yaw) * speed;
    }
    
    public static void forward(final double length) {
        final double yaw = Math.toRadians(MoveUtils2.mc.thePlayer.rotationYaw);
        MoveUtils2.mc.thePlayer.setPosition(MoveUtils2.mc.thePlayer.posX + -Math.sin(yaw) * length, MoveUtils2.mc.thePlayer.posY, MoveUtils2.mc.thePlayer.posZ + Math.cos(yaw) * length);
    }
    
    public static double getDirection() {
        float rotationYaw = MoveUtils2.mc.thePlayer.rotationYaw;
        if (MoveUtils2.mc.thePlayer.moveForward < 0.0f) {
            rotationYaw += 180.0f;
        }
        float forward = 1.0f;
        if (MoveUtils2.mc.thePlayer.moveForward < 0.0f) {
            forward = -0.5f;
        }
        else if (MoveUtils2.mc.thePlayer.moveForward > 0.0f) {
            forward = 0.5f;
        }
        if (MoveUtils2.mc.thePlayer.moveStrafing > 0.0f) {
            rotationYaw -= 90.0f * forward;
        }
        if (MoveUtils2.mc.thePlayer.moveStrafing < 0.0f) {
            rotationYaw += 90.0f * forward;
        }
        return Math.toRadians(rotationYaw);
    }
    
    public static double getDirectionWithYaw(final float yaw) {
        float rotationYaw = yaw;
        if (MoveUtils2.mc.thePlayer.moveForward < 0.0f) {
            rotationYaw += 180.0f;
        }
        float forward = 1.0f;
        if (MoveUtils2.mc.thePlayer.moveForward < 0.0f) {
            forward = -0.5f;
        }
        else if (MoveUtils2.mc.thePlayer.moveForward > 0.0f) {
            forward = 0.5f;
        }
        if (MoveUtils2.mc.thePlayer.moveStrafing > 0.0f) {
            rotationYaw -= 90.0f * forward;
        }
        if (MoveUtils2.mc.thePlayer.moveStrafing < 0.0f) {
            rotationYaw += 90.0f * forward;
        }
        return Math.toRadians(rotationYaw);
    }
}
