package ClassSub;

import net.minecraft.client.*;
import net.minecraft.entity.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import java.util.*;
import net.minecraft.util.*;

public class Class339
{
    public static Minecraft mc;
    
    
    public static float[] getRotations(final Entity entity) {
        final double posX = Minecraft.getMinecraft().thePlayer.posX;
        final double n = Minecraft.getMinecraft().thePlayer.posY + Minecraft.getMinecraft().thePlayer.getEyeHeight();
        final double posZ = Minecraft.getMinecraft().thePlayer.posZ;
        final double posX2 = entity.posX;
        final double n2 = entity.posY + entity.height / 2.0f;
        final double posZ2 = entity.posZ;
        final double n3 = posX - posX2;
        final double n4 = n - n2;
        final double n5 = posZ - posZ2;
        return new float[] { (float)(Math.toDegrees(Math.atan2(n5, n3)) + 90.0), (float)(90.0 - Math.toDegrees(Math.atan2(Math.sqrt(Math.pow(n3, 2.0) + Math.pow(n5, 2.0)), n4))) };
    }
    
    private static float[] getDirectionToEntity(final Entity entity) {
        return new float[] { getYaw(entity) + Class339.mc.thePlayer.rotationYaw, getPitch(entity) + Class339.mc.thePlayer.rotationPitch };
    }
    
    public static float[] getDirectionToBlock(final double n, final double n2, final double n3, final EnumFacing enumFacing) {
        final EntityEgg entityEgg = new EntityEgg((World)Class339.mc.theWorld);
        entityEgg.posX = n + 0.5;
        entityEgg.posY = n2 + 0.5;
        entityEgg.posZ = n3 + 0.5;
        final EntityEgg entityEgg2 = entityEgg;
        entityEgg2.posX += enumFacing.getDirectionVec().getX() * 0.25;
        final EntityEgg entityEgg3 = entityEgg;
        entityEgg3.posY += enumFacing.getDirectionVec().getY() * 0.25;
        final EntityEgg entityEgg4 = entityEgg;
        entityEgg4.posZ += enumFacing.getDirectionVec().getZ() * 0.25;
        return getDirectionToEntity((Entity)entityEgg);
    }
    
    public static float[] getRotationNeededForBlock(final EntityPlayer entityPlayer, final BlockPos blockPos) {
        final double n = blockPos.getX() - entityPlayer.posX;
        final double n2 = blockPos.getY() + 0.5 - (entityPlayer.posY + entityPlayer.getEyeHeight());
        final double n3 = blockPos.getZ() - entityPlayer.posZ;
        return new float[] { (float)(Math.atan2(n3, n) * 180.0 / 3.141592653589793) - 90.0f, (float)(-Math.atan2(n2, Math.sqrt(n * n + n3 * n3)) * 180.0 / 3.141592653589793) };
    }
    
    public static float getYaw(final Entity entity) {
        final double n = entity.posX - Class339.mc.thePlayer.posX;
        final double n2 = entity.posZ - Class339.mc.thePlayer.posZ;
        return MathHelper.wrapAngleTo180_float(-Class339.mc.thePlayer.rotationYaw - (float)((n2 < 0.0 && n < 0.0) ? (90.0 + Math.toDegrees(Math.atan(n2 / n))) : ((n2 < 0.0 && n > 0.0) ? (-90.0 + Math.toDegrees(Math.atan(n2 / n))) : Math.toDegrees(-Math.atan(n / n2)))));
    }
    
    public static float getPitch(final Entity entity) {
        final double n = entity.posX - Class339.mc.thePlayer.posX;
        final double n2 = entity.posZ - Class339.mc.thePlayer.posZ;
        return -MathHelper.wrapAngleTo180_float(Class339.mc.thePlayer.rotationPitch - (float)(-Math.toDegrees(Math.atan((entity.posY - 1.6 + entity.getEyeHeight() - Class339.mc.thePlayer.posY) / MathHelper.sqrt_double(n * n + n2 * n2)))));
    }
    
