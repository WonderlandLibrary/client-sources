/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.utils;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;

public class RotationUtils {
    public static float[] getRotations(Entity target) {
        double x = target.posX;
        double z = target.posZ;
        double y = target.posY + (double)(target.getEyeHeight() / 2.0f) - 0.5;
        return RotationUtils.getRotationFromPosition(x, z, y);
    }

    public static float[] getAverageRotations(List<EntityLivingBase> targetList) {
        double posX = 0.0;
        double posY = 0.0;
        double posZ = 0.0;
        for (Entity ent : targetList) {
            posX += ent.posX;
            posY += ent.boundingBox.maxY - 2.0;
            posZ += ent.posZ;
        }
        return new float[]{RotationUtils.getRotationFromPosition(posX /= (double)targetList.size(), posZ /= (double)targetList.size(), posY /= (double)targetList.size())[0], RotationUtils.getRotationFromPosition(posX, posZ, posY)[1]};
    }

    public static float[] getRotationFromPosition(double x, double z, double y) {
        Minecraft.getMinecraft();
        double xDiff = x - Minecraft.thePlayer.posX;
        Minecraft.getMinecraft();
        double zDiff = z - Minecraft.thePlayer.posZ;
        Minecraft.getMinecraft();
        double yDiff = y - Minecraft.thePlayer.posY - 0.6;
        double dist = MathHelper.sqrt_double(xDiff * xDiff + zDiff * zDiff);
        float yaw = (float)(Math.atan2(zDiff, xDiff) * 180.0 / 3.141592653589793) - 90.0f;
        float pitch = (float)(-(Math.atan2(yDiff, dist) * 180.0 / 3.141592653589793));
        return new float[]{yaw, pitch};
    }

    public static float getTrajAngleSolutionLow(float d3, float d1, float velocity) {
        float g = 0.006f;
        float sqrt = velocity * velocity * velocity * velocity - 0.006f * (0.006f * (d3 * d3) + 2.0f * d1 * (velocity * velocity));
        return (float)Math.toDegrees(Math.atan(((double)(velocity * velocity) - Math.sqrt(sqrt)) / (double)(0.006f * d3)));
    }

    public static float getNewAngle(float angle) {
        if ((angle %= 360.0f) >= 180.0f) {
            angle -= 360.0f;
        }
        if (angle < -180.0f) {
            angle += 360.0f;
        }
        return angle;
    }

    public static float getDistanceBetweenAngles(float angle1, float angle2) {
        float angle3 = Math.abs(angle1 - angle2) % 360.0f;
        if (angle3 > 180.0f) {
            angle3 = 360.0f - angle3;
        }
        return angle3;
    }
}

