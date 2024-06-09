/*
 * Decompiled with CFR 0.145.
 */
package us.amerikan.utils;

import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class Utils {
    private static final Random RANDOM = new Random();
    private Minecraft mc = Minecraft.getMinecraft();

    public static int random(int min, int max) {
        return RANDOM.nextInt(max - min) + min;
    }

    public static Vec3 getRandomCenter(AxisAlignedBB bb2) {
        return new Vec3(bb2.minX + (bb2.maxX - bb2.minX) * 0.8 * Math.random(), bb2.minY + (bb2.maxY - bb2.minY) * 1.0 * Math.random(), bb2.minZ + (bb2.maxZ - bb2.minZ) * 0.8 * Math.random());
    }

    public static float[] getNeededRotations(Vec3 vec) {
        Minecraft.getMinecraft();
        Minecraft.getMinecraft();
        Minecraft.getMinecraft();
        Minecraft.getMinecraft();
        Vec3 eyesPos = new Vec3(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY + (double)Minecraft.thePlayer.getEyeHeight(), Minecraft.thePlayer.posZ);
        double diffX = vec.xCoord - eyesPos.xCoord;
        double diffY = vec.yCoord - eyesPos.yCoord;
        double diffZ = vec.zCoord - eyesPos.zCoord;
        double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
        float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f;
        float pitch = (float)(-Math.toDegrees(Math.atan2(diffY, diffXZ)));
        return new float[]{MathHelper.wrapAngleTo180_float(yaw), MathHelper.wrapAngleTo180_float(pitch)};
    }

    public static float[] faceEntity(Entity entity, float currentYaw, float yawSpeed, boolean random) {
        double y2;
        double x2 = entity.posX - Minecraft.thePlayer.posX;
        double z2 = entity.posZ - Minecraft.thePlayer.posZ;
        if (entity instanceof EntityLivingBase) {
            EntityLivingBase var14 = (EntityLivingBase)entity;
            y2 = var14.posY + (double)var14.getEyeHeight() - (Minecraft.thePlayer.posY + (double)Minecraft.thePlayer.getEyeHeight());
        } else {
            y2 = (entity.getEntityBoundingBox().minY + entity.getEntityBoundingBox().maxY) / 2.0 - (Minecraft.thePlayer.posY + (double)Minecraft.thePlayer.getEyeHeight());
        }
        double distance = MathHelper.sqrt_double(x2 * x2 + z2 * z2);
        float nextYaw = (float)(Math.atan2(z2, x2) * 180.0 / 3.141592653589793) - 90.0f;
        float pitch = (float)(-(Math.atan2(y2, distance) * 180.0 / 3.141592653589793));
        if (random) {
            double rand = new Random().nextDouble();
            nextYaw = rand >= 0.6 ? (float)((double)nextYaw + (rand + (double)new Random().nextInt(7))) : (float)((double)nextYaw - (rand + (double)new Random().nextInt(7)));
            rand = new Random().nextDouble();
            pitch = rand >= 0.6 ? (float)((double)pitch + rand) : (float)((double)pitch - rand);
        }
        float yaw = Utils.updateRotation(currentYaw, nextYaw, yawSpeed);
        return new float[]{yaw, pitch - 3.0f};
    }

    public static float updateRotation(float currentRotation, float nextRotation, float rotationSpeed) {
        float f2 = MathHelper.wrapAngleTo180_float(nextRotation - currentRotation);
        if (f2 > rotationSpeed) {
            f2 = rotationSpeed;
        }
        if (f2 < -rotationSpeed) {
            f2 = -rotationSpeed;
        }
        return currentRotation + f2;
    }

    public static float getDirection() {
        Minecraft mc2 = Minecraft.getMinecraft();
        float var1 = Minecraft.thePlayer.rotationYaw;
        if (Minecraft.thePlayer.moveForward < 0.0f) {
            var1 += 180.0f;
        }
        float forward = 1.0f;
        if (Minecraft.thePlayer.moveForward < 0.0f) {
            forward = -0.5f;
        } else if (Minecraft.thePlayer.moveForward > 0.0f) {
            forward = 0.5f;
        }
        if (Minecraft.thePlayer.moveStrafing > 0.0f) {
            var1 -= 90.0f * forward;
        }
        if (Minecraft.thePlayer.moveStrafing < 0.0f) {
            var1 += 90.0f * forward;
        }
        return var1 *= 0.017453292f;
    }
}