    public static float[] getRotationFromPosition(final double n, final double n2, final double n3) {
        final double n4 = n - Minecraft.getMinecraft().thePlayer.posX;
        final double n5 = n3 - Minecraft.getMinecraft().thePlayer.posZ;
        return new float[] { (float)(Math.atan2(n5, n4) * 180.0 / 3.141592653589793) - 90.0f, (float)(-Math.atan2(n2 - Minecraft.getMinecraft().thePlayer.posY - Minecraft.getMinecraft().thePlayer.getEyeHeight(), MathHelper.sqrt_double(n4 * n4 + n5 * n5)) * 180.0 / 3.141592653589793) };
    }
    
    public static float[] getRotationsNeededBlock(final double n, final double n2, final double n3) {
        final double n4 = n + 0.5 - Minecraft.getMinecraft().thePlayer.posX;
        final double n5 = n3 + 0.5 - Minecraft.getMinecraft().thePlayer.posZ;
        return new float[] { Minecraft.getMinecraft().thePlayer.rotationYaw + MathHelper.wrapAngleTo180_float((float)(Math.atan2(n5, n4) * 180.0 / 3.141592653589793) - 90.0f - Minecraft.getMinecraft().thePlayer.rotationYaw), Minecraft.getMinecraft().thePlayer.rotationPitch + MathHelper.wrapAngleTo180_float((float)(-Math.atan2(n2 + 0.5 - (Minecraft.getMinecraft().thePlayer.posY + Minecraft.getMinecraft().thePlayer.getEyeHeight()), MathHelper.sqrt_double(n4 * n4 + n5 * n5)) * 180.0 / 3.141592653589793) - Minecraft.getMinecraft().thePlayer.rotationPitch) };
    }
    
    public static float[] getHypixelRotationsNeededBlock(final double n, final double n2, final double n3) {
        final double n4 = n + 0.5 - Minecraft.getMinecraft().thePlayer.posX;
        final double n5 = n3 + 0.5 - Minecraft.getMinecraft().thePlayer.posZ;
        return new float[] { Minecraft.getMinecraft().thePlayer.rotationYaw + MathHelper.wrapAngleTo180_float((float)(Math.atan2(n5, n4) * 180.0 / 3.141592653589793) - 90.0f - (120 + new Random().nextInt(2))), Minecraft.getMinecraft().thePlayer.rotationPitch + MathHelper.wrapAngleTo180_float((float)(-Math.atan2(n2 + 0.5 - (Minecraft.getMinecraft().thePlayer.posY + Minecraft.getMinecraft().thePlayer.getEyeHeight()), MathHelper.sqrt_double(n4 * n4 + n5 * n5)) * 180.0 / 3.141592653589793) - Minecraft.getMinecraft().thePlayer.rotationPitch) };
    }
    
    public static float[] getRotationsNeededBlock(final double n, final double n2, final double n3, final double n4, final double n5, final double n6) {
        final double n7 = n4 + 0.5 - n;
        final double n8 = n6 + 0.5 - n3;
        return new float[] { (float)(Math.atan2(n8, n7) * 180.0 / 3.141592653589793) - 90.0f, (float)(-Math.atan2(n5 + 0.5 - (n2 + Minecraft.getMinecraft().thePlayer.getEyeHeight()), MathHelper.sqrt_double(n7 * n7 + n8 * n8)) * 180.0 / 3.141592653589793) };
    }
    
