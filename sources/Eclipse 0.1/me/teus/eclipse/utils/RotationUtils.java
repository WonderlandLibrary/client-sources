package me.teus.eclipse.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

import java.util.concurrent.ThreadLocalRandom;


public class RotationUtils implements Utils{

    public static float[] getNeededRotations(Vec3 vec) {
        Vec3 playerVector = new Vec3(Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.posY + Minecraft.getMinecraft().thePlayer.getEyeHeight(), Minecraft.getMinecraft().thePlayer.posZ);
        double y = vec.yCoord - playerVector.yCoord;
        double x = vec.xCoord - playerVector.xCoord;
        double z = vec.zCoord - playerVector.zCoord;
        double dff = Math.sqrt(x * x + z * z);
        float yaw = (float) Math.toDegrees(Math.atan2(z, x)) - 90;
        float pitch = (float) -Math.toDegrees(Math.atan2(y, dff));
        return new float[]{updateRotation(yaw, yaw, 180), updateRotation(pitch, pitch, 90)};
    }

    public static Vec3 getClosestPoint(final Vec3 start, final AxisAlignedBB boundingBox) {
        final double closestX = start.xCoord >= boundingBox.maxX ? boundingBox.maxX : boundingBox.minX,
                closestY = start.yCoord >= boundingBox.maxY ? boundingBox.maxY :
                        start.yCoord <= boundingBox.minY ? boundingBox.minY : boundingBox.minY + (start.yCoord - boundingBox.minY),
                closestZ = start.zCoord >= boundingBox.maxZ ? boundingBox.maxZ : boundingBox.minZ;
        return new Vec3(closestX, closestY, closestZ);
    }

    public static Vec3 getRandomCenter(AxisAlignedBB bb) {
        return new Vec3(bb.minX + (bb.maxX - bb.minX) * ThreadLocalRandom.current().nextDouble(0.2, 0.8),
                bb.minY + (bb.maxY - bb.minY) * ThreadLocalRandom.current().nextDouble(0.2, 0.8),
                bb.minZ + (bb.maxZ - bb.minZ) * ThreadLocalRandom.current().nextDouble(0.2, 0.8));
    }

    public static Vec3 getCenter(AxisAlignedBB bb) {
        return new Vec3(bb.minX + (bb.maxX - bb.minX) * 0.5, bb.minY + (bb.maxY - bb.minY) * 0.5, bb.minZ + (bb.maxZ - bb.minZ) * 0.5);
    }

    public static float updateRotation(float p_75652_1_, float p_75652_2_, float p_75652_3_) {
        float f = MathHelper.wrapAngleTo180_float(p_75652_2_ - p_75652_1_);

        if (f > p_75652_3_)
        {
            f = p_75652_3_;
        }

        if (f < -p_75652_3_)
        {
            f = -p_75652_3_;
        }

        return p_75652_1_ + f;
    }

}
