package Focus.Beta.UTILS.Math;

import Focus.Beta.UTILS.world.PlayerUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import Focus.Beta.UTILS.helper.Helper;


public class RotationUtil {
    public static final List<Float> YAW_SPEEDS = new ArrayList<>();
    public static Rotation currentRotation;
    public static Rotation lastRotation = new Rotation(0.0F, 0.0F);
    public static boolean moveToRotation;
    public static boolean jumpFix;
    private float field_76336_a;
    private float field_76334_b;
    private float field_76335_c;



    public static float getYawBetween(float yaw, double srcX, double srcZ, double destX, double destZ) {
        double xDist = destX - srcX;
        double zDist = destZ - srcZ;
        float var1 = (float)(StrictMath.atan2(zDist, xDist) * 180.0D / 3.141592653589793D) - 90.0F;
        return yaw + MathHelper.wrapAngleTo180_float(var1 - yaw);
    }

    public static float getYawToEntity(Entity entity) {
        EntityPlayerSP player = PlayerUtil.getLocalPlayer();
        return getYawBetween(player.rotationYaw, player.posX, player.posZ, entity.posX, entity.posZ);
    }

    public static Rotation fixedRotations(double paramDouble1, double paramDouble2) {
        float f1 = Minecraft.getMinecraft().gameSettings.mouseSensitivity * 0.6F + 0.2F;
        float f2 = f1 * f1 * f1 * 8.0F;
        float f3 = (float) (paramDouble1 - lastRotation.yaw);
        float f4 = (float) (paramDouble2 - lastRotation.pitch);
        f3 = (float) (f3 - f3 % (f2 * 0.15D));
        f4 = (float) (f4 - f4 % (f2 * 0.15D));
        return new Rotation(lastRotation.yaw + f3, MathHelper.clamp_float(lastRotation.pitch + f4, -90.0F, 90.0F));
    }

