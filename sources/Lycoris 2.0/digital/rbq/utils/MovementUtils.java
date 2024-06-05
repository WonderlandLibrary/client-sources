/*
 * Decompiled with CFR 0.150.
 */
package digital.rbq.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import digital.rbq.events.player.MoveEvent;

public final class MovementUtils {
    private static final Minecraft mc = Minecraft.getMinecraft();

    public static void setSpeed(MoveEvent moveEvent, double moveSpeed) {
        MovementUtils.setSpeed(moveEvent, moveSpeed, MovementUtils.mc.thePlayer.rotationYaw, MovementUtils.mc.thePlayer.movementInput.moveStrafe, MovementUtils.mc.thePlayer.movementInput.moveForward);
    }

    public static void setSpeed(MoveEvent moveEvent, double moveSpeed, float pseudoYaw, double pseudoStrafe, double pseudoForward) {
        double forward = pseudoForward;
        double strafe = pseudoStrafe;
        float yaw = pseudoYaw;
        if (forward != 0.0) {
            if (strafe > 0.0) {
                yaw += (float)(forward > 0.0 ? -45 : 45);
            } else if (strafe < 0.0) {
                yaw += (float)(forward > 0.0 ? 45 : -45);
            }
            strafe = 0.0;
            if (forward > 0.0) {
                forward = 1.0;
            } else if (forward < 0.0) {
                forward = -1.0;
            }
        }
        if (strafe > 0.0) {
            strafe = 1.0;
        } else if (strafe < 0.0) {
            strafe = -1.0;
        }
        double mx = Math.cos(Math.toRadians(yaw + 90.0f));
        double mz = Math.sin(Math.toRadians(yaw + 90.0f));
        moveEvent.x = forward * moveSpeed * mx + strafe * moveSpeed * mz;
        moveEvent.z = forward * moveSpeed * mz - strafe * moveSpeed * mx;
    }

    public static double getBaseMoveSpeed() {
        double baseSpeed = 0.2875;
        if (MovementUtils.mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
            baseSpeed *= 1.0 + 0.2 * (double)(MovementUtils.mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1);
        }
        return baseSpeed;
    }

    public static double getJumpBoostModifier(double baseJumpHeight) {
        if (MovementUtils.mc.thePlayer.isPotionActive(Potion.jump)) {
            int amplifier = MovementUtils.mc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier();
            baseJumpHeight += (double)((float)(amplifier + 1) * 0.1f);
        }
        return baseJumpHeight;
    }
}

