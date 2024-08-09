/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.pathfinding;

import javax.annotation.Nullable;
import net.minecraft.block.AbstractRailBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.MobEntity;
import net.minecraft.pathfinding.FlaggedPathPoint;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.pathfinding.WalkNodeProcessor;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.Region;

public class WalkAndSwimNodeProcessor
extends WalkNodeProcessor {
    private float field_203247_k;
    private float field_203248_l;

    @Override
    public void func_225578_a_(Region region, MobEntity mobEntity) {
        super.func_225578_a_(region, mobEntity);
        mobEntity.setPathPriority(PathNodeType.WATER, 0.0f);
        this.field_203247_k = mobEntity.getPathPriority(PathNodeType.WALKABLE);
        mobEntity.setPathPriority(PathNodeType.WALKABLE, 6.0f);
        this.field_203248_l = mobEntity.getPathPriority(PathNodeType.WATER_BORDER);
        mobEntity.setPathPriority(PathNodeType.WATER_BORDER, 4.0f);
    }

    @Override
    public void postProcess() {
        this.entity.setPathPriority(PathNodeType.WALKABLE, this.field_203247_k);
        this.entity.setPathPriority(PathNodeType.WATER_BORDER, this.field_203248_l);
        super.postProcess();
    }

    @Override
    public PathPoint getStart() {
        return this.openPoint(MathHelper.floor(this.entity.getBoundingBox().minX), MathHelper.floor(this.entity.getBoundingBox().minY + 0.5), MathHelper.floor(this.entity.getBoundingBox().minZ));
    }

    @Override
    public FlaggedPathPoint func_224768_a(double d, double d2, double d3) {
        return new FlaggedPathPoint(this.openPoint(MathHelper.floor(d), MathHelper.floor(d2 + 0.5), MathHelper.floor(d3)));
    }

    @Override
    public int func_222859_a(PathPoint[] pathPointArray, PathPoint pathPoint) {
        PathPoint pathPoint2;
        boolean bl;
        int n = 0;
        boolean bl2 = true;
        BlockPos blockPos = new BlockPos(pathPoint.x, pathPoint.y, pathPoint.z);
        double d = this.func_203246_a(blockPos);
        PathPoint pathPoint3 = this.func_203245_a(pathPoint.x, pathPoint.y, pathPoint.z + 1, 1, d);
        PathPoint pathPoint4 = this.func_203245_a(pathPoint.x - 1, pathPoint.y, pathPoint.z, 1, d);
        PathPoint pathPoint5 = this.func_203245_a(pathPoint.x + 1, pathPoint.y, pathPoint.z, 1, d);
        PathPoint pathPoint6 = this.func_203245_a(pathPoint.x, pathPoint.y, pathPoint.z - 1, 1, d);
        PathPoint pathPoint7 = this.func_203245_a(pathPoint.x, pathPoint.y + 1, pathPoint.z, 0, d);
        PathPoint pathPoint8 = this.func_203245_a(pathPoint.x, pathPoint.y - 1, pathPoint.z, 1, d);
        if (pathPoint3 != null && !pathPoint3.visited) {
            pathPointArray[n++] = pathPoint3;
        }
        if (pathPoint4 != null && !pathPoint4.visited) {
            pathPointArray[n++] = pathPoint4;
        }
        if (pathPoint5 != null && !pathPoint5.visited) {
            pathPointArray[n++] = pathPoint5;
        }
        if (pathPoint6 != null && !pathPoint6.visited) {
            pathPointArray[n++] = pathPoint6;
        }
        if (pathPoint7 != null && !pathPoint7.visited) {
            pathPointArray[n++] = pathPoint7;
        }
        if (pathPoint8 != null && !pathPoint8.visited) {
            pathPointArray[n++] = pathPoint8;
        }
        boolean bl3 = pathPoint6 == null || pathPoint6.nodeType == PathNodeType.OPEN || pathPoint6.costMalus != 0.0f;
        boolean bl4 = pathPoint3 == null || pathPoint3.nodeType == PathNodeType.OPEN || pathPoint3.costMalus != 0.0f;
        boolean bl5 = pathPoint5 == null || pathPoint5.nodeType == PathNodeType.OPEN || pathPoint5.costMalus != 0.0f;
        boolean bl6 = bl = pathPoint4 == null || pathPoint4.nodeType == PathNodeType.OPEN || pathPoint4.costMalus != 0.0f;
        if (bl3 && bl && (pathPoint2 = this.func_203245_a(pathPoint.x - 1, pathPoint.y, pathPoint.z - 1, 1, d)) != null && !pathPoint2.visited) {
            pathPointArray[n++] = pathPoint2;
        }
        if (bl3 && bl5 && (pathPoint2 = this.func_203245_a(pathPoint.x + 1, pathPoint.y, pathPoint.z - 1, 1, d)) != null && !pathPoint2.visited) {
            pathPointArray[n++] = pathPoint2;
        }
        if (bl4 && bl && (pathPoint2 = this.func_203245_a(pathPoint.x - 1, pathPoint.y, pathPoint.z + 1, 1, d)) != null && !pathPoint2.visited) {
            pathPointArray[n++] = pathPoint2;
        }
        if (bl4 && bl5 && (pathPoint2 = this.func_203245_a(pathPoint.x + 1, pathPoint.y, pathPoint.z + 1, 1, d)) != null && !pathPoint2.visited) {
            pathPointArray[n++] = pathPoint2;
        }
        return n;
    }

    private double func_203246_a(BlockPos blockPos) {
        if (!this.entity.isInWater()) {
            BlockPos blockPos2 = blockPos.down();
            VoxelShape voxelShape = this.blockaccess.getBlockState(blockPos2).getCollisionShape(this.blockaccess, blockPos2);
            return (double)blockPos2.getY() + (voxelShape.isEmpty() ? 0.0 : voxelShape.getEnd(Direction.Axis.Y));
        }
        return (double)blockPos.getY() + 0.5;
    }

    @Nullable
    private PathPoint func_203245_a(int n, int n2, int n3, int n4, double d) {
        PathPoint pathPoint = null;
        BlockPos blockPos = new BlockPos(n, n2, n3);
        double d2 = this.func_203246_a(blockPos);
        if (d2 - d > 1.125) {
            return null;
        }
        PathNodeType pathNodeType = this.getPathNodeType(this.blockaccess, n, n2, n3, this.entity, this.entitySizeX, this.entitySizeY, this.entitySizeZ, false, true);
        float f = this.entity.getPathPriority(pathNodeType);
        double d3 = (double)this.entity.getWidth() / 2.0;
        if (f >= 0.0f) {
            pathPoint = this.openPoint(n, n2, n3);
            pathPoint.nodeType = pathNodeType;
            pathPoint.costMalus = Math.max(pathPoint.costMalus, f);
        }
        if (pathNodeType != PathNodeType.WATER && pathNodeType != PathNodeType.WALKABLE) {
            if (pathPoint == null && n4 > 0 && pathNodeType != PathNodeType.FENCE && pathNodeType != PathNodeType.UNPASSABLE_RAIL && pathNodeType != PathNodeType.TRAPDOOR) {
                pathPoint = this.func_203245_a(n, n2 + 1, n3, n4 - 1, d);
            }
            if (pathNodeType == PathNodeType.OPEN) {
                AxisAlignedBB axisAlignedBB = new AxisAlignedBB((double)n - d3 + 0.5, (double)n2 + 0.001, (double)n3 - d3 + 0.5, (double)n + d3 + 0.5, (float)n2 + this.entity.getHeight(), (double)n3 + d3 + 0.5);
                if (!this.entity.world.hasNoCollisions(this.entity, axisAlignedBB)) {
                    return null;
                }
                PathNodeType pathNodeType2 = this.getPathNodeType(this.blockaccess, n, n2 - 1, n3, this.entity, this.entitySizeX, this.entitySizeY, this.entitySizeZ, false, true);
                if (pathNodeType2 == PathNodeType.BLOCKED) {
                    pathPoint = this.openPoint(n, n2, n3);
                    pathPoint.nodeType = PathNodeType.WALKABLE;
                    pathPoint.costMalus = Math.max(pathPoint.costMalus, f);
                    return pathPoint;
                }
                if (pathNodeType2 == PathNodeType.WATER) {
                    pathPoint = this.openPoint(n, n2, n3);
                    pathPoint.nodeType = PathNodeType.WATER;
                    pathPoint.costMalus = Math.max(pathPoint.costMalus, f);
                    return pathPoint;
                }
                int n5 = 0;
                while (n2 > 0 && pathNodeType == PathNodeType.OPEN) {
                    --n2;
                    if (n5++ >= this.entity.getMaxFallHeight()) {
                        return null;
                    }
                    pathNodeType = this.getPathNodeType(this.blockaccess, n, n2, n3, this.entity, this.entitySizeX, this.entitySizeY, this.entitySizeZ, false, true);
                    f = this.entity.getPathPriority(pathNodeType);
                    if (pathNodeType != PathNodeType.OPEN && f >= 0.0f) {
                        pathPoint = this.openPoint(n, n2, n3);
                        pathPoint.nodeType = pathNodeType;
                        pathPoint.costMalus = Math.max(pathPoint.costMalus, f);
                        break;
                    }
                    if (!(f < 0.0f)) continue;
                    return null;
                }
            }
            return pathPoint;
        }
        if (n2 < this.entity.world.getSeaLevel() - 10 && pathPoint != null) {
            pathPoint.costMalus += 1.0f;
        }
        return pathPoint;
    }

    @Override
    protected PathNodeType func_215744_a(IBlockReader iBlockReader, boolean bl, boolean bl2, BlockPos blockPos, PathNodeType pathNodeType) {
        if (pathNodeType == PathNodeType.RAIL && !(iBlockReader.getBlockState(blockPos).getBlock() instanceof AbstractRailBlock) && !(iBlockReader.getBlockState(blockPos.down()).getBlock() instanceof AbstractRailBlock)) {
            pathNodeType = PathNodeType.UNPASSABLE_RAIL;
        }
        if (pathNodeType == PathNodeType.DOOR_OPEN || pathNodeType == PathNodeType.DOOR_WOOD_CLOSED || pathNodeType == PathNodeType.DOOR_IRON_CLOSED) {
            pathNodeType = PathNodeType.BLOCKED;
        }
        if (pathNodeType == PathNodeType.LEAVES) {
            pathNodeType = PathNodeType.BLOCKED;
        }
        return pathNodeType;
    }

    @Override
    public PathNodeType getPathNodeType(IBlockReader iBlockReader, int n, int n2, int n3) {
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        PathNodeType pathNodeType = WalkAndSwimNodeProcessor.func_237238_b_(iBlockReader, mutable.setPos(n, n2, n3));
        if (pathNodeType == PathNodeType.WATER) {
            for (Direction direction : Direction.values()) {
                PathNodeType pathNodeType2 = WalkAndSwimNodeProcessor.func_237238_b_(iBlockReader, mutable.setPos(n, n2, n3).move(direction));
                if (pathNodeType2 != PathNodeType.BLOCKED) continue;
                return PathNodeType.WATER_BORDER;
            }
            return PathNodeType.WATER;
        }
        if (pathNodeType == PathNodeType.OPEN && n2 >= 1) {
            BlockState blockState = iBlockReader.getBlockState(new BlockPos(n, n2 - 1, n3));
            PathNodeType pathNodeType3 = WalkAndSwimNodeProcessor.func_237238_b_(iBlockReader, mutable.setPos(n, n2 - 1, n3));
            pathNodeType = pathNodeType3 != PathNodeType.WALKABLE && pathNodeType3 != PathNodeType.OPEN && pathNodeType3 != PathNodeType.LAVA ? PathNodeType.WALKABLE : PathNodeType.OPEN;
            if (pathNodeType3 == PathNodeType.DAMAGE_FIRE || blockState.isIn(Blocks.MAGMA_BLOCK) || blockState.isIn(BlockTags.CAMPFIRES)) {
                pathNodeType = PathNodeType.DAMAGE_FIRE;
            }
            if (pathNodeType3 == PathNodeType.DAMAGE_CACTUS) {
                pathNodeType = PathNodeType.DAMAGE_CACTUS;
            }
            if (pathNodeType3 == PathNodeType.DAMAGE_OTHER) {
                pathNodeType = PathNodeType.DAMAGE_OTHER;
            }
        }
        if (pathNodeType == PathNodeType.WALKABLE) {
            pathNodeType = WalkAndSwimNodeProcessor.func_237232_a_(iBlockReader, mutable.setPos(n, n2, n3), pathNodeType);
        }
        return pathNodeType;
    }
}

