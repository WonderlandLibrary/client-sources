/*
 * Decompiled with CFR 0.152.
 */
package drunkclient.beta.API.events.world;

import drunkclient.beta.API.Event;
import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import net.minecraft.util.MovementInput;

public class EventMove
extends Event {
    public static double x;
    public static double y;
    public static double z;
    private double motionX;
    private double motionY;
    private double motionZ;

    public EventMove(double x, double y, double z) {
        EventMove.x = x;
        EventMove.y = y;
        EventMove.z = z;
        this.motionX = x;
        this.motionY = y;
        this.motionZ = z;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        EventMove.x = x;
    }

    public double getY() {
        return y;
    }

    public static void setY(double y) {
        EventMove.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        EventMove.z = z;
    }

    public double getLegitMotion() {
        return 0.42f;
    }

    public double getJumpBoostModifier(double baseJumpHeight) {
        Minecraft.getMinecraft();
        if (!Minecraft.thePlayer.isPotionActive(Potion.jump)) return baseJumpHeight;
        Minecraft.getMinecraft();
        int amplifier = Minecraft.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier();
        baseJumpHeight += (double)((float)(amplifier + 1) * 0.1f);
        return baseJumpHeight;
    }

    public double getMovementSpeed() {
        double baseSpeed = 0.2873;
        Minecraft.getMinecraft();
        if (!Minecraft.thePlayer.isPotionActive(Potion.moveSpeed)) return baseSpeed;
        Minecraft.getMinecraft();
        int amplifier = Minecraft.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
        baseSpeed *= 1.0 + 0.2 * (double)(amplifier + 1);
        return baseSpeed;
    }

    public void setSpeed(EventMove moveEvent, double moveSpeed, float pseudoYaw, double pseudoStrafe, double pseudoForward) {
        double forward = pseudoForward;
        double strafe = pseudoStrafe;
        float yaw = pseudoYaw;
        if (pseudoForward != 0.0) {
            if (pseudoStrafe > 0.0) {
                yaw = pseudoYaw + (float)(pseudoForward > 0.0 ? -45 : 45);
            } else if (pseudoStrafe < 0.0) {
                yaw = pseudoYaw + (float)(pseudoForward > 0.0 ? 45 : -45);
            }
            strafe = 0.0;
            if (pseudoForward > 0.0) {
                forward = 1.0;
            } else if (pseudoForward < 0.0) {
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
        Minecraft.getMinecraft();
        Minecraft.thePlayer.motionX = forward * moveSpeed * mx + strafe * moveSpeed * mz;
        Minecraft.getMinecraft();
        Minecraft.thePlayer.motionZ = forward * moveSpeed * mz - strafe * moveSpeed * mx;
    }

    public void setSpeed(EventMove moveEvent, double moveSpeed) {
        Minecraft.getMinecraft();
        float f = Minecraft.thePlayer.rotationYaw;
        Minecraft.getMinecraft();
        MovementInput cfr_ignored_0 = Minecraft.thePlayer.movementInput;
        double d = MovementInput.moveStrafe;
        Minecraft.getMinecraft();
        MovementInput cfr_ignored_1 = Minecraft.thePlayer.movementInput;
        this.setSpeed(moveEvent, moveSpeed, f, d, MovementInput.moveForward);
    }

    public double getMotionY(double mY) {
        Minecraft.getMinecraft();
        if (!Minecraft.thePlayer.isPotionActive(Potion.jump)) return mY;
        Minecraft.getMinecraft();
        mY += (double)(Minecraft.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1;
        return mY;
    }
}

