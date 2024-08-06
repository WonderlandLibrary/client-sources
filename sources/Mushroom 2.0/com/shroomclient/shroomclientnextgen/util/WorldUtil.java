package com.shroomclient.shroomclientnextgen.util;

import java.util.ArrayList;
import java.util.Iterator;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.RaycastContext;

public class WorldUtil {

    public static ArrayList<BlockPos> findBlocksWithinDistance(
        Vec3d source,
        double maxDistance,
        Block type
    ) {
        ArrayList<BlockPos> o = new ArrayList<>();
        for (BlockPos bp : VectorUtil.getPosWithinDistance(
            source,
            maxDistance
        )) {
            BlockState bs = C.w().getBlockState(bp);
            if (bs.getBlock() == type) o.add(bp);
        }
        return o;
    }

    public static @Nullable BlockHitResult rayTraceBlocks(
        Vec3d start,
        Vec3d end,
        RaycastContext.FluidHandling fluid,
        Entity ent
    ) {
        return C.w()
            .raycast(
                new RaycastContext(
                    start,
                    end,
                    RaycastContext.ShapeType.COLLIDER,
                    fluid,
                    ent
                )
            );
    }

    public static @Nullable BlockHitResult rayTraceBlocks(
        Vec3d start,
        Vec3d end
    ) {
        return rayTraceBlocks(
            start,
            end,
            RaycastContext.FluidHandling.NONE,
            C.p()
        );
    }

    public static BlockHitResult raytraceBlocksPlayer(
        double blockReachDistance,
        float pitch,
        float yaw
    ) {
        Vec3d vec3 = C.p()
            .getPos()
            .add(new Vec3d(0, C.p().getEyeHeight(C.p().getPose()), 0));
        Vec3d vec31 = VectorUtil.getVectorForRotation(pitch, yaw);
        Vec3d vec32 = vec3.add(
            vec31.x * blockReachDistance,
            vec31.y * blockReachDistance,
            vec31.z * blockReachDistance
        );
        return rayTraceBlocks(vec3, vec32);
    }

    public static boolean isLookingAtBox(
        Box box,
        double reach,
        float pitch,
        float yaw,
        double step
    ) {
        Vec3d look = VectorUtil.getVectorForRotation(pitch, yaw);
        Vec3d start = C.p()
            .getPos()
            .add(new Vec3d(0, C.p().getEyeHeight(C.p().getPose()), 0));
        Vec3d current;
        double multi = 0;
        while (true) {
            current = start.add(
                new Vec3d(look.x * multi, look.y * multi, look.z * multi)
            );
            double d = start.distanceTo(current);
            if (d > reach) return false;
            if (box.contains(current)) return true;
            multi += step;
        }
    }

    public static boolean isLookingAtEntity(
        Entity entity,
        double reach,
        float pitch,
        float yaw,
        double step,
        double hbMulti
    ) {
        Vec3d look = VectorUtil.getVectorForRotation(pitch, yaw);
        Vec3d start = C.p()
            .getPos()
            .add(new Vec3d(0, C.p().getEyeHeight(C.p().getPose()), 0));
        Box boundingBox = entity.getBoundingBox();
        boundingBox = boundingBox.expand(
            (boundingBox.getLengthX() * (hbMulti - 1d)) / 2d,
            (boundingBox.getLengthY() * (hbMulti - 1d)) / 2d,
            (boundingBox.getLengthZ() * (hbMulti - 1d)) / 2d
        );
        Vec3d current;
        double multi = 0;
        while (true) {
            current = start.add(
                new Vec3d(look.x * multi, look.y * multi, look.z * multi)
            );
            double d = start.distanceTo(current);
            if (d > reach) return false;
            if (boundingBox.contains(current)) return true;
            multi += step;
        }
    }

    public static boolean isLookingAtEntity(
        Entity entity,
        double reach,
        float pitch,
        float yaw
    ) {
        return isLookingAtEntity(entity, reach, pitch, yaw, 0.1d, 1d);
    }

    public static boolean isOverVoid(Vec3d pos) {
        BlockPos bp = new BlockPos(VectorUtil.toVec3i(pos));
        for (int y = bp.getY(); y >= 0; y--) {
            BlockPos b = new BlockPos(bp.getX(), y, bp.getZ());
            Block bl = C.w().getBlockState(b).getBlock();
            if (bl != Blocks.AIR) return false;
        }
        return true;
    }

