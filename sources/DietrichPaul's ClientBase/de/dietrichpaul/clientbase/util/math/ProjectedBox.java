package de.dietrichpaul.clientbase.util.math;

import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class ProjectedBox {

    private float leftYaw, rightYaw, deltaYaw;
    private float bottomPitch, upPitch, deltaPitch;

    public ProjectedBox(Vec3d camera, Box box) {
        Vec3d[] corners = new Vec3d[8];
        corners[0] = new Vec3d(box.minX, box.minY, box.minZ);
        corners[1] = new Vec3d(box.maxX, box.minY, box.minZ);
        corners[2] = new Vec3d(box.minX, box.minY, box.maxZ);
        corners[3] = new Vec3d(box.maxX, box.minY, box.maxZ);
        corners[4] = new Vec3d(box.minX, box.maxY, box.minZ);
        corners[5] = new Vec3d(box.maxX, box.maxY, box.minZ);
        corners[6] = new Vec3d(box.minX, box.maxY, box.maxZ);
        corners[7] = new Vec3d(box.maxX, box.maxY, box.maxZ);

        float smallestPitch = Float.MAX_VALUE;
        float biggestPitch = Float.MIN_VALUE;
        float smallestYaw = 0;
        float biggestYaw = 0;
        float biggestDeltaYaw = Float.MIN_VALUE;

        for (Vec3d cornerA : corners) {
            float[] rotationA = new float[2];
            MathUtil.getRotations(camera, cornerA, rotationA);

            if (rotationA[1] < smallestPitch) {
                smallestPitch = rotationA[1];
            }
            if (rotationA[1] > biggestPitch) {
                biggestPitch = rotationA[1];
            }

            for (Vec3d cornerB : corners) {
                float[] rotationB = new float[2];
                MathUtil.getRotations(camera, cornerB, rotationB);
                float yawDiff = MathHelper.angleBetween(rotationB[0], rotationA[0]);
                if (yawDiff > biggestDeltaYaw) {
                    smallestYaw = rotationA[0];
                    biggestYaw = rotationB[0];
                    biggestDeltaYaw = yawDiff;
                }
            }
        }

        if (MathHelper.subtractAngles(smallestYaw, biggestYaw) < 0) {
            float temp = smallestYaw;
            smallestYaw = biggestYaw;
            biggestYaw = temp;
        }

        leftYaw = smallestYaw;
        rightYaw = biggestYaw;
        deltaYaw = biggestDeltaYaw;
        bottomPitch = biggestPitch;
        upPitch = smallestPitch;
        deltaPitch = MathHelper.angleBetween(bottomPitch, upPitch);
    }

    public float getDeltaPitch() {
        return deltaPitch;
    }

    public float getDeltaYaw() {
        return deltaYaw;
    }

    public float getYaw(float x) {
        return MathHelper.lerpAngleDegrees(x, leftYaw, rightYaw);
    }

    public float getPitch(float y) {
        return MathHelper.lerpAngleDegrees(y, bottomPitch, upPitch);
    }

    public float getX(float yaw) {
        return MathHelper.subtractAngles(leftYaw, yaw) / deltaYaw;
    }

    public float getY(float pitch) {
        return MathHelper.subtractAngles(pitch, bottomPitch) / deltaPitch;
    }

}