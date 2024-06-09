package net.minecraft.client.main.neptune.Utils;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.*;

import java.util.*;

import net.minecraft.util.*;
import net.minecraft.entity.player.*;

public class CombatUtils
{
    public static double angleDifference(final double a, final double b) {
        return ((a - b) % 360.0 + 540.0) % 360.0 - 180.0;
    }
    
    public static float[] faceTarget(final Entity target, final float p_70625_2_, final float p_70625_3_, final boolean miss) {
        final double var4 = target.posX - Minecraft.getMinecraft().thePlayer.posX;
        final double var5 = target.posZ - Minecraft.getMinecraft().thePlayer.posZ;
        double var7;
        if (target instanceof EntityLivingBase) {
            final EntityLivingBase var6 = (EntityLivingBase)target;
            var7 = var6.posY + var6.getEyeHeight() - (Minecraft.getMinecraft().thePlayer.posY + Minecraft.getMinecraft().thePlayer.getEyeHeight());
        }
        else {
            var7 = (target.getEntityBoundingBox().minY + target.getEntityBoundingBox().maxY) / 2.0 - (Minecraft.getMinecraft().thePlayer.posY + Minecraft.getMinecraft().thePlayer.getEyeHeight());
        }
        final Random rnd = new Random();
        final float offset = miss ? (rnd.nextInt(15) * 0.25f + 5.0f) : 0.0f;
        final double var8 = MathHelper.sqrt_double(var4 * var4 + var5 * var5);
        final float var9 = (float)(Math.atan2(var5 + offset, var4) * 180.0 / 3.141592653589793) - 90.0f;
        final float var10 = (float)(-(Math.atan2(var7 - ((target instanceof EntityPlayer) ? 0.5f : 0.0f) + offset, var8) * 180.0 / 3.141592653589793));
        final float pitch = changeRotation(Minecraft.getMinecraft().thePlayer.rotationPitch, var10, p_70625_3_);
        final float yaw = changeRotation(Minecraft.getMinecraft().thePlayer.rotationYaw, var9, p_70625_2_);
        return new float[] { yaw, pitch };
    }
    
    public static float changeRotation(final float p_70663_1_, final float p_70663_2_, final float p_70663_3_) {
        float var4 = MathHelper.wrapAngleTo180_float(p_70663_2_ - p_70663_1_);
        if (var4 > p_70663_3_) {
            var4 = p_70663_3_;
        }
        if (var4 < -p_70663_3_) {
            var4 = -p_70663_3_;
        }
        return p_70663_1_ + var4;
    }
    
    public static double[] getRotationToEntity(final Entity entity) {
        final double pX = Minecraft.getMinecraft().thePlayer.posX;
        final double pY = Minecraft.getMinecraft().thePlayer.posY + Minecraft.getMinecraft().thePlayer.getEyeHeight();
        final double pZ = Minecraft.getMinecraft().thePlayer.posZ;
        final double eX = entity.posX;
        final double eY = entity.posY + entity.height / 2.0f;
        final double eZ = entity.posZ;
        final double dX = pX - eX;
        final double dY = pY - eY;
        final double dZ = pZ - eZ;
        final double dH = Math.sqrt(Math.pow(dX, 2.0) + Math.pow(dZ, 2.0));
        final double yaw = Math.toDegrees(Math.atan2(dZ, dX)) + 90.0;
        final double pitch = Math.toDegrees(Math.atan2(dH, dY));
        return new double[] { yaw, 90.0 - pitch };
    }
    
    public static boolean isWithingFOV(final Entity en, float angle) {
        angle *= 0.5;
        final double a = Minecraft.getMinecraft().thePlayer.rotationYaw;
        final double angleDifference = CombatUtils.angleDifference(a, getRotationToEntity(en)[0]);
        return (angleDifference > 0.0 && angleDifference < angle) || (-angle < angleDifference && angleDifference < 0.0);
    }
}
