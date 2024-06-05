package ClassSub;

import net.minecraft.client.*;
import net.minecraft.client.entity.*;
import cn.Hanabi.injection.interfaces.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.entity.player.*;

public class Class45
{
    
    
    public static float[] getNeededRotationsToEntity(final Entity entity) {
        Minecraft.getMinecraft();
        final EntityPlayerSP thePlayer = Minecraft.getMinecraft().thePlayer;
        final double n = entity.posX - thePlayer.posX;
        final double n2 = entity.posY - thePlayer.posY;
        final double n3 = -Math.atan2(n, entity.posZ - thePlayer.posZ) * 57.0;
        final double n4 = -(Math.asin(n2 / thePlayer.getDistanceToEntity(entity)) * 57.0 - 20.0);
        return new float[] { (float)n3, (float)((n4 > 90.0) ? 90.0 : ((n4 < -90.0) ? -90.0 : n4)) };
    }
    
    public static float[] getRotations(final Entity entity) {
        return getRotationFromPosition(entity.posX, entity.posZ, entity.posY + entity.getEyeHeight() / 2.0f);
    }
    
    public static float[] getAverageRotations(final List<Entity> list) {
        double n = 0.0;
        double n2 = 0.0;
        double n3 = 0.0;
        for (final Entity entity : list) {
            n += entity.posX;
            n2 += ((IEntity)entity).getBoundingBox().maxY - 2.0;
            n3 += entity.posZ;
        }
        final float[] array = new float[2];
        final int n4 = 0;
        final double n5 = n / list.size();
        final double n6 = n3 / list.size();
        final double n7;
        array[n4] = getRotationFromPosition(n5, n6, n7 = n2 / list.size())[0];
        array[1] = getRotationFromPosition(n5, n6, n7)[1];
        return array;
    }
    
    public static float[] getBowAngles(final Entity entity) {
        final double n = entity.posX - entity.lastTickPosX;
        final double n2 = entity.posZ - entity.lastTickPosZ;
        final double n3 = Minecraft.getMinecraft().thePlayer.getDistanceToEntity(entity);
        final double n4 = n3 - n3 % 0.8;
        final boolean isSprinting = entity.isSprinting();
        return new float[] { (float)Math.toDegrees(Math.atan2(entity.posZ + n4 / 0.8 * n2 * (isSprinting ? 1.25 : 1.0) - Minecraft.getMinecraft().thePlayer.posZ, entity.posX + n4 / 0.8 * n * (isSprinting ? 1.25 : 1.0) - Minecraft.getMinecraft().thePlayer.posX)) - 90.0f, (float)Math.toDegrees(Math.atan2(Minecraft.getMinecraft().thePlayer.posY + Minecraft.getMinecraft().thePlayer.getEyeHeight() - (entity.posY + entity.getEyeHeight()), Minecraft.getMinecraft().thePlayer.getDistanceToEntity(entity))) };
    }
    
    public static float[] getRotationFromPosition(final double n, final double n2, final double n3) {
        final double n4 = n - Minecraft.getMinecraft().thePlayer.posX;
        final double n5 = n2 - Minecraft.getMinecraft().thePlayer.posZ;
        return new float[] { (float)(Math.atan2(n5, n4) * 180.0 / 3.141592653589793) - 90.0f, (float)(-Math.atan2(n3 - Minecraft.getMinecraft().thePlayer.posY - 1.2, MathHelper.sqrt_double(n4 * n4 + n5 * n5)) * 180.0 / 3.141592653589793) };
    }
    
    public static float getTrajAngleSolutionLow(final float n, final float n2, final float n3) {
        final float n4 = 0.006f;
        return (float)Math.toDegrees(Math.atan((n3 * n3 - Math.sqrt(n3 * n3 * n3 * n3 - n4 * (n4 * (n * n) + 2.0f * n2 * (n3 * n3)))) / (n4 * n)));
    }
    
