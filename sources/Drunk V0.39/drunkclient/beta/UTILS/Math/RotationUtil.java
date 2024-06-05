/*
 * Decompiled with CFR 0.152.
 */
package drunkclient.beta.UTILS.Math;

import drunkclient.beta.UTILS.Math.Rotation;
import drunkclient.beta.UTILS.Math.Vec3d;
import drunkclient.beta.UTILS.world.PlayerUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class RotationUtil {
    public static final List<Float> YAW_SPEEDS = new ArrayList<Float>();
    public static Rotation currentRotation;
    public static Rotation lastRotation;
    public static boolean moveToRotation;
    public static boolean jumpFix;
    private float field_76336_a;
    private float field_76334_b;
    private float field_76335_c;

    public static float getYawBetween(float yaw, double srcX, double srcZ, double destX, double destZ) {
        double xDist = destX - srcX;
        double zDist = destZ - srcZ;
        float var1 = (float)(StrictMath.atan2(zDist, xDist) * 180.0 / Math.PI) - 90.0f;
        return yaw + MathHelper.wrapAngleTo180_float(var1 - yaw);
    }

    public static float getYawToEntity(Entity entity) {
        EntityPlayerSP player = PlayerUtil.getLocalPlayer();
        return RotationUtil.getYawBetween(player.rotationYaw, player.posX, player.posZ, entity.posX, entity.posZ);
    }

    public static Rotation fixedRotations(double paramDouble1, double paramDouble2) {
        float f1 = Minecraft.getMinecraft().gameSettings.mouseSensitivity * 0.6f + 0.2f;
        float f2 = f1 * f1 * f1 * 8.0f;
        float f3 = (float)(paramDouble1 - (double)RotationUtil.lastRotation.yaw);
        float f4 = (float)(paramDouble2 - (double)RotationUtil.lastRotation.pitch);
        f3 = (float)((double)f3 - (double)f3 % ((double)f2 * 0.15));
        f4 = (float)((double)f4 - (double)f4 % ((double)f2 * 0.15));
        return new Rotation(RotationUtil.lastRotation.yaw + f3, MathHelper.clamp_float(RotationUtil.lastRotation.pitch + f4, -90.0f, 90.0f));
    }

    public static void updateRotations(float paramFloat1, float paramFloat2) {
        Minecraft.getMinecraft();
        Minecraft.thePlayer.rotationYawHead = paramFloat1;
        Minecraft.getMinecraft();
        Minecraft.thePlayer.rotationPitchHead = paramFloat2;
        while (true) {
            Minecraft.getMinecraft();
            float f = Minecraft.thePlayer.rotationYawHead;
            Minecraft.getMinecraft();
            if (!(f - Minecraft.thePlayer.prevRotationYawHead < -180.0f)) {
                while (true) {
                    Minecraft.getMinecraft();
                    float f2 = Minecraft.thePlayer.rotationYawHead;
                    Minecraft.getMinecraft();
                    if (!(f2 - Minecraft.thePlayer.prevRotationYawHead >= 180.0f)) return;
                    Minecraft.getMinecraft();
                    Minecraft.thePlayer.prevRotationYawHead += 360.0f;
                }
            }
            Minecraft.getMinecraft();
            Minecraft.thePlayer.prevRotationYawHead -= 360.0f;
        }
    }

    public static void resetRotations() {
        currentRotation = null;
        moveToRotation = false;
    }

    public final float updateRotation(float paramFloat1, float paramFloat2, float paramFloat3) {
        float f = MathHelper.wrapAngleTo180_float(paramFloat2 - paramFloat1);
        if (f > paramFloat3) {
            f = paramFloat3;
        }
        if (!(f < -paramFloat3)) return paramFloat1 + f;
        f = -paramFloat3;
        return paramFloat1 + f;
    }

    public final float calcRot(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
        float f = MathHelper.wrapAngleTo180_float(paramFloat2 - paramFloat1);
        if (Math.abs(f) > 90.0f) {
            return MathHelper.clamp_float(paramFloat4 += paramFloat3, 0.0f, 180.0f);
        }
        if (paramFloat4 > 20.0f) {
            return MathHelper.clamp_float(paramFloat4 -= paramFloat3, 0.0f, 180.0f);
        }
        paramFloat4 += paramFloat3;
        return MathHelper.clamp_float(paramFloat4, 0.0f, 180.0f);
    }

    public static final float[] rotationsToVector(Vec3 paramVec3) {
        Minecraft.getMinecraft();
        Vec3 localVec31 = Minecraft.thePlayer.getPositionEyes(1.0f);
        Vec3 localVec32 = paramVec3.subtract(localVec31);
        return new float[]{(float)Math.toDegrees(Math.atan2(localVec32.zCoord, localVec32.xCoord)) - 90.0f, (float)(-Math.toDegrees(Math.atan2(localVec32.yCoord, Math.hypot(localVec32.xCoord, localVec32.zCoord))))};
    }

    public final float[] rotationsToEntityWithBow(Entity paramEntity) {
        Minecraft.getMinecraft();
        float f = Minecraft.thePlayer.getDistanceToEntity(paramEntity);
        Minecraft.getMinecraft();
        double d1 = Math.sqrt(f * Minecraft.thePlayer.getDistanceToEntity(paramEntity)) / 1.5;
        double d = paramEntity.posX + (paramEntity.posX - paramEntity.prevPosX) * d1;
        Minecraft.getMinecraft();
        double d2 = d - Minecraft.thePlayer.posX;
        double d3 = paramEntity.posZ + (paramEntity.posZ - paramEntity.prevPosZ) * d1;
        Minecraft.getMinecraft();
        double d32 = d3 - Minecraft.thePlayer.posZ;
        double d4 = paramEntity.posY + (paramEntity.posY - paramEntity.prevPosY);
        Minecraft.getMinecraft();
        float f2 = Minecraft.thePlayer.getDistanceToEntity(paramEntity);
        Minecraft.getMinecraft();
        double d5 = d4 + (double)(f2 * Minecraft.thePlayer.getDistanceToEntity(paramEntity) / 300.0f) + (double)paramEntity.getEyeHeight();
        Minecraft.getMinecraft();
        double d6 = d5 - Minecraft.thePlayer.posY;
        Minecraft.getMinecraft();
        double d7 = d6 - (double)Minecraft.thePlayer.getEyeHeight();
        Minecraft.getMinecraft();
        double d42 = d7 - Minecraft.thePlayer.motionY;
        return new float[]{(float)Math.toDegrees(Math.atan2(d32, d2)) - 90.0f, (float)(-Math.toDegrees(Math.atan2(d42, Math.hypot(d2, d32))))};
    }

    public final float calculateRotation(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5) {
        float f = MathHelper.wrapAngleTo180_float(paramFloat2 - paramFloat1);
        if (f < -paramFloat4) return paramFloat1 + MathHelper.clamp_float(f, -paramFloat3, paramFloat3);
        if (!(f > paramFloat5)) return paramFloat1;
        return paramFloat1 + MathHelper.clamp_float(f, -paramFloat3, paramFloat3);
    }

    public final void collect(float paramFloat) {
        if (paramFloat < 5.0f) {
            return;
        }
        if (YAW_SPEEDS.size() > 50) {
            YAW_SPEEDS.remove(Collections.min(YAW_SPEEDS));
            return;
        }
        YAW_SPEEDS.add(Float.valueOf(paramFloat));
    }

    public final float readSpeed() {
        if (!YAW_SPEEDS.isEmpty()) return YAW_SPEEDS.get(ThreadLocalRandom.current().nextInt(0, YAW_SPEEDS.size() - 1)).floatValue();
        return 20.0f;
    }

    public final float[] rotationsToPos(BlockPos paramBlockPos) {
        double d = (double)paramBlockPos.getX() + 0.4;
        Minecraft.getMinecraft();
        double d1 = d - Minecraft.thePlayer.posX;
        double d2 = (double)paramBlockPos.getY() + 0.5;
        Minecraft.getMinecraft();
        double d3 = d2 - Minecraft.thePlayer.posY;
        Minecraft.getMinecraft();
        double d22 = d3 - (double)Minecraft.thePlayer.getEyeHeight();
        double d4 = (double)paramBlockPos.getZ() + 0.4;
        Minecraft.getMinecraft();
        double d32 = d4 - Minecraft.thePlayer.posZ;
        return new float[]{(float)Math.toDegrees(Math.atan2(d32, d1)) - 90.0f, (float)(-Math.toDegrees(Math.atan2(d22, Math.hypot(d1, d32))))};
    }

    public static float pitch() {
        return Minecraft.thePlayer.rotationPitch;
    }

    public static void pitch(float pitch) {
        Minecraft.thePlayer.rotationPitch = pitch;
    }

    public static float yaw() {
        return Minecraft.thePlayer.rotationYaw;
    }

    public static void yaw(float yaw) {
        Minecraft.thePlayer.rotationYaw = yaw;
    }

    public static float[] faceTarget(Entity target, float p_706252, float p_706253, boolean miss) {
        double var7;
        double d = target.posX;
        Minecraft.getMinecraft();
        double var4 = d - Minecraft.thePlayer.posX;
        double d2 = target.posZ;
        Minecraft.getMinecraft();
        double var5 = d2 - Minecraft.thePlayer.posZ;
        if (target instanceof EntityLivingBase) {
            EntityLivingBase var6 = (EntityLivingBase)target;
            double d3 = var6.posY + (double)var6.getEyeHeight();
            Minecraft.getMinecraft();
            double d4 = Minecraft.thePlayer.posY;
            Minecraft.getMinecraft();
            var7 = d3 - (d4 + (double)Minecraft.thePlayer.getEyeHeight());
        } else {
            double d5 = (target.getEntityBoundingBox().minY + target.getEntityBoundingBox().maxY) / 2.0;
            Minecraft.getMinecraft();
            double d6 = Minecraft.thePlayer.posY;
            Minecraft.getMinecraft();
            var7 = d5 - (d6 + (double)Minecraft.thePlayer.getEyeHeight());
        }
        Random rnd = new Random();
        double var8 = MathHelper.sqrt_double(var4 * var4 + var5 * var5);
        float var9 = (float)(Math.atan2(var5, var4) * 180.0 / Math.PI) - 90.0f;
        float var10 = (float)(-(Math.atan2(var7 - (target instanceof EntityPlayer ? 0.25 : 0.0), var8) * 180.0 / Math.PI));
        Minecraft.getMinecraft();
        float pitch = RotationUtil.changeRotation(Minecraft.thePlayer.rotationPitch, var10, p_706253);
        Minecraft.getMinecraft();
        float yaw = RotationUtil.changeRotation(Minecraft.thePlayer.rotationYaw, var9, p_706252);
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

    public static double[] getRotationToEntity(Entity entity) {
        double pX = Minecraft.thePlayer.posX;
        double pY = Minecraft.thePlayer.posY + (double)Minecraft.thePlayer.getEyeHeight();
        double pZ = Minecraft.thePlayer.posZ;
        double eX = entity.posX;
        double eY = entity.posY + (double)(entity.height / 2.0f);
        double eZ = entity.posZ;
        double dX = pX - eX;
        double dY = pY - eY;
        double dZ = pZ - eZ;
        double dH = Math.sqrt(Math.pow(dX, 2.0) + Math.pow(dZ, 2.0));
        double yaw = Math.toDegrees(Math.atan2(dZ, dX)) + 90.0;
        double pitch = Math.toDegrees(Math.atan2(dH, dY));
        return new double[]{yaw, 90.0 - pitch};
    }

    public static float[] getRotations(Entity entity) {
        double diffY;
        if (entity == null) {
            return null;
        }
        double diffX = entity.posX - Minecraft.thePlayer.posX;
        double diffZ = entity.posZ - Minecraft.thePlayer.posZ;
        if (entity instanceof EntityLivingBase) {
            EntityLivingBase elb = (EntityLivingBase)entity;
            diffY = elb.posY + ((double)elb.getEyeHeight() - 0.4) - (Minecraft.thePlayer.posY + (double)Minecraft.thePlayer.getEyeHeight());
        } else {
            diffY = (entity.boundingBox.minY + entity.boundingBox.maxY) / 2.0 - (Minecraft.thePlayer.posY + (double)Minecraft.thePlayer.getEyeHeight());
        }
        double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
        float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0 / Math.PI) - 90.0f;
        float pitch = (float)(-Math.atan2(diffY, dist) * 180.0 / Math.PI);
        return new float[]{yaw, pitch};
    }

    public static float getDistanceBetweenAngles(float angle1, float angle2) {
        float angle3 = Math.abs(angle1 - angle2) % 360.0f;
        if (!(angle3 > 180.0f)) return angle3;
        return 0.0f;
    }

    public static int wrapAngleToDirection(float yaw, int zones) {
        int angle = (int)((double)(yaw + (float)(360 / (2 * zones))) + 0.5) % 360;
        if (angle >= 0) return angle / (360 / zones);
        angle += 360;
        return angle / (360 / zones);
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

    public static float[] doScaffoldRotations(Vec3d vec) {
        double d = vec.xCoord;
        Minecraft.getMinecraft();
        double diffX = d - Minecraft.thePlayer.posX;
        double d2 = vec.yCoord;
        Minecraft.getMinecraft();
        double diffY = d2 - Minecraft.thePlayer.boundingBox.minY;
        double d3 = vec.zCoord;
        Minecraft.getMinecraft();
        double diffZ = d3 - Minecraft.thePlayer.posZ;
        double dist = Math.sqrt(diffX * diffX + diffZ * diffZ);
        float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX));
        float pitch = (float)(-Math.toDegrees(Math.atan2(diffY, dist)));
        float[] fArray = new float[2];
        Minecraft.getMinecraft();
        float f = Minecraft.thePlayer.rotationYaw;
        Minecraft.getMinecraft();
        fArray[0] = f + MathHelper.wrapAngleTo180_float(yaw - Minecraft.thePlayer.rotationYaw);
        Minecraft.getMinecraft();
        float f2 = Minecraft.thePlayer.rotationPitch;
        Minecraft.getMinecraft();
        fArray[1] = f2 + MathHelper.wrapAngleTo180_float(pitch - Minecraft.thePlayer.rotationPitch);
        return fArray;
    }

    public static float[] getRotationsToEntity(Entity entity) {
        Minecraft.getMinecraft();
        EntityPlayerSP player = Minecraft.thePlayer;
        double xDist = entity.posX - player.posX;
        double zDist = entity.posZ - player.posZ;
        double entEyeHeight = entity.getEyeHeight();
        double yDist = entity.posY + entEyeHeight - Math.min(Math.max(entity.posY - player.posY, 0.0), entEyeHeight) - (player.posY + (double)player.getEyeHeight());
        double fDist = MathHelper.sqrt_double(xDist * xDist + zDist * zDist);
        Minecraft.getMinecraft();
        float rotationYaw = Minecraft.thePlayer.rotationYaw;
        float var1 = (float)(Math.atan2(zDist, xDist) * 180.0 / Math.PI) - 90.0f;
        float yaw = rotationYaw + MathHelper.wrapAngleTo180_float(var1 - rotationYaw);
        Minecraft.getMinecraft();
        float rotationPitch = Minecraft.thePlayer.rotationPitch;
        float var2 = (float)(-(Math.atan2(yDist, fDist) * 180.0 / Math.PI));
        float pitch = rotationPitch + MathHelper.wrapAngleTo180_float(var2 - rotationPitch);
        return new float[]{yaw, MathHelper.clamp_float(pitch, -90.0f, 90.0f)};
    }

    public static float getAngleChange(EntityLivingBase entityIn) {
        float yaw = RotationUtil.getRotationsToEntity(entityIn)[0];
        float pitch = RotationUtil.getRotationsToEntity(entityIn)[1];
        Minecraft.getMinecraft();
        float playerYaw = Minecraft.thePlayer.rotationYaw;
        Minecraft.getMinecraft();
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

    public static float getDistanceToEntity(EntityLivingBase entityLivingBase) {
        Minecraft.getMinecraft();
        return Minecraft.thePlayer.getDistanceToEntity(entityLivingBase);
    }

    static {
        lastRotation = new Rotation(0.0f, 0.0f);
    }
}

