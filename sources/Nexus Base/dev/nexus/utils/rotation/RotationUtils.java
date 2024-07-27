package dev.nexus.utils.rotation;

import dev.nexus.Nexus;
import dev.nexus.utils.Utils;
import dev.nexus.utils.client.MathUtils;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class RotationUtils implements Utils {

    public static float[] getRotations(Entity entity, float minYawRandom, float maxYawRandom, float minPitchRandom, float maxPitchRandom, float minTurnSpeed, float maxTurnSpeed) {
        float[] last = new float[]{Nexus.INSTANCE.getRotationManager().yaw, Nexus.INSTANCE.getRotationManager().pitch};
        Vec3 eye = mc.thePlayer.getPositionEyes(1.0f);

        AxisAlignedBB entityBoundingBox = entity.getEntityBoundingBox().expand(
                entity.getCollisionBorderSize(),
                entity.getCollisionBorderSize(),
                entity.getCollisionBorderSize()
        );

        double vectorY = MathHelper.clamp_double(eye.yCoord, entityBoundingBox.minY, entityBoundingBox.maxY);

        Vec3 vector = new Vec3(entity.posX, vectorY, entity.posZ);

        double x = vector.xCoord - mc.thePlayer.posX,
                y = vector.yCoord - mc.thePlayer.posY - mc.thePlayer.getEyeHeight(),
                z = vector.zCoord - mc.thePlayer.posZ;

        double theta = MathHelper.sqrt_double(x * x + z * z);

        float yaw = (float) (Math.atan2(z, x) * 180.0 / Math.PI - 90.0),
                pitch = (float) (-(Math.atan2(y, theta) * 180.0 / Math.PI));

        if (mc.thePlayer.ticksExisted % 2 == 1) {
            yaw += MathUtils.getRandomInRange(
                    -MathUtils.getRandomInRange(minYawRandom, maxYawRandom),
                    MathUtils.getRandomInRange(minYawRandom, maxYawRandom)
            );
            pitch += MathUtils.getRandomInRange(
                    -MathUtils.getRandomInRange(minPitchRandom, maxPitchRandom),
                    MathUtils.getRandomInRange(minPitchRandom, maxPitchRandom)
            );
        }

        yaw = smoothRotation(
                last[0], yaw,
                MathUtils.getRandomInRange(
                        minTurnSpeed,
                        maxTurnSpeed
                )
        );

        pitch = smoothRotation(
                last[1], pitch,
                MathUtils.getRandomInRange(
                        minTurnSpeed,
                        maxTurnSpeed
                )
        );

        return new float[]{yaw,pitch};
    }

    public static float clamp(final float n) {
        return MathHelper.clamp_float(n, -90.0f, 90.0f);
    }

    public static float smoothRotation(float from, float to, float speed) {
        float f = MathHelper.wrapAngleTo180_float(to - from);

        if (f > speed) {
            f = speed;
        }

        if (f < -speed) {
            f = -speed;
        }

        return from + f;
    }

    public static float[] getCappedRotations(float[] prev, float[] current, float speed) {
        float yawDiff = RotationUtils.getYawDifference(current[0], prev[0]);
        if (Math.abs(yawDiff) > speed)
            yawDiff = (speed * (yawDiff > 0 ? 1 : -1)) / 2f;
        float cappedPYaw = MathHelper.wrapAngleTo180_float(prev[0] + yawDiff);

        float pitchDiff = RotationUtils.getYawDifference(current[1], prev[1]);
        if (Math.abs(pitchDiff) > speed / 2f)
            pitchDiff = (speed / 2f * (pitchDiff > 0 ? 1 : -1)) / 2f;
        float cappedPitch = MathHelper.wrapAngleTo180_float(prev[1] + pitchDiff);
        return new float[]{cappedPYaw, cappedPitch};
    }

    public static float[] getPatchedAndCappedRots(float[] prev, float[] current, float speed) {
        return patchGCD(prev, getCappedRotations(prev, current, speed));
    }

    public static float getYawDifference(float yaw1, float yaw2) {
        float yawDiff = MathHelper.wrapAngleTo180_float(yaw1) - MathHelper.wrapAngleTo180_float(yaw2);
        if (Math.abs(yawDiff) > 180)
            yawDiff = yawDiff + 360;
        return MathHelper.wrapAngleTo180_float(yawDiff);
    }

    public static float getPitchDifference(float pitch1, float pitch2) {
        return (pitch1 - pitch2);
    }

    public static float[] patchGCD(float[] prevRotation, float[] currentRotation) {
        float f = mc.gameSettings.mouseSensitivity * 0.6F + 0.2F;
        float gcd = f * f * f * 8.0F * 0.15F;
        final float deltaYaw = currentRotation[0] - prevRotation[0],
                deltaPitch = currentRotation[1] - prevRotation[1];
        final float yaw = prevRotation[0] + Math.round(deltaYaw / gcd) * gcd,
                pitch = prevRotation[1] + Math.round(deltaPitch / gcd) * gcd;

        return new float[]{yaw, pitch};
    }
}