    public static void updateRotations(float paramFloat1, float paramFloat2) {
    	Minecraft.getMinecraft().thePlayer.rotationYawHead = paramFloat1;
    	Minecraft.getMinecraft().thePlayer.rotationPitchHead = paramFloat2;
        while (Minecraft.getMinecraft().thePlayer.rotationYawHead - Minecraft.getMinecraft().thePlayer.prevRotationYawHead < -180.0F) {
        	Minecraft.getMinecraft().thePlayer.prevRotationYawHead -= 360.0F;
        }
        while (Minecraft.getMinecraft().thePlayer.rotationYawHead - Minecraft.getMinecraft().thePlayer.prevRotationYawHead >= 180.0F) {
        	Minecraft.getMinecraft().thePlayer.prevRotationYawHead += 360.0F;
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
        if (f < -paramFloat3) {
            f = -paramFloat3;
        }
        return paramFloat1 + f;
    }

    public final float calcRot(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
        float f = MathHelper.wrapAngleTo180_float(paramFloat2 - paramFloat1);
        if (Math.abs(f) > 90.0F) {
            paramFloat4 += paramFloat3;
        } else if (paramFloat4 > 20.0F) {
            paramFloat4 -= paramFloat3;
        } else {
            paramFloat4 += paramFloat3;
        }
        return MathHelper.clamp_float(paramFloat4, 0.0F, 180.0F);
    }

    public static final float[] rotationsToVector(Vec3 paramVec3) {
        Vec3 localVec31 = Minecraft.getMinecraft().thePlayer.getPositionEyes(1.0F);
        Vec3 localVec32 = paramVec3.subtract(localVec31);
        return new float[]{(float) Math.toDegrees(Math.atan2(localVec32.zCoord, localVec32.xCoord)) - 90.0F, (float) -Math.toDegrees(Math.atan2(localVec32.yCoord, Math.hypot(localVec32.xCoord, localVec32.zCoord)))};
    }

    public final float[] rotationsToEntityWithBow(Entity paramEntity) {
        double d1 = Math.sqrt(Minecraft.getMinecraft().thePlayer.getDistanceToEntity(paramEntity) * Minecraft.getMinecraft().thePlayer.getDistanceToEntity(paramEntity)) / 1.5D;
        double d2 = paramEntity.posX + (paramEntity.posX - paramEntity.prevPosX) * d1 - Minecraft.getMinecraft().thePlayer.posX;
        double d3 = paramEntity.posZ + (paramEntity.posZ - paramEntity.prevPosZ) * d1 - Minecraft.getMinecraft().thePlayer.posZ;
        double d4 = paramEntity.posY + (paramEntity.posY - paramEntity.prevPosY) + Minecraft.getMinecraft().thePlayer.getDistanceToEntity(paramEntity) * Minecraft.getMinecraft().thePlayer.getDistanceToEntity(paramEntity) / 300.0F + paramEntity.getEyeHeight() - Minecraft.getMinecraft().thePlayer.posY - Minecraft.getMinecraft().thePlayer.getEyeHeight() - Minecraft.getMinecraft().thePlayer.motionY;
        return new float[]{(float) Math.toDegrees(Math.atan2(d3, d2)) - 90.0F, (float) -Math.toDegrees(Math.atan2(d4, Math.hypot(d2, d3)))};
    }

    public final float calculateRotation(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5) {
        float f = MathHelper.wrapAngleTo180_float(paramFloat2 - paramFloat1);
        if ((f < -paramFloat4) || (f > paramFloat5)) {
            return paramFloat1 + MathHelper.clamp_float(f, -paramFloat3, paramFloat3);
        }
        return paramFloat1;
    }

    public final void collect(float paramFloat) {
        if (paramFloat < 5.0F) {
            return;
        }
        if (YAW_SPEEDS.size() > 50) {
            YAW_SPEEDS.remove(Collections.min(YAW_SPEEDS));
            return;
        }
        YAW_SPEEDS.add(Float.valueOf(paramFloat));
    }

    public final float readSpeed() {
        if (YAW_SPEEDS.isEmpty()) {
            return 20.0F;
        }
        return ((Float) YAW_SPEEDS.get(ThreadLocalRandom.current().nextInt(0, YAW_SPEEDS.size() - 1))).floatValue();
    }

    public final float[] rotationsToPos(BlockPos paramBlockPos) {
        double d1 = paramBlockPos.getX() + 0.4D - Minecraft.getMinecraft().thePlayer.posX;
        double d2 = paramBlockPos.getY() + 0.5D - Minecraft.getMinecraft().thePlayer.posY - Minecraft.getMinecraft().thePlayer.getEyeHeight();
        double d3 = paramBlockPos.getZ() + 0.4D - Minecraft.getMinecraft().thePlayer.posZ;
        return new float[]{(float) Math.toDegrees(Math.atan2(d3, d1)) - 90.0F, (float) -Math.toDegrees(Math.atan2(d2, Math.hypot(d1, d3)))};
    }
    
    public static float pitch() {
        return Helper.mc.thePlayer.rotationPitch;
    }

    public static void pitch(float pitch) {
        Helper.mc.thePlayer.rotationPitch = pitch;
    }

    public static float yaw() {
        return Helper.mc.thePlayer.rotationYaw;
    }

    public static void yaw(float yaw) {
        Helper.mc.thePlayer.rotationYaw = yaw;
    }

    public static float[] faceTarget(final Entity target, final float p_706252, final float p_706253,
                                     final boolean miss) {
        final double var4 = target.posX - Minecraft.getMinecraft().thePlayer.posX;
        final double var5 = target.posZ - Minecraft.getMinecraft().thePlayer.posZ;
        double var7;
        if (target instanceof EntityLivingBase) {
            final EntityLivingBase var6 = (EntityLivingBase) target;
            var7 = var6.posY + var6.getEyeHeight() - (Minecraft.getMinecraft().thePlayer.posY + Minecraft.getMinecraft().thePlayer.getEyeHeight());
        } else {
            var7 = (target.getEntityBoundingBox().minY + target.getEntityBoundingBox().maxY) / 2.0
                    - (Minecraft.getMinecraft().thePlayer.posY + Minecraft.getMinecraft().thePlayer.getEyeHeight());
        }
        final Random rnd = new Random();
        final double var8 = MathHelper.sqrt_double(var4 * var4 + var5 * var5);
        final float var9 = (float) (Math.atan2(var5, var4) * 180.0 / Math.PI) - 90.0f;
        final float var10 = (float) (-(Math.atan2(var7 - ((target instanceof EntityPlayer) ? 0.25 : 0.0), var8) * 180.0
                / Math.PI));
        final float pitch = changeRotation(Minecraft.getMinecraft().thePlayer.rotationPitch, var10, p_706253);
        final float yaw = changeRotation(Minecraft.getMinecraft().thePlayer.rotationYaw, var9, p_706252);
        return new float[]{yaw, pitch};
    }

    public static float changeRotation(float p_706631, float p_706632, float p_706633) {
        float var4 = MathHelper.wrapAngleTo180_float(p_706632 - p_706631);
        if (var4 > p_706633) {
            var4 = p_706633;
        }
        if (var4 < -p_706633) {
            var4 = -p_706633;
        }
        return p_706631 + var4;
    }

    public static double[] getRotationToEntity(Entity entity) {
        double pX = Helper.mc.thePlayer.posX;
        double pY = Helper.mc.thePlayer.posY + (double) Helper.mc.thePlayer.getEyeHeight();
        double pZ = Helper.mc.thePlayer.posZ;
        double eX = entity.posX;
        double eY = entity.posY + (double) (entity.height / 2.0f);
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
        double diffX = entity.posX - Helper.mc.thePlayer.posX;
        double diffZ = entity.posZ - Helper.mc.thePlayer.posZ;
        if (entity instanceof EntityLivingBase) {
            EntityLivingBase elb = (EntityLivingBase) entity;
            diffY = elb.posY + ((double) elb.getEyeHeight() - 0.4)
                    - (Helper.mc.thePlayer.posY + (double) Helper.mc.thePlayer.getEyeHeight());
        } else {
            diffY = (entity.boundingBox.minY + entity.boundingBox.maxY) / 2.0
                    - (Helper.mc.thePlayer.posY + (double) Helper.mc.thePlayer.getEyeHeight());
        }
        double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
        float yaw = (float) (Math.atan2(diffZ, diffX) * 180.0 / Math.PI) - 90.0f;
        float pitch = (float) (-Math.atan2(diffY, dist) * 180.0 / Math.PI);
        return new float[]{yaw, pitch};
    }

    public static float getDistanceBetweenAngles(float angle1, float angle2) {
        float angle3 = Math.abs(angle1 - angle2) % 360.0f;
        if (angle3 > 180.0f) {
            angle3 = 0.0f;
        }
        return angle3;
    }

    public static int wrapAngleToDirection(float yaw, int zones) {
        int angle = (int) ((double) (yaw + (float) (360 / (2 * zones))) + 0.5) % 360;
        if (angle < 0) {
            angle += 360;
        }
        return angle / (360 / zones);
    }

    public static float getYawChange(float yaw, double posX, double posZ) {
        double deltaX = posX - Minecraft.getMinecraft().thePlayer.posX;
        double deltaZ = posZ - Minecraft.getMinecraft().thePlayer.posZ;
        double yawToEntity = 0;
        if ((deltaZ < 0.0D) && (deltaX < 0.0D)) {
            if (deltaX != 0)
                yawToEntity = 90.0D + Math.toDegrees(Math.atan(deltaZ / deltaX));
        } else if ((deltaZ < 0.0D) && (deltaX > 0.0D)) {
            if (deltaX != 0)
                yawToEntity = -90.0D + Math.toDegrees(Math.atan(deltaZ / deltaX));
        } else {
            if (deltaZ != 0)
                yawToEntity = Math.toDegrees(-Math.atan(deltaX / deltaZ));
        }

        return MathHelper.wrapAngleTo180_float(-(yaw - (float) yawToEntity));
    }

    public static float[] doScaffoldRotations(Vec3d vec) {
        double diffX = vec.xCoord - Minecraft.getMinecraft().thePlayer.posX;
        double diffY = vec.yCoord - (Minecraft.getMinecraft().thePlayer.boundingBox.minY);
        double diffZ = vec.zCoord - Minecraft.getMinecraft().thePlayer.posZ;
        double dist = Math.sqrt(diffX * diffX + diffZ * diffZ);
        float yaw = (float) (Math.toDegrees(Math.atan2(diffZ, diffX)));
        float pitch = (float) -Math.toDegrees(Math.atan2(diffY, dist));
        return new float[]{
        		Minecraft.getMinecraft().thePlayer.rotationYaw
                        + MathHelper.wrapAngleTo180_float(yaw - Minecraft.getMinecraft().thePlayer.rotationYaw),
                        Minecraft.getMinecraft().thePlayer.rotationPitch
                        + MathHelper.wrapAngleTo180_float(pitch - Minecraft.getMinecraft().thePlayer.rotationPitch)};
    }

    public static float[] getRotationsToEntity(Entity entity) {
        final EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
        double xDist = entity.posX - player.posX;
        double zDist = entity.posZ - player.posZ;

        double entEyeHeight = entity.getEyeHeight();
        double yDist = ((entity.posY + entEyeHeight) - Math.min(Math.max(entity.posY - player.posY, 0), entEyeHeight)) -
                (player.posY + player.getEyeHeight());
        double fDist = MathHelper.sqrt_double(xDist * xDist + zDist * zDist);
        float rotationYaw = Minecraft.getMinecraft().thePlayer.rotationYaw;
        float var1 = (float) (Math.atan2(zDist, xDist) * 180.0D / Math.PI) - 90.0F;

        float yaw = rotationYaw + MathHelper.wrapAngleTo180_float(var1 - rotationYaw);
        float rotationPitch = Minecraft.getMinecraft().thePlayer.rotationPitch;

        float var2 = (float) (-(Math.atan2(yDist, fDist) * 180.0D / Math.PI));
        float pitch = rotationPitch + MathHelper.wrapAngleTo180_float(var2 - rotationPitch);

        return new float[]{yaw, MathHelper.clamp_float(pitch, -90.0f, 90.0f)};
    }

    public static float getAngleChange(EntityLivingBase entityIn) {
        float yaw = getRotationsToEntity(entityIn)[0];
        float pitch = getRotationsToEntity(entityIn)[1];
        float playerYaw = Minecraft.getMinecraft().thePlayer.rotationYaw;
        float playerPitch = Minecraft.getMinecraft().thePlayer.rotationPitch;
        if (playerYaw < 0)
            playerYaw += 360;
        if (playerPitch < 0)
            playerPitch += 360;
        if (yaw < 0)
            yaw += 360;
        if (pitch < 0)
            pitch += 360;
        float yawChange = Math.max(playerYaw, yaw) - Math.min(playerYaw, yaw);
        float pitchChange = Math.max(playerPitch, pitch) - Math.min(playerPitch, pitch);
        return yawChange + pitchChange;
    }

    public static float getDistanceToEntity(EntityLivingBase entityLivingBase) {
        return Minecraft.getMinecraft().thePlayer.getDistanceToEntity(entityLivingBase);
    }

}
