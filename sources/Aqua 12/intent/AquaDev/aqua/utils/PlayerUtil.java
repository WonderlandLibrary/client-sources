// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.utils;

import net.minecraft.util.MathHelper;
import net.minecraft.util.BlockPos;
import events.listeners.EventPlayerMove;
import net.minecraft.client.Minecraft;

public class PlayerUtil
{
    public static double getSpeed() {
        return Math.sqrt(Minecraft.getMinecraft().thePlayer.motionX * Minecraft.getMinecraft().thePlayer.motionX + Minecraft.getMinecraft().thePlayer.motionZ * Minecraft.getMinecraft().thePlayer.motionZ);
    }
    
    public static void setSpeed(final double speed) {
        final Minecraft mc = Minecraft.getMinecraft();
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
    
    public static void setSpeed1(final EventPlayerMove moveEvent, final double moveSpeed) {
        final Minecraft mc = Minecraft.getMinecraft();
        setSpeed1(moveEvent, moveSpeed, mc.thePlayer.rotationYaw, mc.thePlayer.movementInput.moveStrafe, mc.thePlayer.movementInput.moveForward);
    }
    
    public static void setSpeed1(final EventPlayerMove moveEvent, final double moveSpeed, final float pseudoYaw, final double pseudoStrafe, final double pseudoForward) {
        double forward = pseudoForward;
        double strafe = pseudoStrafe;
        float yaw = pseudoYaw;
        if (forward != 0.0) {
            if (strafe > 0.0) {
                yaw += ((forward > 0.0) ? -45 : 45);
            }
            else if (strafe < 0.0) {
                yaw += ((forward > 0.0) ? 45 : -45);
            }
            strafe = 0.0;
            if (forward > 0.0) {
                forward = 1.0;
            }
            else if (forward < 0.0) {
                forward = -1.0;
            }
        }
        if (strafe > 0.0) {
            strafe = 1.0;
        }
        else if (strafe < 0.0) {
            strafe = -1.0;
        }
        final double mx = Math.cos(Math.toRadians(yaw + 90.0f));
        final double mz = Math.sin(Math.toRadians(yaw + 90.0f));
        moveEvent.setX(forward * moveSpeed * mx + strafe * moveSpeed * mz);
        moveEvent.setZ(forward * moveSpeed * mz - strafe * moveSpeed * mx);
    }
    
    public static double getDistanceToBlock(final BlockPos pos) {
        final double f = Minecraft.getMinecraft().thePlayer.posX - pos.getX();
        final double f2 = Minecraft.getMinecraft().thePlayer.posY - pos.getY();
        final double f3 = Minecraft.getMinecraft().thePlayer.posZ - pos.getZ();
        return MathHelper.sqrt_double(f * f + f2 * f2 + f3 * f3);
    }
}
