package dev.excellent.impl.util.rotation;

import dev.excellent.api.interfaces.game.IMinecraft;
import dev.excellent.impl.util.math.Mathf;
import lombok.Data;
import lombok.experimental.UtilityClass;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import org.joml.Vector2d;
import org.joml.Vector2f;
import org.joml.Vector3d;

import static java.lang.Math.PI;

@Data
@UtilityClass
public class RotationUtil implements IMinecraft {
    public float[] getRotation(final Vector3d pos) {
        final float deltaX = (float) pos.x;
        final float deltaY = (float) pos.y;
        final float deltaZ = (float) pos.z;
        final float distance = (float) Math.hypot(deltaX, deltaZ);
        float yaw = (float) (Math.toDegrees(Math.atan2(deltaZ, deltaX)) - 90F);
        float pitch = (float) (Math.toDegrees(-Math.atan2(deltaY, distance)));

        return new float[]{MathHelper.wrapDegrees(yaw), MathHelper.clamp(pitch, -90F, 90F)};
    }

    public double getDistance(Vector3d first, Vector3d second) {
        double x = first.x() - second.x();
        double y = first.y() - second.y();
        double z = first.z() - second.z();
        return Math.sqrt(x * x + y * y + z * z);
    }

    private Vector2d getRotationOffset(float yaw, double distance) {
        double yawRad = Math.toRadians(yaw),
                xOffset = -Math.sin(yawRad) * distance,
                zOffset = Math.cos(yawRad) * distance;
        return new Vector2d(xOffset, zOffset);
    }

    public float getAngleDiff(float angle1, float angle2) {
        float difference = Math.abs(angle1 - angle2) % 360;
        return difference > 180 ? 360 - difference : difference;
    }

    public boolean isInRange(float angle1, float angle2, float max) {
        return getAngleDiff(angle1, angle2) <= max;
    }

    public float calculateCorrectYawOffset(float yaw) {
        double xDiff = mc.player.getPosX() - mc.player.prevPosX;
        double zDiff = mc.player.getPosZ() - mc.player.prevPosZ;
        float distSquared = (float) (xDiff * xDiff + zDiff * zDiff);
        float renderYawOffset = mc.player.renderYawOffset;
        float offset = renderYawOffset;
        float yawOffsetDiff;

        if (distSquared > 0.0025000002F) {
            offset = (float) MathHelper.atan2(zDiff, xDiff) * 180F / (float) PI - 90F;
        }

        if (mc.player != null && mc.player.swingProgress > 0F) {
            offset = yaw;
        }

        yawOffsetDiff = MathHelper.wrapDegrees(yaw - (renderYawOffset + MathHelper.wrapDegrees(offset - renderYawOffset) * 0.3F));
        yawOffsetDiff = MathHelper.clamp(yawOffsetDiff, -75F, 75F);

        renderYawOffset = yaw - yawOffsetDiff;
        if (yawOffsetDiff * yawOffsetDiff > 2500F) {
            renderYawOffset += yawOffsetDiff * 0.2F;
        }

        return renderYawOffset;
    }

    public Vector2f calculate(final double x, final double y, final double z) {
        net.minecraft.util.math.vector.Vector3d pos = mc.player.getPositionVec().add(0, mc.player.getEyeHeight(), 0);
        return calculate(new Vector3d(pos.x, pos.y, pos.z), new Vector3d(x, y, z));
    }

    public Vector2f calculate(final Vector3d to) {
        net.minecraft.util.math.vector.Vector3d pos = mc.player.getPositionVec().add(0, mc.player.getEyeHeight(), 0);
        Vector3d from = new Vector3d(pos.x, pos.y, pos.z);
        return calculate(from, to);
    }

    public Vector2f calculate(final Vector3d from, final Vector3d to) {
        Vector3d diff = to.sub(from);
        double distance = Math.hypot(diff.x(), diff.z());
        float yaw = (float) (MathHelper.atan2(diff.z(), diff.x()) * 180F / PI) - 90F;
        float pitch = (float) (-(MathHelper.atan2(diff.y(), distance) * 180F / PI));
        yaw = normalize(yaw);
        pitch = (float) Mathf.clamp(-90, 90, pitch);
        return new Vector2f(yaw, pitch);
    }

