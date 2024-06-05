package gq.vapu.czfclient.Util;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

import java.util.Random;

public class CombatUtil {
    private static final Minecraft mc = Minecraft.getMinecraft();

    public static float[] getRotations(final Entity entity) {
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
        return new float[]{(float) yaw, (float) (90.0 - pitch)};
    }

    public static float[] getRotationsnull(Entity entity) {
        if (entity == null)
            return null;
        double diffX = entity.posX - mc.thePlayer.posX;
        double diffZ = entity.posZ - mc.thePlayer.posZ;
        double diffY;
        if (entity instanceof EntityLivingBase) {
            EntityLivingBase elb = (EntityLivingBase) entity;
            diffY = elb.posY
                    + (elb.getEyeHeight() - 0.4)
                    - (mc.thePlayer.posY + mc.thePlayer
                    .getEyeHeight());
        } else
            diffY = (entity.boundingBox.minY + entity.boundingBox.maxY)
                    / 2.0D
                    - (mc.thePlayer.posY + mc.thePlayer
                    .getEyeHeight());
        double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
        float yaw = (float) (Math.atan2(diffZ, diffX) * 180.0D / 3.141592653589793D) - 90.0F;
        float pitch = (float) -(Math.atan2(diffY, dist) * 180.0D / 3.141592653589793D);

        return new float[]{yaw, pitch};
    }

    public static float[] getRotationFromPosition(final double x, final double y, final double z) {
        final double xDiff = x - Minecraft.getMinecraft().thePlayer.posX;
        final double zDiff = z - Minecraft.getMinecraft().thePlayer.posZ;
        final double yDiff = y - Minecraft.getMinecraft().thePlayer.posY - Minecraft.getMinecraft().thePlayer.getEyeHeight();
        final double dist = MathHelper.sqrt_double(xDiff * xDiff + zDiff * zDiff);
        final float yaw = (float) (Math.atan2(zDiff, xDiff) * 180.0 / 3.141592653589793) - 90.0f;
        final float pitch = (float) (-(Math.atan2(yDiff, dist) * 180.0 / 3.141592653589793));
        return new float[]{yaw, pitch};
    }

    public static float[] getRotationsNeededBlock(final double x, final double y, final double z) {
        final double diffX = x + 0.5 - Minecraft.getMinecraft().thePlayer.posX;
        final double diffZ = z + 0.5 - Minecraft.getMinecraft().thePlayer.posZ;
        final double diffY = y + 0.5 - (Minecraft.getMinecraft().thePlayer.posY + Minecraft.getMinecraft().thePlayer.getEyeHeight());
        final double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
        final float yaw = (float) (Math.atan2(diffZ, diffX) * 180.0 / 3.141592653589793) - 90.0f;
        final float pitch = (float) (-(Math.atan2(diffY, dist) * 180.0 / 3.141592653589793));
        return new float[]{Minecraft.getMinecraft().thePlayer.rotationYaw + MathHelper.wrapAngleTo180_float(yaw - Minecraft.getMinecraft().thePlayer.rotationYaw), Minecraft.getMinecraft().thePlayer.rotationPitch + MathHelper.wrapAngleTo180_float(pitch - Minecraft.getMinecraft().thePlayer.rotationPitch)};
    }

    public static float[] getRotationsNeededBlock(final double x, final double y, final double z, final double x1, final double y1, final double z1) {
        final double diffX = x1 + 0.5 - x;
        final double diffZ = z1 + 0.5 - z;
        final double diffY = y1 + 0.5 - (y + Minecraft.getMinecraft().thePlayer.getEyeHeight());
        final double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
        final float yaw = (float) (Math.atan2(diffZ, diffX) * 180.0 / 3.141592653589793) - 90.0f;
        final float pitch = (float) (-(Math.atan2(diffY, dist) * 180.0 / 3.141592653589793));
        return new float[]{yaw, pitch};
    }

    public static float getTrajAngleSolutionLow(final float d3, final float d1, final float velocity) {
        final float g = 0.006f;
        final float sqrt = velocity * velocity * velocity * velocity - g * (g * (d3 * d3) + 2.0f * d1 * (velocity * velocity));
        return (float) Math.toDegrees(Math.atan((velocity * velocity - Math.sqrt(sqrt)) / (g * d3)));
    }

    public static float getNewAngle(float angle) {
        angle %= 360.0f;
        if (angle >= 180.0f) {
            angle -= 360.0f;
        }
        if (angle < -180.0f) {
            angle += 360.0f;
        }
        return angle;
    }

