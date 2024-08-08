package me.xatzdevelopments.util;


import me.xatzdevelopments.events.listeners.EventMoveFlying;
import net.minecraft.client.Minecraft;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovementInput;

public class StrafeUtil {

    private final static Minecraft mc = Minecraft.getMinecraft();

    public static void setStrafe() {
        double currentSpeed = Math.sqrt(Math.pow(mc.thePlayer.motionX, 2.0D) + Math.pow(mc.thePlayer.motionZ, 2.0D));
        MovementInput movementInput = mc.thePlayer.movementInput;
        float forward = movementInput.moveForward;
        float strafe = movementInput.moveStrafe;
        float yaw = mc.thePlayer.rotationYaw;
        if (forward != 0.0D) {
            if (strafe > 0.0D) {
                yaw += ((forward > 0.0D) ? -45 : 45);
            } else if (strafe < 0.0D) {
                yaw += ((forward > 0.0D) ? 45 : -45);
            }
            strafe = 0.0F;
            if (forward > 0.0F) {
                forward = 1.0F;
            } else if (forward < 0.0F) {
                forward = -1.0F;
            }
        }
        if (strafe > 0.0D) {
            strafe = 1.0F;
        } else if (strafe < 0.0D) {
            strafe = -1.0F;
        }
        double mx = Math.cos(Math.toRadians((yaw + 90.0F)));
        double mz = Math.sin(Math.toRadians((yaw + 90.0F)));

        double ms = currentSpeed;

        mc.thePlayer.motionX = forward * ms * mx + strafe * ms * mz;
        mc.thePlayer.motionZ = forward * ms * mz - strafe * ms * mx;
    }

    public static void customMoveFlying(EventMoveFlying event, float yaw) {
        float forward = event.getForward();
        float strafe = event.getStrafe();
        float friction = event.getFriction();

        float f = strafe * strafe + forward * forward;

        if (f >= 1.0E-4F) {
            f = MathHelper.sqrt_float(f);

            if (f < 1.0F) {
                f = 1.0F;
            }

            f = friction / f;
            strafe = strafe * f;
            forward = forward * f;
            float f1 = MathHelper.sin(yaw * (float) Math.PI / 180.0F);
            float f2 = MathHelper.cos(yaw * (float) Math.PI / 180.0F);
            mc.thePlayer.motionX += (strafe * f2 - forward * f1);
            mc.thePlayer.motionZ += (forward * f2 + strafe * f1);
        }
    }

    /*
        Credits to LiquidBounce
     */
    public static void customSilentMoveFlying(EventMoveFlying event, float yaw) {
        int dif = (int) ((MathHelper.wrapAngleTo180_float(mc.thePlayer.rotationYaw - yaw - 23.5f - 135) + 180) / 45);

        float strafe = event.getStrafe();
        float forward = event.getForward();
        float friction = event.getFriction();

        float calcForward = 0f;
        float calcStrafe = 0f;

        switch (dif) {
            case 0:
                calcForward = forward;
                calcStrafe = strafe;
                break;
            case 1:
                calcForward += forward;
                calcStrafe -= forward;
                calcForward += strafe;
                calcStrafe += strafe;
                break;
            case 2:
                calcForward = strafe;
                calcStrafe = -forward;
                break;
            case 3:
                calcForward -= forward;
                calcStrafe -= forward;
                calcForward += strafe;
                calcStrafe -= strafe;
                break;
            case 4:
                calcForward = -forward;
                calcStrafe = -strafe;
                break;
            case 5:
                calcForward -= forward;
                calcStrafe += forward;
                calcForward -= strafe;
                calcStrafe -= strafe;
                break;
            case 6:
                calcForward = -strafe;
                calcStrafe = forward;
                break;
            case 7:
                calcForward += forward;
                calcStrafe += forward;
                calcForward -= strafe;
                calcStrafe += strafe;
                break;
        }

        if (calcForward > 1f || calcForward < 0.9f && calcForward > 0.3f || calcForward < -1f || calcForward > -0.9f && calcForward < -0.3f) {
            calcForward *= 0.5f;
        }

        if (calcStrafe > 1f || calcStrafe < 0.9f && calcStrafe > 0.3f || calcStrafe < -1f || calcStrafe > -0.9f && calcStrafe < -0.3f) {
            calcStrafe *= 0.5f;
        }

        float d = calcStrafe * calcStrafe + calcForward * calcForward;

        if (d >= 1.0E-4f) {
            d = MathHelper.sqrt_float(d);
            if (d < 1.0f) d = 1.0f;
            d = friction / d;
            calcStrafe *= d;
            calcForward *= d;
            float yawSin = MathHelper.sin((float) (yaw * Math.PI / 180f));
            float yawCos = MathHelper.cos((float) (yaw * Math.PI / 180f));
            mc.thePlayer.motionX += calcStrafe * yawCos - calcForward * yawSin;
            mc.thePlayer.motionZ += calcForward * yawCos + calcStrafe * yawSin;
        }
    }

}