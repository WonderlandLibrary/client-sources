package dev.stephen.nexus.utils.rotation;

import dev.stephen.nexus.utils.Utils;
import dev.stephen.nexus.utils.math.MathUtils;
import dev.stephen.nexus.utils.math.RandomUtil;
import dev.stephen.nexus.utils.mc.CombatUtils;
import dev.stephen.nexus.utils.timer.MillisTimer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.*;

public class RotationUtils implements Utils {

    public static final float PI = (float) Math.PI;
    public static final float TO_DEGREES = 180.0F / PI;

    // Block
    public static float[] getRotationToBlock(BlockPos blockPos,Direction direction) {
        PlayerEntity player = mc.player;

        double centerX = blockPos.getX() + 0.5 + direction.getOffsetX() * 0.5;
        double centerY = blockPos.getY() + 0.5 + direction.getOffsetY() * 0.5;
        double centerZ = blockPos.getZ() + 0.5 + direction.getOffsetZ() * 0.5;

        double playerX = player.getX();
        double playerY = player.getY() + player.getEyeHeight(mc.player.getPose());
        double playerZ = player.getZ();

        double deltaX = centerX - playerX;
        double deltaY = centerY - playerY;
        double deltaZ = centerZ - playerZ;

        double distanceXZ = Math.sqrt(deltaX * deltaX + deltaZ * deltaZ);
        float yaw = (float) (Math.toDegrees(Math.atan2(deltaZ, deltaX)) - 90.0F);
        float pitch = (float) -Math.toDegrees(Math.atan2(deltaY, distanceXZ));

        return new float[]{yaw, pitch};
    }

        // Entity
    public static float[] getRotationToEntity(PlayerEntity e) {
        Vec3d to = getTargetVec(e, CombatUtils.getDistanceToEntity(e) + 0.5);
        if (to == null) {
            return null;
        }

        Vec3d from = new Vec3d(mc.player.getX(), mc.player.getY() + mc.player.getEyeHeight(mc.player.getPose()), mc.player.getZ());
        double diffX = to.x - from.x;
        double diffY = to.y - from.y;
        double diffZ = to.z - from.z;
        double dist = Math.sqrt(diffX * diffX + diffZ * diffZ);

        float pitch = (float) -Math.atan2(dist, diffY);
        float yaw = (float) Math.atan2(diffZ, diffX);
        pitch = (float) MathUtils.wrapAngleTo180_double(((pitch * 180F) / Math.PI + 90) * -1);
        yaw = (float) MathUtils.wrapAngleTo180_double(((yaw * 180) / Math.PI) - 90);

        float rand = (float) (Math.random() - Math.random());
        yaw += rand;
        pitch -= rand;

        return new float[]{yaw, pitch};
    }

    private static Vec3d getTargetVec(Entity target, double maxReach) {
        for (int i = 0; i < 1000; i++) {
            Vec3d point = CombatUtils.getClosestVec(target, 1, 1, 0, 0, 0);
            if (point.distanceTo(CombatUtils.getEyeVec()) <= maxReach) {
                return point;
            }
        }
        return null;
    }

    // GCD Patch
    public static float[] getFixedRotation(final float[] rotations, final float[] lastRotations) {
        final float yaw = rotations[0];
        final float pitch = rotations[1];

        final float lastYaw = lastRotations[0];
        final float lastPitch = lastRotations[1];

        final float f = (float) (mc.options.getMouseSensitivity().getValue() * 0.6F + 0.2F);
        final float gcd = f * f * f * 1.2F;

        final float deltaYaw = yaw - lastYaw;
        final float deltaPitch = pitch - lastPitch;

        final float fixedDeltaYaw = deltaYaw - (deltaYaw % gcd);
        final float fixedDeltaPitch = deltaPitch - (deltaPitch % gcd);

        final float fixedYaw = lastYaw + fixedDeltaYaw;
        final float fixedPitch = lastPitch + fixedDeltaPitch;

        return new float[]{fixedYaw, fixedPitch};
    }

    // Rotation Fixes
    public static float[] getPatchedAndCappedRots(float[] prev, float[] current, float speed) {
        return getFixedRotation(getCappedRotations(prev, current, speed), prev);
    }

    public static float[] getCappedRotations(float[] prev, float[] current, float speed) {
        float yawDiff = RotationUtils.getYawDifference(current[0], prev[0]);
        if (Math.abs(yawDiff) > speed)
            yawDiff = (speed * (yawDiff > 0 ? 1 : -1)) / 2f;
        float cappedPYaw = MathUtils.wrapAngleTo180_float(prev[0] + yawDiff);

        float pitchDiff = RotationUtils.getYawDifference(current[1], prev[1]);
        if (Math.abs(pitchDiff) > speed / 2f)
            pitchDiff = (speed / 2f * (pitchDiff > 0 ? 1 : -1)) / 2f;
        float cappedPitch = MathUtils.wrapAngleTo180_float(prev[1] + pitchDiff);
        return new float[]{cappedPYaw, cappedPitch};
    }

    // Differences
    public static float getYawDifference(float yaw1, float yaw2) {
        float yawDiff = MathUtils.wrapAngleTo180_float(yaw1) - MathUtils.wrapAngleTo180_float(yaw2);
        if (Math.abs(yawDiff) > 180) {
            yawDiff = yawDiff + 360;
        }
        return MathUtils.wrapAngleTo180_float(yawDiff);
    }

    // Rise
    public static float[] calculate(final Vec3d from, final Vec3d to) {
        final Vec3d diff = to.subtract(from);
        final double distance = Math.hypot(diff.getX(), diff.getZ());
        final float yaw = (float) (MathHelper.atan2(diff.getZ(), diff.getX()) * TO_DEGREES) - 90.0F;
        final float pitch = (float) (-(MathHelper.atan2(diff.getY(), distance) * TO_DEGREES));
        return new float[]{yaw, pitch};
    }

    public static float[] calculate(final Vec3d to) {
        return calculate(mc.player.getPos().add(0, mc.player.getEyeHeight(mc.player.getPose()), 0), to);
    }

    public static float[] calculate(final Vec3d position, final Direction Direction) {
        double x = position.getX() + 0.5D;
        double y = position.getY() + 0.5D;
        double z = position.getZ() + 0.5D;

        x += (double) Direction.getVector().getX() * 0.5D;
        y += (double) Direction.getVector().getY() * 0.5D;
        z += (double) Direction.getVector().getZ() * 0.5D;
        return calculate(new Vec3d(x, y, z));
    }
}