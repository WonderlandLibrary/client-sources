/*
 * Decompiled with CFR 0.152.
 */
package drunkclient.beta.UTILS.Math;

import drunkclient.beta.UTILS.Math.Vec3d;
import drunkclient.beta.UTILS.world.MovementUtil;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class RotationUtils {
    private static final Minecraft mc = Minecraft.getMinecraft();

    public static float[] getRotsByPos(double posX, double posY, double posZ) {
        EntityPlayerSP player = Minecraft.thePlayer;
        double x = posX - player.posX;
        double y = posY - player.posY + (double)player.getEyeHeight();
        double z = posZ - player.posZ;
        double dist = MathHelper.sqrt_double(x * x + z * z);
        float yaw = (float)(Math.atan2(z, x) * 180.0 / Math.PI) - 90.0f;
        float pitch = (float)(-(Math.atan2(y, dist) * 180.0 / Math.PI));
        return new float[]{yaw, pitch};
    }

    public static float[] faceTarget(Entity target, float p_706252, float p_706253, boolean miss) {
        double var7;
        double var4 = target.posX - Minecraft.thePlayer.posX;
        double var5 = target.posZ - Minecraft.thePlayer.posZ;
        if (target instanceof EntityLivingBase) {
            EntityLivingBase var6 = (EntityLivingBase)target;
            var7 = var6.posY + (double)var6.getEyeHeight() - (Minecraft.thePlayer.posY + (double)Minecraft.thePlayer.getEyeHeight());
        } else {
            var7 = (target.getEntityBoundingBox().minY + target.getEntityBoundingBox().maxY) / 2.0 - (Minecraft.thePlayer.posY + (double)Minecraft.thePlayer.getEyeHeight());
        }
        Random rnd = new Random();
        double var8 = MathHelper.sqrt_double(var4 * var4 + var5 * var5);
        float var9 = (float)(Math.atan2(var5, var4) * 180.0 / Math.PI) - 90.0f;
        float var10 = (float)(-(Math.atan2(var7 - (target instanceof EntityPlayer ? 0.25 : 0.0), var8) * 180.0 / Math.PI));
        float pitch = RotationUtils.changeRotation(Minecraft.thePlayer.rotationPitch, var10, p_706253);
        float yaw = RotationUtils.changeRotation(Minecraft.thePlayer.rotationYaw, var9, p_706252);
        return new float[]{yaw, pitch};
    }

    public static float changeRotation(float p_706631, float p_706632, float p_706633) {
        float var4 = MathHelper.wrapAngleTo180_float(p_706632 - p_706631);
        if (var4 > p_706633) {
            var4 = p_706633;
        }
        if (!(var4 < -p_706633)) return p_706631 + var4;
        var4 = -p_706633;
        return p_706631 + var4;
    }

    public static float[] faceBlock(BlockPos target) {
        EntityOtherPlayerMP entityOtherPlayerMP = new EntityOtherPlayerMP(RotationUtils.mc.theWorld, Minecraft.thePlayer.getGameProfile());
        entityOtherPlayerMP.setPositionAndRotation((double)target.getX() + 0.5, target.getY() - 1, (double)target.getZ() + 0.5, Minecraft.thePlayer.rotationYaw, Minecraft.thePlayer.rotationPitch);
        entityOtherPlayerMP.rotationYawHead = Minecraft.thePlayer.rotationYawHead;
        entityOtherPlayerMP.setSneaking(Minecraft.thePlayer.isSneaking());
        float[] rots = RotationUtils.getRotations(entityOtherPlayerMP);
        return new float[]{rots[0], rots[1]};
    }

    public static Vec3 getVectorForRotation(float[] rotation) {
        float yawCos = MathHelper.cos(-rotation[0] * ((float)Math.PI / 180) - (float)Math.PI);
        float yawSin = MathHelper.sin(-rotation[0] * ((float)Math.PI / 180) - (float)Math.PI);
        float pitchCos = -MathHelper.cos(-rotation[1] * ((float)Math.PI / 180));
        float pitchSin = MathHelper.sin(-rotation[1] * ((float)Math.PI / 180));
        return new Vec3(yawSin * pitchCos, pitchSin, yawCos * pitchCos);
    }

    public static float[] getRotations(Entity target) {
        double var7;
        double var4 = target.posX - Minecraft.thePlayer.posX;
        double var5 = target.posZ - Minecraft.thePlayer.posZ;
        if (target instanceof EntityLivingBase) {
            EntityLivingBase var6 = (EntityLivingBase)target;
            var7 = var6.posY + (double)var6.getEyeHeight() - (Minecraft.thePlayer.posY + (double)Minecraft.thePlayer.getEyeHeight());
        } else {
            var7 = (target.getEntityBoundingBox().minY + target.getEntityBoundingBox().maxY) / 2.0 - (Minecraft.thePlayer.posY + (double)Minecraft.thePlayer.getEyeHeight());
        }
        double var8 = MathHelper.sqrt_double(var4 * var4 + var5 * var5);
        float var9 = (float)(Math.atan2(var5, var4) * 180.0 / Math.PI) - 90.0f;
        float var10 = (float)(-(Math.atan2(var7 - (target instanceof EntityPlayer ? 0.25 : 0.0), var8) * 180.0 / Math.PI));
        float pitch = RotationUtils.changeRotation(Minecraft.thePlayer.rotationPitch, var10);
        float yaw = RotationUtils.changeRotation(Minecraft.thePlayer.rotationYaw, var9);
        return new float[]{yaw, pitch};
    }

    public static float changeRotation(float p_706631, float p_706632) {
        float var4 = MathHelper.wrapAngleTo180_float(p_706632 - p_706631);
        if (var4 > 1000.0f) {
            var4 = 1000.0f;
        }
        if (!(var4 < -1000.0f)) return p_706631 + var4;
        var4 = -1000.0f;
        return p_706631 + var4;
    }

    public static float[] faceEntitySmooth(double curYaw, double curPitch, double intendedYaw, double intendedPitch, double yawSpeed, double pitchSpeed) {
        float yaw = RotationUtils.updateRotation((float)curYaw, (float)intendedYaw, (float)yawSpeed);
        float pitch = RotationUtils.updateRotation((float)curPitch, (float)intendedPitch, (float)pitchSpeed);
        return new float[]{yaw, pitch};
    }

    public static float[] getNeededRotationsForAAC(EntityLivingBase eyesPos) {
        double diffX = eyesPos.posX - Minecraft.thePlayer.posX;
        double diffY = eyesPos.posY + (double)eyesPos.getEyeHeight() - (Minecraft.thePlayer.posY + (double)Minecraft.thePlayer.getEyeHeight());
        double diffZ = eyesPos.posZ - Minecraft.thePlayer.posZ;
        double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
        float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f;
        float pitch = (float)(-Math.toDegrees(Math.atan2(diffY, diffXZ)));
        return new float[]{MathHelper.wrapAngleTo180_float(yaw), MathHelper.wrapAngleTo180_float(pitch)};
    }

    public float[] getRotaions(Entity e) {
        Minecraft.getMinecraft();
        double d = Minecraft.thePlayer.posX;
        Minecraft.getMinecraft();
        double d2 = Minecraft.thePlayer.posY;
        Minecraft.getMinecraft();
        double d3 = d2 + (double)Minecraft.thePlayer.getEyeHeight();
        Minecraft.getMinecraft();
        Vec3d eyesPos = new Vec3d(d, d3, Minecraft.thePlayer.posZ);
        AxisAlignedBB bb = e.getEntityBoundingBox();
        Vec3d vec = new Vec3d(bb.minX + (bb.maxX - bb.minX) * 0.5, bb.minY + (bb.maxY - bb.minY) * 0.5, bb.minZ + (bb.maxZ - bb.minZ) * 0.5);
        double diffX = vec.xCoord - eyesPos.xCoord;
        double diffY = vec.yCoord - eyesPos.yCoord;
        double diffZ = vec.zCoord - eyesPos.zCoord;
        double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
        float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f;
        float pitch = (float)(-Math.toDegrees(Math.atan2(diffY, diffXZ)));
        return new float[]{MathHelper.wrapAngleTo180_float(yaw), MathHelper.wrapAngleTo180_float(pitch)};
    }

    public static float updateRotation(float currentRotation, float intendedRotation, float increment) {
        float f = MathHelper.wrapAngleTo180_float(intendedRotation - currentRotation);
        if (f > increment) {
            f = increment;
        }
        if (!(f < -increment)) return currentRotation + f;
        f = -increment;
        return currentRotation + f;
    }

    public static float getAngleChange(EntityLivingBase entityIn) {
        float yaw = RotationUtils.getNeededRotations(entityIn)[0];
        float pitch = RotationUtils.getNeededRotations(entityIn)[1];
        float playerYaw = Minecraft.thePlayer.rotationYaw;
        float playerPitch = Minecraft.thePlayer.rotationPitch;
        if (playerYaw < 0.0f) {
            playerYaw += 360.0f;
        }
        if (playerPitch < 0.0f) {
            playerPitch += 360.0f;
        }
        if (yaw < 0.0f) {
            yaw += 360.0f;
        }
        if (pitch < 0.0f) {
            pitch += 360.0f;
        }
        float yawChange = Math.max(playerYaw, yaw) - Math.min(playerYaw, yaw);
        float pitchChange = Math.max(playerPitch, pitch) - Math.min(playerPitch, pitch);
        return yawChange + pitchChange;
    }

    public static float[] getNeededRotations(EntityLivingBase entityIn) {
        double d0 = entityIn.posX - Minecraft.thePlayer.posX;
        double d1 = entityIn.posZ - Minecraft.thePlayer.posZ;
        double d2 = entityIn.posY + (double)entityIn.getEyeHeight() - (Minecraft.thePlayer.getEntityBoundingBox().minY + (double)Minecraft.thePlayer.getEyeHeight());
        double d3 = MathHelper.sqrt_double(d0 * d0 + d1 * d1);
        float f = (float)(MathHelper.func_181159_b(d1, d0) * 180.0 / Math.PI) - 90.0f;
        float f1 = (float)(-(MathHelper.func_181159_b(d2, d3) * 180.0 / Math.PI));
        return new float[]{f, f1};
    }

    public static float updateRotation3(float current, float intended, float factor) {
        float var4 = MathHelper.wrapAngleTo180_float(intended - current);
        if (var4 > factor) {
            var4 = factor;
        }
        if (!(var4 < -factor)) return current + var4;
        var4 = -factor;
        return current + var4;
    }

    public static float[] getRotations(double posX, double posY, double posZ) {
        EntityPlayerSP player = Minecraft.thePlayer;
        double x = posX - player.posX;
        double y = posY - (player.posY + (double)player.getEyeHeight());
        double z = posZ - player.posZ;
        double dist = MathHelper.sqrt_double(x * x + z * z);
        float yaw = (float)(Math.atan2(z, x) * 180.0 / Math.PI) - 90.0f;
        float pitch = (float)(-(Math.atan2(y, dist) * 180.0 / Math.PI));
        return new float[]{yaw, pitch};
    }

    public static float getDistanceToEntity(EntityLivingBase entityLivingBase) {
        return Minecraft.thePlayer.getDistanceToEntity(entityLivingBase);
    }

    public static boolean isOnSameTeam(EntityLivingBase entity) {
        if (entity.getTeam() == null) return false;
        if (Minecraft.thePlayer.getTeam() == null) return false;
        char c1 = entity.getDisplayName().getFormattedText().charAt(1);
        char c2 = Minecraft.thePlayer.getDisplayName().getFormattedText().charAt(1);
        if (c1 != c2) return false;
        return true;
    }

    public static float[] getRotationsEntity(EntityLivingBase entity) {
        if (!MovementUtil.isMoving()) return RotationUtils.getRotations(entity.posX, entity.posY + (double)entity.getEyeHeight() - 0.4, entity.posZ);
        return RotationUtils.getRotations(entity.posX + RotationUtils.randomNumber(0.03, -0.03), entity.posY + (double)entity.getEyeHeight() - 0.4 + RotationUtils.randomNumber(0.07, -0.07), entity.posZ + RotationUtils.randomNumber(0.03, -0.03));
    }

    public static double randomNumber(double max, double min) {
        return Math.random() * (max - min) + min;
    }

    public static float[] getRotations1(Entity e) {
        double deltaX = e.posX + (e.posX - e.lastTickPosX) - Minecraft.thePlayer.posX;
        double deltaY = e.posY - 3.5 + (double)e.getEyeHeight() - Minecraft.thePlayer.posY + (double)Minecraft.thePlayer.getEyeHeight();
        double deltaZ = e.posZ + (e.posZ - e.lastTickPosZ) - Minecraft.thePlayer.posZ;
        double distance = Math.sqrt(Math.pow(deltaX, 2.0) + Math.pow(deltaZ, 2.0));
        float yaw = (float)Math.toDegrees(-Math.atan(deltaX / deltaZ)) + (float)(Math.random() * 2.0) - 1.0f;
        float pitch = (float)(-Math.toDegrees(Math.atan(deltaY / distance))) + (float)(Math.random() * 2.0) - 1.0f;
        double v = Math.toDegrees(Math.atan(deltaZ / deltaX));
        if (deltaX < 0.0 && deltaZ < 0.0) {
            yaw = (float)(90.0 + v);
            return new float[]{yaw, pitch};
        } else {
            if (!(deltaX > 0.0) || !(deltaZ < 0.0)) return new float[]{yaw, pitch};
            yaw = (float)(-90.0 + v);
        }
        return new float[]{yaw, pitch};
    }

    public static float[] getDirectionToBlock(int var0, int var1, int var2, EnumFacing var3) {
        EntityEgg var4 = new EntityEgg(RotationUtils.mc.theWorld);
        var4.posX = (double)var0 + 0.5;
        var4.posY = (double)var1 + 0.5;
        var4.posZ = (double)var2 + 0.5;
        var4.posX += (double)var3.getDirectionVec().getX() * 0.25;
        var4.posY += (double)var3.getDirectionVec().getY() * 0.25;
        var4.posZ += (double)var3.getDirectionVec().getZ() * 0.25;
        return RotationUtils.getDirectionToEntity(var4);
    }

    private static float[] getDirectionToEntity(Entity var0) {
        float[] fArray = new float[2];
        fArray[0] = RotationUtils.getYaw(var0) + Minecraft.thePlayer.rotationYaw;
        fArray[1] = RotationUtils.getPitch(var0) + Minecraft.thePlayer.rotationPitch;
        return fArray;
    }

    public static float[] getRotationNeededForBlock(EntityPlayer paramEntityPlayer, BlockPos pos) {
        double d1 = (double)pos.getX() - paramEntityPlayer.posX;
        double d2 = (double)pos.getY() + 0.5 - (paramEntityPlayer.posY + (double)paramEntityPlayer.getEyeHeight());
        double d3 = (double)pos.getZ() - paramEntityPlayer.posZ;
        double d4 = Math.sqrt(d1 * d1 + d3 * d3);
        float f1 = (float)(Math.atan2(d3, d1) * 180.0 / Math.PI) - 90.0f;
        float f2 = (float)(-(Math.atan2(d2, d4) * 180.0 / Math.PI));
        return new float[]{f1, f2};
    }

    public static float getYaw(Entity var0) {
        double var5;
        double var1 = var0.posX - Minecraft.thePlayer.posX;
        double var3 = var0.posZ - Minecraft.thePlayer.posZ;
        if (var3 < 0.0 && var1 < 0.0) {
            var5 = 90.0 + Math.toDegrees(Math.atan(var3 / var1));
            return MathHelper.wrapAngleTo180_float(-(Minecraft.thePlayer.rotationYaw - (float)var5));
        }
        if (var3 < 0.0 && var1 > 0.0) {
            var5 = -90.0 + Math.toDegrees(Math.atan(var3 / var1));
            return MathHelper.wrapAngleTo180_float(-(Minecraft.thePlayer.rotationYaw - (float)var5));
        }
        var5 = Math.toDegrees(-Math.atan(var1 / var3));
        return MathHelper.wrapAngleTo180_float(-(Minecraft.thePlayer.rotationYaw - (float)var5));
    }

    public static float getPitch(Entity var0) {
        double var1 = var0.posX - Minecraft.thePlayer.posX;
        double var3 = var0.posZ - Minecraft.thePlayer.posZ;
        double var5 = var0.posY - 1.6 + (double)var0.getEyeHeight() - Minecraft.thePlayer.posY;
        double var7 = MathHelper.sqrt_double(var1 * var1 + var3 * var3);
        double var9 = -Math.toDegrees(Math.atan(var5 / var7));
        return -MathHelper.wrapAngleTo180_float(Minecraft.thePlayer.rotationPitch - (float)var9);
    }

    public static Vec3 getVectorForRotation(float pitch, float yaw) {
        float f = MathHelper.cos(-yaw * ((float)Math.PI / 180) - (float)Math.PI);
        float f2 = MathHelper.sin(-yaw * ((float)Math.PI / 180) - (float)Math.PI);
        float f3 = -MathHelper.cos(-pitch * ((float)Math.PI / 180));
        float f4 = MathHelper.sin(-pitch * ((float)Math.PI / 180));
        return new Vec3(f2 * f3, f4, f * f3);
    }

    public static float getYawChange(float yaw, double posX, double posZ) {
        Minecraft.getMinecraft();
        double deltaX = posX - Minecraft.thePlayer.posX;
        Minecraft.getMinecraft();
        double deltaZ = posZ - Minecraft.thePlayer.posZ;
        double yawToEntity = 0.0;
        if (deltaZ < 0.0 && deltaX < 0.0) {
            if (deltaX == 0.0) return MathHelper.wrapAngleTo180_float(-(yaw - (float)yawToEntity));
            yawToEntity = 90.0 + Math.toDegrees(Math.atan(deltaZ / deltaX));
            return MathHelper.wrapAngleTo180_float(-(yaw - (float)yawToEntity));
        }
        if (deltaZ < 0.0 && deltaX > 0.0) {
            if (deltaX == 0.0) return MathHelper.wrapAngleTo180_float(-(yaw - (float)yawToEntity));
            yawToEntity = -90.0 + Math.toDegrees(Math.atan(deltaZ / deltaX));
            return MathHelper.wrapAngleTo180_float(-(yaw - (float)yawToEntity));
        }
        if (deltaZ == 0.0) return MathHelper.wrapAngleTo180_float(-(yaw - (float)yawToEntity));
        yawToEntity = Math.toDegrees(-Math.atan(deltaX / deltaZ));
        return MathHelper.wrapAngleTo180_float(-(yaw - (float)yawToEntity));
    }

    public static float getPitchChange(float pitch, Entity entity, double posY) {
        double d = entity.posX;
        Minecraft.getMinecraft();
        double deltaX = d - Minecraft.thePlayer.posX;
        double d2 = entity.posZ;
        Minecraft.getMinecraft();
        double deltaZ = d2 - Minecraft.thePlayer.posZ;
        double d3 = posY - 2.2 + (double)entity.getEyeHeight();
        Minecraft.getMinecraft();
        double deltaY = d3 - Minecraft.thePlayer.posY;
        double distanceXZ = MathHelper.sqrt_double(deltaX * deltaX + deltaZ * deltaZ);
        double pitchToEntity = -Math.toDegrees(Math.atan(deltaY / distanceXZ));
        return -MathHelper.wrapAngleTo180_float(pitch - (float)pitchToEntity) - 2.5f;
    }
}

