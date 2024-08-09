package dev.darkmoon.client.utility.move;

import dev.darkmoon.client.utility.Utility;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class MovementUtility implements Utility {
    public static boolean reason(boolean water) {
        boolean critWater = water && mc.world.getBlockState(new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ)).getBlock()
                instanceof BlockLiquid && mc.world.getBlockState(new BlockPos(mc.player.posX, mc.player.posY + 1,
                mc.player.posZ)).getBlock() instanceof BlockAir;
        return mc.player.isPotionActive(MobEffects.BLINDNESS) || mc.player.isOnLadder()
                || mc.player.isInWater() && !critWater || mc.player.isInWeb || mc.player.capabilities.isFlying;
    }
    public static double getDifferenceOf(final float num1,final float num2) {
        return Math.abs(num2-num1) > Math.abs(num1-num2) ? Math.abs(num1-num2) : Math.abs(num2-num1);
    }
    public static double getDifferenceOf(final double num1,final double num2) {
        return Math.abs(num2-num1) > Math.abs(num1-num2) ? Math.abs(num1-num2) : Math.abs(num2-num1);
    }
    public static double getDifferenceOf(final int num1,final int num2) {
        return Math.abs(num2-num1) > Math.abs(num1-num2) ? Math.abs(num1-num2) : Math.abs(num2-num1);
    }
    public static boolean getUnderBoxes(EntityPlayer player) {
        return !mc.world.getCollisionBoxes(player, player.getEntityBoundingBox().shrink(0.0625D)).isEmpty();
    }

    public static void setSpeed(float speed) {
        float yaw = MovementUtility.mc.player.rotationYaw;
        float forward = MovementUtility.mc.player.movementInput.moveForward;
        float strafe = MovementUtility.mc.player.movementInput.moveStrafe;
        if (forward != 0.0f) {
            if (strafe > 0.0f) {
                yaw += (float)(forward > 0.0f ? -45 : 45);
            } else if (strafe < 0.0f) {
                yaw += (float)(forward > 0.0f ? 45 : -45);
            }
            strafe = 0.0f;
            if (forward > 0.0f) {
                forward = 1.0f;
            } else if (forward < 0.0f) {
                forward = -1.0f;
            }
        }
        MovementUtility.mc.player.motionX = (double)(forward * speed) * Math.cos(Math.toRadians(yaw + 90.0f)) + (double)(strafe * speed) * Math.sin(Math.toRadians(yaw + 90.0f));
        MovementUtility.mc.player.motionZ = (double)(forward * speed) * Math.sin(Math.toRadians(yaw + 90.0f)) - (double)(strafe * speed) * Math.cos(Math.toRadians(yaw + 90.0f));
    }
    public static double getSpeed() {
        return Math.hypot(mc.player.motionX, mc.player.motionZ);
    }
    public static float getSpeed1() {
        return (float)Math.sqrt(mc.player.motionX * mc.player.motionX + mc.player.motionZ * mc.player.motionZ);
    }
    public static double[] forward(final double d) {
        float f = Minecraft.getMinecraft().player.movementInput.moveForward;
        float f2 = Minecraft.getMinecraft().player.movementInput.moveStrafe;
        float f3 = Minecraft.getMinecraft().player.prevRotationYaw + (Minecraft.getMinecraft().player.rotationYaw - Minecraft.getMinecraft().player.prevRotationYaw) * Minecraft.getMinecraft().getRenderPartialTicks();
        if (f != 0.0f) {
            if (f2 > 0.0f) {
                f3 += ((f > 0.0f) ? -45 : 45);
            } else if (f2 < 0.0f) {
                f3 += ((f > 0.0f) ? 45 : -45);
            }
            f2 = 0.0f;
            if (f > 0.0f) {
                f = 1.0f;
            } else if (f < 0.0f) {
                f = -1.0f;
            }
        }
        final double d2 = Math.sin(Math.toRadians(f3 + 90.0f));
        final double d3 = Math.cos(Math.toRadians(f3 + 90.0f));
        final double d4 = f * d * d3 + f2 * d * d2;
        final double d5 = f * d * d2 - f2 * d * d3;
        return new double[]{d4, d5};
    }

    public static boolean isBlockAboveHead() {
        AxisAlignedBB axisAlignedBB = new AxisAlignedBB(mc.player.posX - 0.3, mc.player.posY + mc.player.getEyeHeight(),
                mc.player.posZ + 0.3, mc.player.posX + 0.3, mc.player.posY + (!mc.player.onGround ? 1.5 : 2.5),
                mc.player.posZ - 0.3);
        return !mc.world.getCollisionBoxes(mc.player, axisAlignedBB).isEmpty();
    }

    public static boolean getOffsetBoxes() {
        return mc.world.getCollisionBoxes(mc.player, mc.player.getEntityBoundingBox().offset(0.0D, 0.1D, 0.0D)).isEmpty();
    }

    public static boolean isMoving() {
        return mc.player.movementInput.moveForward != 0.0F || mc.player.movementInput.moveStrafe != 0.0F;
    }

    public static void strafe(double motion) {
        if (!isMoving()) return;
        double radians = getDirection();
        mc.player.motionX = -Math.sin(radians) * motion;
        mc.player.motionZ = Math.cos(radians) * motion;
    }

    public static float getDirection() {
        float rotationYaw = mc.player.rotationYaw;

        float strafeFactor = 0f;

        if (mc.player.movementInput.moveForward > 0)
            strafeFactor = 1;
        if (mc.player.movementInput.moveForward < 0)
            strafeFactor = -1;

        if (strafeFactor == 0) {
            if (mc.player.movementInput.moveStrafe > 0)
                rotationYaw -= 90;

            if (mc.player.movementInput.moveStrafe < 0)
                rotationYaw += 90;
        } else {
            if (mc.player.movementInput.moveStrafe > 0)
                rotationYaw -= 45 * strafeFactor;

            if (mc.player.movementInput.moveStrafe < 0)
                rotationYaw += 45 * strafeFactor;
        }

        if (strafeFactor < 0)
            rotationYaw -= 180;

        return (float) Math.toRadians(rotationYaw);
    }

    public static void setMotion(double motion) {
        double forward = mc.player.movementInput.moveForward;
        double strafe = mc.player.movementInput.moveStrafe;
        float yaw = mc.player.rotationYaw;
        if (forward == 0 && strafe == 0) {
            mc.player.motionX = 0;
            mc.player.motionZ = 0;
        } else {
            if (forward != 0) {
                if (strafe > 0) {
                    yaw += (float) (forward > 0 ? -45 : 45);
                } else if (strafe < 0) {
                    yaw += (float) (forward > 0 ? 45 : -45);
                }
                strafe = 0;
                if (forward > 0) {
                    forward = 1;
                } else if (forward < 0) {
                    forward = -1;
                }
            }
            double sin = MathHelper.sin((float) Math.toRadians(yaw + 90));
            double cos = MathHelper.cos((float) Math.toRadians(yaw + 90));
            mc.player.motionX = forward * motion * cos + strafe * motion * sin;
            mc.player.motionZ = forward * motion * sin - strafe * motion * cos;

        }
    }

    public static float getMotion() {
        return (float) Math.sqrt(mc.player.motionX * mc.player.motionX + mc.player.motionZ * mc.player.motionZ);
    }

    public static boolean isInLiquid() {
        return mc.player.isInWater() || mc.player.isInLava();
    }
}
