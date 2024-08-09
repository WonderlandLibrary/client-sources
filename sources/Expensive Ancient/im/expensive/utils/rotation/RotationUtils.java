package im.expensive.utils.rotation;

import im.expensive.functions.impl.combat.killAura.rotation.VecRotation;
import im.expensive.utils.client.IMinecraft;
import im.expensive.utils.combat.Rotation;
import lombok.experimental.UtilityClass;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.*;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3d;

import javax.swing.*;
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Math.*;
import static net.minecraft.util.math.MathHelper.*;

@UtilityClass
public class RotationUtils implements IMinecraft {
    public Vector3d getClosestVec(Entity entity) {
        double wHalf = entity.getWidth() / 2;

        double yExpand = clamp(entity.getPosYEye() - entity.getPosY(), 0, entity.getHeight());

        double xExpand = clamp(mc.player.getPosX() - entity.getPosX(), -wHalf, wHalf);
        double zExpand = clamp(mc.player.getPosZ() - entity.getPosZ(), -wHalf, wHalf);

        return new Vector3d(
                entity.getPosX() - mc.player.getPosX() + xExpand,
                entity.getPosY() - mc.player.getPosYEye() + yExpand,
                entity.getPosZ() - mc.player.getPosZ() + zExpand
        );
    }

    public Rotation getRotation(Vector3d vec) {
        return new Rotation(
                (float) wrapDegrees(toDegrees(Math.atan2(vec.z, vec.x)) - 90),
                (float) wrapDegrees(toDegrees(-Math.atan2(vec.y, hypot(vec.x, vec.z))))
        );
    }

    public VecRotation createRotation(Vector3d vector) {
        return new VecRotation(
                (float) wrapDegrees(toDegrees(Math.atan2(vector.z, vector.x)) - 90),
                (float) wrapDegrees(toDegrees(-Math.atan2(vector.y, hypot(vector.x, vector.z))))
        );
    }

    public static Rotation resetRotation(Rotation rotation) {
        if (rotation == null) {
            return null;
        } else {
            float yaw = rotation.getYaw() + wrapDegrees(mc.player.rotationYaw - rotation.getYaw());
            float pitch = mc.player.rotationPitch;
            return new Rotation(yaw, pitch);
        }
    }

    public Vector3d raytraceBox(Vector3d vector, AxisAlignedBB box, double rangeSquared) {
        Vector3d vector3d = null;

        if (!isHitBoxNotVisible(vector)) {
            for (double x = 0.0; x <= 1.0; x += 0.1) {
                for (double y = 0.0; y <= 1.0; y += 0.1) {
                    for (double z = 0.0; z <= 1.0; z += 0.1) {
                        Vector3d spot = new Vector3d(
                                box.minX + (box.maxX - box.minX) * x,
                                box.minY + (box.maxY - box.minY) * y,
                                box.minZ + (box.maxZ - box.minZ) * z
                        );
                        double distance = vector.squareDistanceTo(spot);

                        if (distance > rangeSquared) {
                            continue;
                        }
                        if (isHitBoxNotVisible(spot)) {
                            vector3d = spot;
                            break;
                        }
                    }
                }
            }
        }
        return vector3d;
    }

    public static float squaredDistanceFromEyes(Vector3d vec) {
        double d0 = vec.x - mc.player.getPosX();
        double d1 = vec.z - mc.player.getPosZ();
        double d2 = vec.y - (mc.player.getPosY() + mc.player.getEyeHeight(mc.player.getPose()));
        return (float) (d0 * d0 + d1 * d1 + d2 * d2);
    }

    public boolean isHitBoxNotVisible(final Vector3d vec3d) {
        final RayTraceContext rayTraceContext = new RayTraceContext(
                mc.player.getEyePosition(1F),
                vec3d,
                RayTraceContext.BlockMode.COLLIDER,
                RayTraceContext.FluidMode.NONE,
                mc.player
        );
        final BlockRayTraceResult blockHitResult = mc.world.rayTraceBlocks(rayTraceContext);
        return blockHitResult.getType() == RayTraceResult.Type.MISS;
    }

    public Vector3d getNearestPoint(Vector3d eyes, AxisAlignedBB box) {
        double[] origin = {eyes.x, eyes.y, eyes.z};
        double[] destMins = {box.minX, box.minY, box.minZ};
        double[] destMaxs = {box.maxX, box.maxY, box.maxZ};

        // Проходим по каждой координате массивов с плавающей запятой и выбираем ближайшую точку
        for (int i = 0; i <= 2; i++) {
            origin[i] = Math.max(destMins[i], Math.min(destMaxs[i], origin[i]));
        }

        return new Vector3d(origin[0], origin[1], origin[2]);
    }

