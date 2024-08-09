/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.pathfinding;

import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.entity.MobEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.pathfinding.FlaggedPathPoint;
import net.minecraft.pathfinding.NodeProcessor;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.pathfinding.PathType;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockReader;

public class SwimNodeProcessor
extends NodeProcessor {
    private final boolean field_205202_j;

    public SwimNodeProcessor(boolean bl) {
        this.field_205202_j = bl;
    }

    @Override
    public PathPoint getStart() {
        return super.openPoint(MathHelper.floor(this.entity.getBoundingBox().minX), MathHelper.floor(this.entity.getBoundingBox().minY + 0.5), MathHelper.floor(this.entity.getBoundingBox().minZ));
    }

    @Override
    public FlaggedPathPoint func_224768_a(double d, double d2, double d3) {
        return new FlaggedPathPoint(super.openPoint(MathHelper.floor(d - (double)(this.entity.getWidth() / 2.0f)), MathHelper.floor(d2 + 0.5), MathHelper.floor(d3 - (double)(this.entity.getWidth() / 2.0f))));
    }

    @Override
    public int func_222859_a(PathPoint[] pathPointArray, PathPoint pathPoint) {
        int n = 0;
        for (Direction direction : Direction.values()) {
            PathPoint pathPoint2 = this.getWaterNode(pathPoint.x + direction.getXOffset(), pathPoint.y + direction.getYOffset(), pathPoint.z + direction.getZOffset());
            if (pathPoint2 == null || pathPoint2.visited) continue;
            pathPointArray[n++] = pathPoint2;
        }
        return n;
    }

    @Override
    public PathNodeType getPathNodeType(IBlockReader iBlockReader, int n, int n2, int n3, MobEntity mobEntity, int n4, int n5, int n6, boolean bl, boolean bl2) {
        return this.getPathNodeType(iBlockReader, n, n2, n3);
    }

    @Override
    public PathNodeType getPathNodeType(IBlockReader iBlockReader, int n, int n2, int n3) {
        BlockPos blockPos = new BlockPos(n, n2, n3);
        FluidState fluidState = iBlockReader.getFluidState(blockPos);
        BlockState blockState = iBlockReader.getBlockState(blockPos);
        if (fluidState.isEmpty() && blockState.allowsMovement(iBlockReader, blockPos.down(), PathType.WATER) && blockState.isAir()) {
            return PathNodeType.BREACH;
        }
        return fluidState.isTagged(FluidTags.WATER) && blockState.allowsMovement(iBlockReader, blockPos, PathType.WATER) ? PathNodeType.WATER : PathNodeType.BLOCKED;
    }

    @Nullable
    private PathPoint getWaterNode(int n, int n2, int n3) {
        PathNodeType pathNodeType = this.isFree(n, n2, n3);
        return (!this.field_205202_j || pathNodeType != PathNodeType.BREACH) && pathNodeType != PathNodeType.WATER ? null : this.openPoint(n, n2, n3);
    }

    @Override
    @Nullable
    protected PathPoint openPoint(int n, int n2, int n3) {
        PathPoint pathPoint = null;
        PathNodeType pathNodeType = this.getPathNodeType(this.entity.world, n, n2, n3);
        float f = this.entity.getPathPriority(pathNodeType);
        if (f >= 0.0f) {
            pathPoint = super.openPoint(n, n2, n3);
            pathPoint.nodeType = pathNodeType;
            pathPoint.costMalus = Math.max(pathPoint.costMalus, f);
            if (this.blockaccess.getFluidState(new BlockPos(n, n2, n3)).isEmpty()) {
                pathPoint.costMalus += 8.0f;
            }
        }
        return pathNodeType == PathNodeType.OPEN ? pathPoint : pathPoint;
    }

    private PathNodeType isFree(int n, int n2, int n3) {
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for (int i = n; i < n + this.entitySizeX; ++i) {
            for (int j = n2; j < n2 + this.entitySizeY; ++j) {
                for (int k = n3; k < n3 + this.entitySizeZ; ++k) {
                    FluidState fluidState = this.blockaccess.getFluidState(mutable.setPos(i, j, k));
                    BlockState blockState = this.blockaccess.getBlockState(mutable.setPos(i, j, k));
                    if (fluidState.isEmpty() && blockState.allowsMovement(this.blockaccess, (BlockPos)mutable.down(), PathType.WATER) && blockState.isAir()) {
                        return PathNodeType.BREACH;
                    }
                    if (fluidState.isTagged(FluidTags.WATER)) continue;
                    return PathNodeType.BLOCKED;
                }
            }
        }
        BlockState blockState = this.blockaccess.getBlockState(mutable);
        return blockState.allowsMovement(this.blockaccess, mutable, PathType.WATER) ? PathNodeType.WATER : PathNodeType.BLOCKED;
    }
}