    public static float getYawChange(final double n, final double n2) {
        final double n3 = n - Minecraft.getMinecraft().thePlayer.posX;
        final double n4 = n2 - Minecraft.getMinecraft().thePlayer.posZ;
        return MathHelper.wrapAngleTo180_float(-Minecraft.getMinecraft().thePlayer.rotationYaw - (float)((n4 < 0.0 && n3 < 0.0) ? (90.0 + Math.toDegrees(Math.atan(n4 / n3))) : ((n4 < 0.0 && n3 > 0.0) ? (-90.0 + Math.toDegrees(Math.atan(n4 / n3))) : Math.toDegrees(-Math.atan(n3 / n4)))));
    }
    
    public static float getYawChangeByHead(final double n, final double n2) {
        final double n3 = n - Minecraft.getMinecraft().thePlayer.posX;
        final double n4 = n2 - Minecraft.getMinecraft().thePlayer.posZ;
        return MathHelper.wrapAngleTo180_float(-Minecraft.getMinecraft().thePlayer.rotationYawHead - (float)((n4 < 0.0 && n3 < 0.0) ? (90.0 + Math.toDegrees(Math.atan(n4 / n3))) : ((n4 < 0.0 && n3 > 0.0) ? (-90.0 + Math.toDegrees(Math.atan(n4 / n3))) : Math.toDegrees(-Math.atan(n3 / n4)))));
    }
    
    public static float getPitchChange(final Entity entity, final double n) {
        final double n2 = entity.posX - Minecraft.getMinecraft().thePlayer.posX;
        final double n3 = entity.posZ - Minecraft.getMinecraft().thePlayer.posZ;
        return -MathHelper.wrapAngleTo180_float(Minecraft.getMinecraft().thePlayer.rotationPitch - (float)(-Math.toDegrees(Math.atan((n - 2.2 + entity.getEyeHeight() - Minecraft.getMinecraft().thePlayer.posY) / MathHelper.sqrt_double(n2 * n2 + n3 * n3))))) - 2.5f;
    }
    
    public static float getNewAngle(float n) {
        if ((n %= 360.0f) >= 180.0f) {
            n -= 360.0f;
        }
        if (n < -180.0f) {
            n += 360.0f;
        }
        return n;
    }
    
    public static float getDistanceBetweenAngles(final float n, final float n2) {
        float n3 = Math.abs(n - n2) % 360.0f;
        if (n3 > 180.0f) {
            n3 = 360.0f - n3;
        }
        return n3;
    }
    
    public static float[] grabBlockRotations(final BlockPos blockPos) {
        return getVecRotation(Minecraft.getMinecraft().thePlayer.getPositionVector().addVector(0.0, (double)Minecraft.getMinecraft().thePlayer.getEyeHeight(), 0.0), new Vec3(blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5));
    }
    
    public static float[] getVecRotation(final Vec3 vec3) {
        return getVecRotation(Minecraft.getMinecraft().thePlayer.getPositionVector().addVector(0.0, (double)Minecraft.getMinecraft().thePlayer.getEyeHeight(), 0.0), vec3);
    }
    
    public static float[] getVecRotation(final Vec3 vec3, final Vec3 vec4) {
        final Vec3 subtract = vec4.subtract(vec3);
        return new float[] { (float)Math.toDegrees(Math.atan2(subtract.zCoord, subtract.xCoord)) - 90.0f, (float)(-Math.toDegrees(Math.atan2(subtract.yCoord, new Vec3(subtract.xCoord, 0.0, subtract.zCoord).lengthVector()))) };
    }
    