    public float normalize(float value) {
        value = value % 360.0f;
        if (value > 180.0f) {
            value -= 360.0f;
        } else if (value < -180.0f) {
            value += 360.0f;
        }
        return value;
    }

    public Vector2f calculate(final Entity entity) {
        net.minecraft.util.math.vector.Vector3d pos = entity.getPositionVec().add(0,
                Math.max(0,
                        Math.min(mc.player.getPosY() - entity.getPosY() + mc.player.getEyeHeight(),
                                (entity.getBoundingBox().maxY - entity.getBoundingBox().minY) * 0.75F)),
                0);
        Vector3d to = new Vector3d(pos.x, pos.y, pos.z);
        return calculate(to);
    }

    public Vector2f resetRotation(final Vector2f rotation) {
        if (rotation == null) {
            return null;
        }
        final float yaw = rotation.x + MathHelper.wrapDegrees(mc.player.rotationYaw - rotation.x);
        final float pitch = mc.player.rotationPitch;
        return new Vector2f(yaw, pitch);
    }

    public Vector2f applySensitivityPatch(final Vector2f rotation) {
        final Vector2f previousRotation = mc.player.getPreviousRotation();
        final float mouseSensitivity = (float) (mc.gameSettings.mouseSensitivity * 0.6F + 0.2F);
        final double multiplier = Math.pow(mouseSensitivity, 3) * 8F * 0.15F;
        final float yaw = previousRotation.x + (float) (Math.round((rotation.x - previousRotation.x) / multiplier) * multiplier);
        final float pitch = previousRotation.y + (float) (Math.round((rotation.y - previousRotation.y) / multiplier) * multiplier);
        return new Vector2f(yaw, MathHelper.clamp(pitch, -90, 90));
    }

    public Vector2f applySensitivityPatch(final Vector2f rotation, final Vector2f previousRotation) {
        final float mouseSensitivity = (float) (mc.gameSettings.mouseSensitivity * 0.6F + 0.2F);
        final double multiplier = Math.pow(mouseSensitivity, 3) * 8F * 0.15F;
        final float yaw = previousRotation.x + (float) (Math.round((rotation.x - previousRotation.x) / multiplier) * multiplier);
        final float pitch = previousRotation.y + (float) (Math.round((rotation.y - previousRotation.y) / multiplier) * multiplier);
        return new Vector2f(yaw, MathHelper.clamp(pitch, -90, 90));
    }

    public Vector2f smooth(final Vector2f lastRotation, final Vector2f targetRotation, final double speed) {
        float yaw = targetRotation.x;
        float pitch = targetRotation.y;
        final float lastYaw = lastRotation.x;
        final float lastPitch = lastRotation.y;

        if (speed != 0) {
            final float rotationSpeed = (float) speed;

            final double deltaYaw = MathHelper.wrapDegrees(targetRotation.x - lastRotation.x);
            final double deltaPitch = pitch - lastPitch;

            final double distance = Math.sqrt(deltaYaw * deltaYaw + deltaPitch * deltaPitch);
            final double distributionYaw = Math.abs(deltaYaw / distance);
            final double distributionPitch = Math.abs(deltaPitch / distance);

            final double maxYaw = rotationSpeed * distributionYaw;
            final double maxPitch = rotationSpeed * distributionPitch;

            final float moveYaw = (float) Math.max(Math.min(deltaYaw, maxYaw), -maxYaw);
            final float movePitch = (float) Math.max(Math.min(deltaPitch, maxPitch), -maxPitch);

            yaw = lastYaw + moveYaw;
            pitch = lastPitch + movePitch;
        }

        float randomYaw = (float) (Math.random() * 2 - 1) / 10;
        float randomPitch = (float) (Math.random() * 2 - 1) / 10;

        yaw += randomYaw;
        pitch += randomPitch;

        final Vector2f rotations = new Vector2f(yaw, pitch);
        final Vector2f fixedRotations = RotationUtil.applySensitivityPatch(rotations);

        yaw = fixedRotations.x;
        pitch = Math.max(-90, Math.min(90, fixedRotations.y));

        return new Vector2f(yaw, pitch);
    }

}