    public static double getFallDistance(Vec3d pos) {
        BlockPos bp = new BlockPos(VectorUtil.toVec3i(pos));

        for (int y = bp.getY(); y >= 0; y--) {
            BlockPos b = new BlockPos(bp.getX(), y, bp.getZ());
            Block bl = C.w().getBlockState(b).getBlock();
            if (bl != Blocks.AIR) return pos.y - y;
        }

        return Double.MAX_VALUE;
    }

    public static boolean canEntityBeSeen(Entity entityIn) {
        BlockHitResult rtr = rayTraceBlocks(
            new Vec3d(
                C.p().getX(),
                C.p().getY() + (double) C.p().getEyeHeight(C.p().getPose()),
                C.p().getZ()
            ),
            new Vec3d(
                entityIn.getX(),
                entityIn.getY() +
                (double) entityIn.getEyeHeight(entityIn.getPose()),
                entityIn.getZ()
            )
        );
        return rtr == null || rtr.getType() != HitResult.Type.BLOCK;
    }

    public static Vec3d prevCamEntityPos;
    public static Vec3d CamEntityPos;

    public static BlockHitResult rayTracePrev(
        float pitch,
        float yaw,
        float reach
    ) {
        if (C.mc.cameraEntity != null) {
            Vec3d start = new Vec3d(
                C.mc.cameraEntity.prevX,
                C.mc.cameraEntity.prevY,
                C.mc.cameraEntity.prevZ
            );
            Vec3d rotationVec = VectorUtil.getVectorForRotation(pitch, yaw);
            Vec3d end = start.add(
                rotationVec.x * reach,
                rotationVec.y * reach,
                rotationVec.z * reach
            );

            return C.w()
                .raycast(
                    new RaycastContext(
                        start,
                        end,
                        RaycastContext.ShapeType.COLLIDER,
                        RaycastContext.FluidHandling.NONE,
                        C.p()
                    )
                );
        }

        return null;
    }

    public static BlockHitResult rayTrace(float pitch, float yaw, float reach) {
        if (C.mc.cameraEntity != null) {
            Entity camEntity = C.mc.cameraEntity;

            Vec3d start = camEntity.getCameraPosVec(1.0f);
            Vec3d rotationVec = VectorUtil.getVectorForRotation(pitch, yaw);
            Vec3d end = start.add(
                rotationVec.x * reach,
                rotationVec.y * reach,
                rotationVec.z * reach
            );

            return C.w()
                .raycast(
                    new RaycastContext(
                        start,
                        end,
                        RaycastContext.ShapeType.OUTLINE,
                        RaycastContext.FluidHandling.NONE,
                        camEntity
                    )
                );
        }

        return null;
    }

    public static BlockPos highestCollision() {
        final Box player = C.p().getBoundingBox();
        Iterator<VoxelShape> collisions = C.w()
            .getCollisions(
                C.p(),
                new Box(
                    player.minX,
                    0.0,
                    player.minZ,
                    player.maxX,
                    player.maxY,
                    player.maxZ
                )
            )
            .iterator();

        if (!collisions.hasNext()) return null;
        VoxelShape topCollision = collisions.next();
        while (collisions.hasNext()) topCollision = collisions.next();
        Box collision = topCollision.getBoundingBox();

        return new BlockPos(
            (int) collision.minX,
            (int) collision.minY,
            (int) collision.minZ
        );
    }

    public static boolean overWater() {
        return C.w().getBlockState(C.p().getBlockPos()).isLiquid();
    }

    public static EntityHitResult rayTraceEntity(
        float pitch,
        float yaw,
        double reach
    ) {
        Entity camEntity = C.mc.cameraEntity;
        Vec3d cameraVec = camEntity.getCameraPosVec(0);
        Vec3d rotationVec = VectorUtil.getVectorForRotation(pitch, yaw);
        Vec3d end = cameraVec.add(
            rotationVec.x * reach,
            rotationVec.y * reach,
            rotationVec.z * reach
        );
        Box box = camEntity
            .getBoundingBox()
            .stretch(rotationVec.multiply(reach))
            .expand(1.0, 1.0, 1.0);

        EntityHitResult entityHitResult = ProjectileUtil.raycast(
            camEntity,
            cameraVec,
            end,
            box,
            Entity::canHit,
            reach * reach
        );

        return entityHitResult;
    }
}