    public static float[] faceTarget(final Entity entity, final float n, final float n2, final boolean b) {
        final double n3 = entity.posX - Minecraft.getMinecraft().thePlayer.posX;
        final double n4 = entity.posZ - Minecraft.getMinecraft().thePlayer.posZ;
        double n5;
        if (entity instanceof EntityLivingBase) {
            final EntityLivingBase entityLivingBase = (EntityLivingBase)entity;
            n5 = entityLivingBase.posY + entityLivingBase.getEyeHeight() - (Minecraft.getMinecraft().thePlayer.posY + Minecraft.getMinecraft().thePlayer.getEyeHeight());
        }
        else {
            n5 = (entity.getEntityBoundingBox().minY + entity.getEntityBoundingBox().maxY) / 2.0 - (Minecraft.getMinecraft().thePlayer.posY + Minecraft.getMinecraft().thePlayer.getEyeHeight());
        }
        final Random random = new Random();
        return new float[] { changeRotation(Minecraft.getMinecraft().thePlayer.rotationYaw, (float)(Math.atan2(n4, n3) * 180.0 / 3.141592653589793) - 90.0f, n), changeRotation(Minecraft.getMinecraft().thePlayer.rotationPitch, (float)(-Math.atan2(n5 - ((entity instanceof EntityPlayer) ? 0.25 : 0.0), MathHelper.sqrt_double(n3 * n3 + n4 * n4)) * 180.0 / 3.141592653589793), n2) };
    }
    
    public static float changeRotation(final float n, final float n2, final float n3) {
        float wrapAngleTo180_float = MathHelper.wrapAngleTo180_float(n2 - n);
        if (wrapAngleTo180_float > n3) {
            wrapAngleTo180_float = n3;
        }
        if (wrapAngleTo180_float < -n3) {
            wrapAngleTo180_float = -n3;
        }
        return n + wrapAngleTo180_float;
    }
    
    public static float getYawChangeGiven(final double n, final double n2, final float n3) {
        final double n4 = n - Minecraft.getMinecraft().thePlayer.posX;
        final double n5 = n2 - Minecraft.getMinecraft().thePlayer.posZ;
        double degrees;
        if (n5 < 0.0 && n4 < 0.0) {
            degrees = 90.0 + Math.toDegrees(Math.atan(n5 / n4));
        }
        else if (n5 < 0.0 && n4 > 0.0) {
            degrees = -90.0 + Math.toDegrees(Math.atan(n5 / n4));
        }
        else {
            degrees = Math.toDegrees(-Math.atan(n4 / n5));
        }
        return MathHelper.wrapAngleTo180_float(-(n3 - (float)degrees));
    }
    
    public static float[] getRotationsNeededBlock(final double n, final double n2, final double n3) {
        final double n4 = n + 0.5 - Minecraft.getMinecraft().thePlayer.posX;
        final double n5 = n3 + 0.5 - Minecraft.getMinecraft().thePlayer.posZ;
        return new float[] { Minecraft.getMinecraft().thePlayer.rotationYaw + MathHelper.wrapAngleTo180_float((float)(Math.atan2(n5, n4) * 180.0 / 3.141592653589793) - 90.0f - Minecraft.getMinecraft().thePlayer.rotationYaw), Minecraft.getMinecraft().thePlayer.rotationPitch + MathHelper.wrapAngleTo180_float((float)(-Math.atan2(n2 + 0.5 - (Minecraft.getMinecraft().thePlayer.posY + Minecraft.getMinecraft().thePlayer.getEyeHeight()), MathHelper.sqrt_double(n4 * n4 + n5 * n5)) * 180.0 / 3.141592653589793) - Minecraft.getMinecraft().thePlayer.rotationPitch) };
    }
    
    public static float[] getRotationsNeededBlock(final double n, final double n2, final double n3, final double n4, final double n5, final double n6) {
        final double n7 = n4 + 0.5 - n;
        final double n8 = n6 + 0.5 - n3;
        return new float[] { (float)(Math.atan2(n8, n7) * 180.0 / 3.141592653589793) - 90.0f, (float)(-Math.atan2(n5 + 0.5 - (n2 + Minecraft.getMinecraft().thePlayer.getEyeHeight()), MathHelper.sqrt_double(n7 * n7 + n8 * n8)) * 180.0 / 3.141592653589793) };
    }
}