    public static float getDistanceBetweenAngles(final float angle1, final float angle2) {
        float angle3 = Math.abs(angle1 - angle2) % 360.0f;
        if (angle3 > 180.0f) {
            angle3 = 360.0f - angle3;
        }
        return angle3;
    }

    public static Vec3[] getCorners(final AxisAlignedBB box) {
        return new Vec3[]{new Vec3(box.minX, box.minY, box.minZ), new Vec3(box.maxX, box.minY, box.minZ), new Vec3(box.minX, box.maxY, box.minZ), new Vec3(box.minX, box.minY, box.maxZ), new Vec3(box.maxX, box.maxY, box.minZ), new Vec3(box.minX, box.maxY, box.maxZ), new Vec3(box.maxX, box.minY, box.maxZ), new Vec3(box.maxX, box.maxY, box.maxZ)};
    }

    public static AxisAlignedBB getCloserBox(final AxisAlignedBB b1, final AxisAlignedBB b2) {
        Vec3[] corners;
        for (int length = (corners = getCorners(b2)).length, i = 0; i < length; ++i) {
            final Vec3 pos = corners[i];
            if (isRotationIn(getRotationFromPosition(pos.xCoord, pos.yCoord, pos.zCoord), b1)) {
                return (getDistanceToBox(b2) < getDistanceToBox(b1)) ? b2 : b1;
            }
        }
        return b2;
    }

    public static double getDistanceToBox(final AxisAlignedBB box) {
        return Minecraft.getMinecraft().thePlayer.getDistance((box.minX + box.maxX) / 2.0, (box.minY + box.maxY) / 2.0, (box.minZ + box.maxZ) / 2.0);
    }

    public static boolean isRotationIn(final float[] rotation, final AxisAlignedBB box) {
        final float[] maxRotations = getMaxRotations(box);
        return maxRotations[0] < rotation[0] && maxRotations[2] < rotation[1] && maxRotations[1] > rotation[0] && maxRotations[3] > rotation[1];
    }

    public static float[] getRandomRotationsInBox(final AxisAlignedBB box) {
        final float[] maxRotations = getMaxRotations(box);
        final float yaw = MathHelper.getRandomIntegerInRange(new Random(), maxRotations[0], maxRotations[1]);
        final float pitch = MathHelper.getRandomIntegerInRange(new Random(), maxRotations[2], maxRotations[3]);
        return new float[]{yaw, pitch};
    }

    public static float[] getMaxRotations(final AxisAlignedBB box) {
        float minYaw = 2.14748365E9f;
        float maxYaw = -2.14748365E9f;
        float minPitch = 2.14748365E9f;
        float maxPitch = -2.14748365E9f;
        Vec3[] corners;
        for (int length = (corners = getCorners(box)).length, i = 0; i < length; ++i) {
            final Vec3 pos = corners[i];
            final float[] rot = getRotationFromPosition(pos.xCoord, pos.yCoord, pos.zCoord);
            if (rot[0] < minYaw) {
                minYaw = rot[0];
            }
            if (rot[0] > maxYaw) {
                maxYaw = rot[0];
            }
            if (rot[1] < minPitch) {
                minPitch = rot[1];
            }
            if (rot[1] > maxPitch) {
                maxPitch = rot[1];
            }
        }
        return new float[]{minYaw, maxYaw, minPitch, maxPitch};
    }

    public static AxisAlignedBB expandBox(final AxisAlignedBB box, double multiplier) {
        multiplier = 1.0 - multiplier / 100.0;
        return box.expand((box.maxX - box.minX) * multiplier, 0.12, (box.maxZ - box.minZ) * multiplier);
    }

    public static AxisAlignedBB contractBox(final AxisAlignedBB box, double multiplier) {
        multiplier = 1.0 - multiplier / 100.0;
        return box.contract((box.maxX - box.minX) * multiplier, 0.12, (box.maxZ - box.minZ) * multiplier);
    }

    public static float getYawDifference(final float current, final float target) {
        final float rot = (target + 180.0f - current) % 360.0f;
        return rot + ((rot > 0.0f) ? -180.0f : 180.0f);
    }

    public static float getPitchDifference(final float current, final float target) {
        final float rot = (target + 90.0f - current) % 180.0f;
        return rot + ((rot > 0.0f) ? -90.0f : 90.0f);
    }
}
