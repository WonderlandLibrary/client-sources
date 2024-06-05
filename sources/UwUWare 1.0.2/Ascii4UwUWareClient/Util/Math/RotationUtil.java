package Ascii4UwUWareClient.Util.Math;

import Ascii4UwUWareClient.Util.Helper;

import java.util.Random;

import Ascii4UwUWareClient.Util.Wrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class RotationUtil {
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
        final double var4 = target.posX - Minecraft.thePlayer.posX;
        final double var5 = target.posZ - Minecraft.thePlayer.posZ;
        double var7;
        if (target instanceof EntityLivingBase) {
            final EntityLivingBase var6 = (EntityLivingBase) target;
            var7 = var6.posY + var6.getEyeHeight() - (Minecraft.thePlayer.posY + Minecraft.thePlayer.getEyeHeight());
        } else {
            var7 = (target.getEntityBoundingBox().minY + target.getEntityBoundingBox().maxY) / 2.0
                    - (Minecraft.thePlayer.posY + Minecraft.thePlayer.getEyeHeight());
        }
        final Random rnd = new Random();
        final double var8 = MathHelper.sqrt_double(var4 * var4 + var5 * var5);
        final float var9 = (float) (Math.atan2(var5, var4) * 180.0 / Math.PI) - 90.0f;
        final float var10 = (float) (-(Math.atan2(var7 - ((target instanceof EntityPlayer) ? 0.25 : 0.0), var8) * 180.0
                / Math.PI));
        final float pitch = changeRotation(Minecraft.thePlayer.rotationPitch, var10, p_706253);
        final float yaw = changeRotation(Minecraft.thePlayer.rotationYaw, var9, p_706252);
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

    public static float[] grabBlockRotations(BlockPos pos) {
        return RotationUtil.getVecRotation(
                Helper.mc.thePlayer.getPositionVector().addVector(0.0, Helper.mc.thePlayer.getEyeHeight(), 0.0),
                new Vec3((double) pos.getX() + 0.5, (double) pos.getY() + 0.5, (double) pos.getZ() + 0.5));
    }

    public static float[] getVecRotation(Vec3 position) {
        return RotationUtil.getVecRotation(
                Helper.mc.thePlayer.getPositionVector().addVector(0.0, Helper.mc.thePlayer.getEyeHeight(), 0.0),
                position);
    }

    public static float[] getVecRotation(Vec3 origin, Vec3 position) {
        Vec3 difference = position.subtract(origin);
        double distance = difference.flat().lengthVector();
        float yaw = (float) Math.toDegrees(Math.atan2(difference.zCoord, difference.xCoord)) - 90.0f;
        float pitch = (float) (-Math.toDegrees(Math.atan2(difference.yCoord, distance)));
        return new float[]{yaw, pitch};
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
        double diffX = vec.xCoord - Minecraft.thePlayer.posX;
        double diffY = vec.yCoord - (Minecraft.thePlayer.boundingBox.minY);
        double diffZ = vec.zCoord - Minecraft.thePlayer.posZ;
        double dist = MathHelper.sqrt(diffX * diffX + diffZ * diffZ);
        float yaw = (float) (Math.toDegrees(Math.atan2(diffZ, diffX)));
        float pitch = (float) -Math.toDegrees(Math.atan2(diffY, dist));
        return new float[]{
                Minecraft.thePlayer.rotationYaw
                        + MathHelper.wrapAngleTo180_float(yaw - Minecraft.thePlayer.rotationYaw),
                Minecraft.thePlayer.rotationPitch
                        + MathHelper.wrapAngleTo180_float(pitch - Minecraft.thePlayer.rotationPitch)};
    }

    public static float[] getRotationsToEntity(Entity entity) {
        final EntityPlayerSP player = Wrapper.getPlayer();
        double xDist = entity.posX - player.posX;
        double zDist = entity.posZ - player.posZ;

        double entEyeHeight = entity.getEyeHeight();
        double yDist = ((entity.posY + entEyeHeight) - Math.min(Math.max(entity.posY - player.posY, 0), entEyeHeight)) -
                (player.posY + player.getEyeHeight());
        double fDist = MathHelper.sqrt_double(xDist * xDist + zDist * zDist);
        float rotationYaw = Wrapper.getPlayer().rotationYaw;
        float var1 = (float) (Math.atan2(zDist, xDist) * 180.0D / Math.PI) - 90.0F;

        float yaw = rotationYaw + MathHelper.wrapAngleTo180_float(var1 - rotationYaw);
        float rotationPitch = Wrapper.getPlayer().rotationPitch;

        float var2 = (float) (-(Math.atan2(yDist, fDist) * 180.0D / Math.PI));
        float pitch = rotationPitch + MathHelper.wrapAngleTo180_float(var2 - rotationPitch);

        return new float[]{yaw, MathHelper.clamp_float(pitch, -90.0f, 90.0f)};
    }

    public static float getAngleChange(EntityLivingBase entityIn) {
        float yaw = getRotationsToEntity(entityIn)[0];
        float pitch = getRotationsToEntity(entityIn)[1];
        float playerYaw = Minecraft.thePlayer.rotationYaw;
        float playerPitch = Minecraft.thePlayer.rotationPitch;
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
        return Minecraft.thePlayer.getDistanceToEntity(entityLivingBase);
    }

}
