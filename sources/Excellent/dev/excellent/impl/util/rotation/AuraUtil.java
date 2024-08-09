package dev.excellent.impl.util.rotation;

import dev.excellent.api.interfaces.client.IAccess;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.*;
import net.minecraft.util.math.vector.Vector3d;

import static net.minecraft.util.math.MathHelper.clamp;

public class AuraUtil implements IAccess {


    private static net.minecraft.util.math.vector.Vector3d calculateVector(LivingEntity target) {
        double yOffset = MathHelper.clamp(mc.player.getPosYEye() - target.getPosYEye(), 0.2, target.getEyeHeight());
        return target.getPositionVec().add(0, yOffset, 0);
    }

    public static Vector3d getClosestVec(Vector3d vec, AxisAlignedBB AABB) {
        return new Vector3d(
                clamp(vec.getX(), AABB.minX, AABB.maxX),
                clamp(vec.getY(), AABB.minY, AABB.maxY),
                clamp(vec.getZ(), AABB.minZ, AABB.maxZ)
        );
    }

    public static Vector3d getClosestVec(Entity entity) {
        Vector3d eyePosVec = mc.player.getEyePosition(1.0F);

        return getClosestVec(eyePosVec, entity).subtract(eyePosVec);
    }

    public static double getStrictDistance(Entity entity) {
        return getClosestVec(entity).length();
    }

    public static Vector3d getClosestVec(Vector3d vec, Entity entity) {
        return getClosestVec(vec, entity.getBoundingBox());
    }

    public static Vector3d getBestVec(final Vector3d pos, final AxisAlignedBB axisAlignedBB) {
        double lastDistance = Double.MAX_VALUE;
        Vector3d bestVec = null;

        final double xWidth = axisAlignedBB.maxX - axisAlignedBB.minX;
        final double zWidth = axisAlignedBB.maxZ - axisAlignedBB.minZ;
        final double height = axisAlignedBB.maxY - axisAlignedBB.minY;

        for (float x = 0F; x < 1F; x += 0.1F) {
            for (float y = 0F; y < 1F; y += 0.1F) {
                for (float z = 0F; z < 1F; z += 0.1F) {

                    final Vector3d hitVec = new Vector3d(
                            axisAlignedBB.minX + xWidth * x,
                            axisAlignedBB.minY + height * y,
                            axisAlignedBB.minZ + zWidth * z
                    );

                    final double distance = pos.distanceTo(hitVec);

                    if (isHitBoxNotVisible(hitVec) && distance < lastDistance) {
                        bestVec = hitVec;
                        lastDistance = distance;
                    }
                }
            }
        }

        return bestVec;
    }

    public static boolean isHitBoxNotVisible(final net.minecraft.util.math.vector.Vector3d vec3d) {
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

    public static Vector3d getVector(LivingEntity target) {

        double wHalf = target.getWidth() / 3D;

        double yExpand = clamp(target.getPosYEye() - target.getPosY(), 0, target.getHeight());

        double xExpand = clamp(mc.player.getPosX() - target.getPosX(), -wHalf, wHalf);
        double zExpand = clamp(mc.player.getPosZ() - target.getPosZ(), -wHalf, wHalf);

        return new Vector3d(
                target.getPosX() - mc.player.getPosX() + xExpand,
                target.getPosY() - mc.player.getPosYEye() + yExpand,
                target.getPosZ() - mc.player.getPosZ() + zExpand
        );
    }

}
