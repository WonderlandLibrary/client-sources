/*
 * Decompiled with CFR 0.145.
 */
package us.amerikan.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class Rotation {
    private static Minecraft mc = Minecraft.getMinecraft();

    public static float[] getRotationsForEntity(EntityLivingBase entity) {
        double diffY1;
        if (entity == null) {
            return null;
        }
        Minecraft.getMinecraft();
        double diffX = entity.posX - Minecraft.thePlayer.posX;
        if (entity instanceof EntityLivingBase) {
            EntityLivingBase entityLivingBase = entity;
            Minecraft.getMinecraft();
            Minecraft.getMinecraft();
            diffY1 = entityLivingBase.posY + (double)entityLivingBase.getEyeHeight() * 0.9 - Minecraft.thePlayer.posY + (double)Minecraft.thePlayer.getEyeHeight();
        } else {
            Minecraft.getMinecraft();
            Minecraft.getMinecraft();
            diffY1 = (entity.boundingBox.minY + entity.boundingBox.maxY) / 2.0 - Minecraft.thePlayer.posY + (double)Minecraft.thePlayer.getEyeHeight();
        }
        Minecraft.getMinecraft();
        double diffZ = entity.posZ - Minecraft.thePlayer.posZ;
        double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
        float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0 / 3.141592653589793) - 90.0f;
        float pitch = (float)(-(Math.atan2(diffY1, dist) * 180.0 / 3.141592653589793));
        return new float[]{MathHelper.wrapAngleTo180_float(yaw), MathHelper.wrapAngleTo180_float(pitch)};
    }

    public static float[] getRotationsForPoint(Vec3 point) {
        if (point == null) {
            return null;
        }
        Minecraft.getMinecraft();
        double diffX = point.xCoord - Minecraft.thePlayer.posX;
        Minecraft.getMinecraft();
        Minecraft.getMinecraft();
        double diffY = point.yCoord - Minecraft.thePlayer.posY + (double)Minecraft.thePlayer.getEyeHeight();
        Minecraft.getMinecraft();
        double diffZ = point.zCoord - Minecraft.thePlayer.posZ;
        double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
        float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0 / 3.141592653589793) - 90.0f;
        float pitch = (float)(-(Math.atan2(diffY, dist) * 180.0 / 3.141592653589793));
        return new float[]{MathHelper.wrapAngleTo180_float(yaw), MathHelper.wrapAngleTo180_float(pitch)};
    }

    public static float[] getRotationsForBlock(BlockPos bp2) {
        double diffX1;
        double diffZ1;
        if (bp2 == null) {
            return null;
        }
        if (bp2.getX() < 0) {
            Minecraft.getMinecraft();
            diffX1 = ((double)bp2.getX() - 0.5 - Minecraft.thePlayer.posX) * 0.35;
        } else {
            Minecraft.getMinecraft();
            diffX1 = ((double)bp2.getX() + 0.5 - Minecraft.thePlayer.posX) * 0.35;
        }
        Minecraft.getMinecraft();
        double diffY = (double)bp2.getY() - 0.5 - Minecraft.thePlayer.posY;
        if (bp2.getZ() < 0) {
            Minecraft.getMinecraft();
            diffZ1 = ((double)bp2.getZ() - 0.5 - Minecraft.thePlayer.posZ) * 0.35;
        } else {
            Minecraft.getMinecraft();
            diffZ1 = ((double)bp2.getZ() + 0.5 - Minecraft.thePlayer.posZ) * 0.35;
        }
        double dist = MathHelper.sqrt_double(diffX1 * diffX1 + diffZ1 * diffZ1);
        float yaw = (float)(Math.atan2(diffZ1, diffX1) * 180.0 / 3.141592653589793) - 90.0f;
        float pitch = (float)(-(Math.atan2(diffY, dist) * 180.0 / 3.141592653589793));
        return new float[]{MathHelper.wrapAngleTo180_float(yaw), MathHelper.wrapAngleTo180_float(pitch)};
    }

    public static boolean isInRange(double before, float after, float max) {
        while (before > 360.0) {
            before -= 360.0;
        }
        while (before < 0.0) {
            before += 360.0;
        }
        while (after > 360.0f) {
            after -= 360.0f;
        }
        while (after < 0.0f) {
            after += 360.0f;
        }
        if (before > (double)after) {
            if (before - (double)after > 180.0 && 360.0 - before - (double)after <= (double)max) {
                return true;
            }
            if (before - (double)after <= (double)max) {
                return true;
            }
        } else {
            if ((double)after - before > 180.0 && (double)(360.0f - after) - before <= (double)max) {
                return true;
            }
            if ((double)after - before <= (double)max) {
                return true;
            }
        }
        return false;
    }

    public static int getRotation(double before, float after) {
        while (before > 360.0) {
            before -= 360.0;
        }
        while (before < 0.0) {
            before += 360.0;
        }
        while (after > 360.0f) {
            after -= 360.0f;
        }
        while (after < 0.0f) {
            after += 360.0f;
        }
        if (before > (double)after) {
            if (before - (double)after > 180.0 + Math.random() * 5.0 - 5.0) {
                return 1;
            }
            return -1;
        }
        if ((double)after - before > 180.0 + Math.random() * 5.0 - 5.0) {
            return -1;
        }
        return 1;
    }

    public static float getYawToBlock(BlockPos bp2) {
        return Rotation.getRotationsForBlock(bp2)[0];
    }

    public static float getPitchToBlock(BlockPos bp2) {
        return Rotation.getRotationsForBlock(bp2)[1];
    }

    public static float getYawToPoint(Vec3 p2) {
        float[] rotations = Rotation.getRotationsForPoint(p2);
        return rotations[0];
    }

    public static float getPitchToPoint(Vec3 p2) {
        float[] rotations = Rotation.getRotationsForPoint(p2);
        return rotations[1];
    }

    public static float getYawToEntity(EntityLivingBase e2) {
        float[] rotations = Rotation.getRotationsForEntity(e2);
        return rotations[0];
    }

    public static float getPitchToEntity(EntityLivingBase e2) {
        float[] rotations = Rotation.getRotationsForEntity(e2);
        return rotations[1];
    }
}