    public static float getTrajAngleSolutionLow(final float n, final float n2, final float n3) {
        final float n4 = 0.006f;
        return (float)Math.toDegrees(Math.atan((n3 * n3 - Math.sqrt(n3 * n3 * n3 * n3 - n4 * (n4 * (n * n) + 2.0f * n2 * (n3 * n3)))) / (n4 * n)));
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
    
    public static Vec3[] getCorners(final AxisAlignedBB axisAlignedBB) {
        return new Vec3[] { new Vec3(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ), new Vec3(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ), new Vec3(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ), new Vec3(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ), new Vec3(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ), new Vec3(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ), new Vec3(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ), new Vec3(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ) };
    }
    
    public static AxisAlignedBB getCloserBox(final AxisAlignedBB axisAlignedBB, final AxisAlignedBB axisAlignedBB2) {
        for (final Vec3 vec3 : getCorners(axisAlignedBB2)) {
            if (isRotationIn(getRotationFromPosition(vec3.xCoord, vec3.yCoord, vec3.zCoord), axisAlignedBB)) {
                return (getDistanceToBox(axisAlignedBB2) < getDistanceToBox(axisAlignedBB)) ? axisAlignedBB2 : axisAlignedBB;
            }
        }
        return axisAlignedBB2;
    }
    
    public static double getDistanceToBox(final AxisAlignedBB axisAlignedBB) {
        return Minecraft.getMinecraft().thePlayer.getDistance((axisAlignedBB.minX + axisAlignedBB.maxX) / 2.0, (axisAlignedBB.minY + axisAlignedBB.maxY) / 2.0, (axisAlignedBB.minZ + axisAlignedBB.maxZ) / 2.0);
    }
    
    public static boolean isRotationIn(final float[] array, final AxisAlignedBB axisAlignedBB) {
        final float[] array2 = getMaxRotations(axisAlignedBB);
        return array2[0] < array[0] && array2[2] < array[1] && array2[1] > array[0] && array2[3] > array[1];
    }
    
    public static float[] getRandomRotationsInBox(final AxisAlignedBB axisAlignedBB) {
        final float[] array = getMaxRotations(axisAlignedBB);
        return new float[] { (float)MathHelper.getRandomDoubleInRange(new Random(), (double)array[0], (double)array[1]), (float)MathHelper.getRandomDoubleInRange(new Random(), (double)array[2], (double)array[3]) };
    }
    
    public static float[] getMaxRotations(final AxisAlignedBB axisAlignedBB) {
        float n = 2.14748365E9f;
        float n2 = -2.14748365E9f;
        float n3 = 2.14748365E9f;
        float n4 = -2.14748365E9f;
        for (final Vec3 vec3 : getCorners(axisAlignedBB)) {
            final float[] array2 = getRotationFromPosition(vec3.xCoord, vec3.yCoord, vec3.zCoord);
            if (array2[0] < n) {
                n = array2[0];
            }
            if (array2[0] > n2) {
                n2 = array2[0];
            }
            if (array2[1] < n3) {
                n3 = array2[1];
            }
            if (array2[1] > n4) {
                n4 = array2[1];
            }
        }
        return new float[] { n, n2, n3, n4 };
    }
    
    public static AxisAlignedBB expandBox(final AxisAlignedBB axisAlignedBB, double n) {
        n = 1.0 - n / 100.0;
        return axisAlignedBB.expand((axisAlignedBB.maxX - axisAlignedBB.minX) * n, 0.12, (axisAlignedBB.maxZ - axisAlignedBB.minZ) * n);
    }
    
    public static AxisAlignedBB contractBox(final AxisAlignedBB axisAlignedBB, double n) {
        n = 1.0 - n / 100.0;
        return axisAlignedBB.contract((axisAlignedBB.maxX - axisAlignedBB.minX) * n, 0.12, (axisAlignedBB.maxZ - axisAlignedBB.minZ) * n);
    }
    
    public static float getYawDifference(final float n, final float n2) {
        final float n3 = (n2 + 180.0f - n) % 360.0f;
        return n3 + ((n3 > 0.0f) ? -180.0f : 180.0f);
    }
    
    public static float getPitchDifference(final float n, final float n2) {
        final float n3 = (n2 + 90.0f - n) % 180.0f;
        return n3 + ((n3 > 0.0f) ? -90.0f : 90.0f);
    }
    
    public static float[] getRotations(final Object o) {
        final Entity entity = (Entity)o;
        final double posX = Minecraft.getMinecraft().thePlayer.posX;
        final double n = Minecraft.getMinecraft().thePlayer.posY + Minecraft.getMinecraft().thePlayer.getEyeHeight();
        final double posZ = Minecraft.getMinecraft().thePlayer.posZ;
        final double posX2 = entity.posX;
        final double n2 = entity.posY + entity.height / 2.0f;
        final double posZ2 = entity.posZ;
        final double n3 = posX - posX2;
        final double n4 = n - n2;
        final double n5 = posZ - posZ2;
        return new float[] { (float)(Math.toDegrees(Math.atan2(n5, n3)) + 90.0), (float)(90.0 - Math.toDegrees(Math.atan2(Math.sqrt(Math.pow(n3, 2.0) + Math.pow(n5, 2.0)), n4))) };
    }
    
    static {
        Class339.mc = Minecraft.getMinecraft();
    }
}
