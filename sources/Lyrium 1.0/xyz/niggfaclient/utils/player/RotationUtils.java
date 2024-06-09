// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.utils.player;

import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraft.entity.Entity;
import xyz.niggfaclient.utils.Utils;

public class RotationUtils extends Utils
{
    public static float getYawToEntity(final Entity entity, final boolean useOldPos) {
        final double xDist = (useOldPos ? entity.prevPosX : entity.posX) - (useOldPos ? RotationUtils.mc.thePlayer.prevPosX : RotationUtils.mc.thePlayer.posX);
        final double zDist = (useOldPos ? entity.prevPosZ : entity.posZ) - (useOldPos ? RotationUtils.mc.thePlayer.prevPosZ : RotationUtils.mc.thePlayer.posZ);
        final float rotationYaw = useOldPos ? RotationUtils.mc.thePlayer.prevRotationYaw : RotationUtils.mc.thePlayer.rotationYaw;
        final float var1 = (float)(Math.atan2(zDist, xDist) * 180.0 / 3.141592653589793) - 90.0f;
        return rotationYaw + MathHelper.wrapAngleTo180_float(var1 - rotationYaw);
    }
    
    public static float getYawToEntity(final Entity entity) {
        return getYawBetween(RotationUtils.mc.thePlayer.rotationYaw, RotationUtils.mc.thePlayer.posX, RotationUtils.mc.thePlayer.posZ, entity.posX, entity.posZ);
    }
    
    public static float getYawBetween(final float yaw, final double srcX, final double srcZ, final double destX, final double destZ) {
        final double xDist = destX - srcX;
        final double zDist = destZ - srcZ;
        final float var1 = (float)(StrictMath.atan2(zDist, xDist) * 180.0 / 3.141592653589793) - 90.0f;
        return yaw + MathHelper.wrapAngleTo180_float(var1 - yaw);
    }
    
    public static float[] getAngles(final EntityLivingBase entity) {
        if (entity == null) {
            return null;
        }
        final double diffX = entity.posX - RotationUtils.mc.thePlayer.posX;
        final double diffY = entity.posY + entity.getEyeHeight() * 0.9 - (RotationUtils.mc.thePlayer.posY + RotationUtils.mc.thePlayer.getEyeHeight());
        final double diffZ = entity.posZ - RotationUtils.mc.thePlayer.posZ;
        final double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
        final float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0 / 3.141592653589793) - 90.0f;
        final float pitch = (float)(-(Math.atan2(diffY, dist) * 180.0 / 3.141592653589793));
        return new float[] { RotationUtils.mc.thePlayer.rotationYaw + MathHelper.wrapAngleTo180_float(yaw - RotationUtils.mc.thePlayer.rotationYaw), RotationUtils.mc.thePlayer.rotationPitch + MathHelper.wrapAngleTo180_float(pitch - RotationUtils.mc.thePlayer.rotationPitch) };
    }
    
    public static float[] getRotsByPos(final double x, final double y, final double z) {
        final double deltaX = x - RotationUtils.mc.thePlayer.posX;
        final double deltaY = y - RotationUtils.mc.thePlayer.posY - RotationUtils.mc.thePlayer.getEyeHeight() + 1.5;
        final double deltaZ = z - RotationUtils.mc.thePlayer.posZ;
        final double dist = Math.sqrt(deltaX * deltaX + deltaZ * deltaZ);
        final float yaw = (float)(Math.atan2(deltaZ, deltaX) * 180.0 / 3.141592653589793) - 90.0f;
        final float pitch = (float)(-(Math.atan2(deltaY, dist) * 180.0 / 3.141592653589793));
        return new float[] { RotationUtils.mc.thePlayer.rotationYaw + MathHelper.wrapAngleTo180_float(yaw - RotationUtils.mc.thePlayer.rotationYaw), RotationUtils.mc.thePlayer.rotationPitch + MathHelper.wrapAngleTo180_float(pitch - RotationUtils.mc.thePlayer.rotationPitch) };
    }
    
    public static float[] getRotations(final Vec3 start, final Vec3 dst) {
        final double xDif = dst.xCoord - start.xCoord;
        final double yDif = dst.yCoord - start.yCoord;
        final double zDif = dst.zCoord - start.zCoord;
        final double distXZ = Math.sqrt(xDif * xDif + zDif * zDif);
        return new float[] { (float)(Math.atan2(zDif, xDif) * 57.29577951308232) - 90.0f, (float)(-(Math.atan2(yDif, distXZ) * 57.29577951308232)) };
    }
    
    public static Vec3 getCenterPointOnBB(final AxisAlignedBB hitBox, final double progressToTop) {
        final double xWidth = hitBox.maxX - hitBox.minX;
        final double zWidth = hitBox.maxZ - hitBox.minZ;
        final double height = hitBox.maxY - hitBox.minY;
        return new Vec3(hitBox.minX + xWidth / 2.0, hitBox.minY + height * progressToTop, hitBox.minZ + zWidth / 2.0);
    }
    
    public static Vec3 getHitOrigin(final Entity entity) {
        return new Vec3(entity.posX, entity.posY + 1.6200000047683716, entity.posZ);
    }
    
    public static void applySmoothing(final float[] lastRotations, final float smoothing, final float[] dstRotation) {
        if (smoothing > 0.0f) {
            final float yawChange = MathHelper.wrapAngleTo180_float(dstRotation[0] - lastRotations[0]);
            final float pitchChange = MathHelper.wrapAngleTo180_float(dstRotation[1] - lastRotations[1]);
            final float smoothingFactor = Math.max(1.0f, smoothing / 10.0f);
            dstRotation[0] = lastRotations[0] + yawChange / smoothingFactor;
            dstRotation[1] = Math.max(Math.min(90.0f, lastRotations[1] + pitchChange / smoothingFactor), -90.0f);
        }
    }
    
    public static double getMouseGCD() {
        final float sens = RotationUtils.mc.gameSettings.mouseSensitivity * 0.6f + 0.2f;
        final float pow = sens * sens * sens * 8.0f;
        return pow * 0.15;
    }
    
    public static void applyGCD(final float[] rotations, final float[] prevRots) {
        final float yawDif = rotations[0] - prevRots[0];
        final float pitchDif = rotations[1] - prevRots[1];
        final double gcd = getMouseGCD();
        final int n = 0;
        rotations[n] -= (float)(yawDif % gcd);
        final int n2 = 1;
        rotations[n2] -= (float)(pitchDif % gcd);
    }
    
    public static float getAngleDifference(final float a, final float b) {
        return ((a - b) % 360.0f + 540.0f) % 360.0f - 180.0f;
    }
}
