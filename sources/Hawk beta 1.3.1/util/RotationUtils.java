package eze.util;

import net.minecraft.client.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;

public class RotationUtils
{
    private static Minecraft mc;
    
    static {
        RotationUtils.mc = Minecraft.getMinecraft();
    }
    
    public static float[] getRotations(final EntityLivingBase ent) {
        final double x = ent.posX;
        final double y = ent.posY + ent.getEyeHeight() / 2.0f;
        final double z = ent.posZ;
        return getRotationFromPosition(x, y, z);
    }
    
    public static float[] doScaffoldRotations(final Vec3d vec) {
        final double diffX = vec.xCoord - RotationUtils.mc.thePlayer.posX;
        final double diffY = vec.yCoord - RotationUtils.mc.thePlayer.boundingBox.minY;
        final double diffZ = vec.zCoord - RotationUtils.mc.thePlayer.posZ;
        final double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
        final float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX));
        final float pitch = (float)(-Math.toDegrees(Math.atan2(diffY, dist)));
        return new float[] { RotationUtils.mc.thePlayer.rotationYaw + MathHelper.wrapAngleTo180_float(yaw - RotationUtils.mc.thePlayer.rotationYaw), RotationUtils.mc.thePlayer.rotationPitch + MathHelper.wrapAngleTo180_float(pitch - RotationUtils.mc.thePlayer.rotationPitch) };
    }
    
    public static float[] getBowAngles(final Entity entity) {
        final double xDelta = entity.posX - entity.lastTickPosX;
        final double zDelta = entity.posZ - entity.lastTickPosZ;
        final double d = Minecraft.getMinecraft().thePlayer.getDistanceToEntity(entity) % 0.8;
        final boolean sprint = entity.isSprinting();
        final double xMulti = d / 0.8 * xDelta * (sprint ? 1.25 : 1.0);
        final double zMulti = d / 0.8 * zDelta * (sprint ? 1.25 : 1.0);
        final double x = entity.posX + xMulti - Minecraft.getMinecraft().thePlayer.posX;
        final double z = entity.posZ + zMulti - Minecraft.getMinecraft().thePlayer.posZ;
        final double y = Minecraft.getMinecraft().thePlayer.posY + Minecraft.getMinecraft().thePlayer.getEyeHeight() - (entity.posY + entity.getEyeHeight());
        final double dist = Minecraft.getMinecraft().thePlayer.getDistanceToEntity(entity);
        final float yaw = (float)Math.toDegrees(Math.atan2(z, x)) - 90.0f;
        final float pitch = (float)Math.toDegrees(Math.atan2(y, dist));
        return new float[] { yaw, pitch };
    }
    
    public static float[] getRotationFromPosition(final double x, final double y, final double z) {
        final double xDiff = x - Minecraft.getMinecraft().thePlayer.posX;
        final double zDiff = z - Minecraft.getMinecraft().thePlayer.posZ;
        final double yDiff = y - Minecraft.getMinecraft().thePlayer.posY - 1.2;
        final double dist = MathHelper.sqrt_double(xDiff * xDiff + zDiff * zDiff);
        final float yaw = (float)(Math.atan2(zDiff, xDiff) * 180.0 / 3.141592653589793) - 90.0f;
        final float pitch = (float)(-(Math.atan2(yDiff, dist) * 180.0 / 3.141592653589793));
        return new float[] { yaw, pitch };
    }
}