    float lastYaw = 0;

    public Rotation getRotation(Rotation lastRotation, Rotation targetRotation, final double speed) {
        float yaw = targetRotation.getYaw();
        float pitch = targetRotation.getPitch();
        final float lastYaw = lastRotation.getYaw();
        final float lastPitch = lastRotation.getPitch();
        final float rotationSpeed = (float) speed;

        if (speed > 0.0f) {
            final double deltaYaw = wrapDegrees(targetRotation.getYaw() - lastRotation.getYaw());
            final double deltaPitch = pitch - lastPitch;

            final double distance = Math.sqrt(deltaYaw * deltaYaw + deltaPitch * deltaPitch);
            final double distributionYaw = Math.abs(deltaYaw / distance);
            final double distributionPitch = Math.abs(deltaPitch / distance);

            double maxYaw = rotationSpeed * distributionYaw;
            final double maxPitch = rotationSpeed * distributionPitch;


            float moveYaw = (float) Math.max(Math.min(deltaYaw, maxYaw), -maxYaw);
            final float movePitch = (float) Math.max(Math.min(deltaPitch, maxPitch), -maxPitch);

            yaw = lastYaw + moveYaw;
            pitch = lastPitch + movePitch;
        }

        yaw += ThreadLocalRandom.current().nextFloat(-0.5f, 0.5f);
        pitch -= ThreadLocalRandom.current().nextFloat(-0.5f, 0.5f);

        final Rotation rotations = new Rotation(yaw, pitch);

        yaw = rotations.getYaw();
        pitch = Math.max(-90, Math.min(90, rotations.getPitch()));

        return new Rotation(yaw, pitch);
    }

    public Vector2f getFakeRotation(LivingEntity target, float attackRange, Vector2f previousRotation) {
        Vector3d targetPosition = calculateVectorToTarget(target, attackRange);
        float targetYaw = (float) MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(targetPosition.z, targetPosition.x)) - 90);
        float targetPitch = (float) Math.toDegrees(-Math.atan2(targetPosition.y, Math.hypot(targetPosition.x, targetPosition.z)));


        float yawDifference = MathHelper.wrapDegrees(targetYaw - previousRotation.x);
        float pitchDifference = (targetPitch - previousRotation.y);

        final double distance = Math.sqrt(yawDifference * yawDifference + pitchDifference * pitchDifference);
        final double distributionYaw = Math.abs(yawDifference / distance);
        final double distributionPitch = Math.abs(pitchDifference / distance);

        float maxYaw = (float) (90.0F * distributionYaw);
        float maxPitch = (float) (30.0F * distributionPitch);


        float clampedYaw = MathHelper.clamp(yawDifference, -maxYaw, maxYaw);
        float clampedPitch = MathHelper.clamp(pitchDifference, -maxPitch, maxPitch);


        float newYaw = previousRotation.x + clampedYaw;
        float newPitch = previousRotation.y + clampedPitch;

        return new Vector2f(newYaw, MathHelper.clamp(newPitch, -90, 90));
    }

    public Vector3d calculateVectorToTarget(Entity target, double attackRange) {
        double eyePositionYOffset = mc.player.getEyePosition(1).y - target.getPosY();
        double maxOffset = target.getHeight() * (RotationUtils.getDistanceEyePos(target) / attackRange);
        double clampedYOffset = clamp(eyePositionYOffset, 0, maxOffset);

        Vector3d playerEyePosition = mc.player.getEyePosition(1.0F);
        return target.getPositionVec()
                .add(0, clampedYOffset, 0)
                .subtract(playerEyePosition);
    }

    public double getDistanceEyePos(Entity target) {
        double wHalf = target.getWidth() / 2.0f;
        double x = MathHelper.clamp(mc.player.getPosX() - target.getPosX(), -wHalf, wHalf);
        double y = MathHelper.clamp(mc.player.getPosY() + (double) mc.player.getEyeHeight() - target.getPosY(),
                0.0, target.getHeight());
        double z = MathHelper.clamp(mc.player.getPosZ() - target.getPosZ(), -wHalf, wHalf);
        return mc.player.getEyePosition(1.0f).distanceTo(target.getPositionVec().add(x, y, z));
    }

    public double getStrictDistance(Entity entity) {
        return getClosestVec(entity).length();
    }

}
