/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.pathfinding;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathFinder;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.pathfinding.PathType;
import net.minecraft.pathfinding.WalkNodeProcessor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class GroundPathNavigator
extends PathNavigator {
    private boolean shouldAvoidSun;

    public GroundPathNavigator(MobEntity mobEntity, World world) {
        super(mobEntity, world);
    }

    @Override
    protected PathFinder getPathFinder(int n) {
        this.nodeProcessor = new WalkNodeProcessor();
        this.nodeProcessor.setCanEnterDoors(false);
        return new PathFinder(this.nodeProcessor, n);
    }

    @Override
    protected boolean canNavigate() {
        return this.entity.isOnGround() || this.isInLiquid() || this.entity.isPassenger();
    }

    @Override
    protected Vector3d getEntityPosition() {
        return new Vector3d(this.entity.getPosX(), this.getPathablePosY(), this.entity.getPosZ());
    }

    @Override
    public Path getPathToPos(BlockPos blockPos, int n) {
        BlockPos blockPos2;
        if (this.world.getBlockState(blockPos).isAir()) {
            blockPos2 = blockPos.down();
            while (blockPos2.getY() > 0 && this.world.getBlockState(blockPos2).isAir()) {
                blockPos2 = blockPos2.down();
            }
            if (blockPos2.getY() > 0) {
                return super.getPathToPos(blockPos2.up(), n);
            }
            while (blockPos2.getY() < this.world.getHeight() && this.world.getBlockState(blockPos2).isAir()) {
                blockPos2 = blockPos2.up();
            }
            blockPos = blockPos2;
        }
        if (!this.world.getBlockState(blockPos).getMaterial().isSolid()) {
            return super.getPathToPos(blockPos, n);
        }
        blockPos2 = blockPos.up();
        while (blockPos2.getY() < this.world.getHeight() && this.world.getBlockState(blockPos2).getMaterial().isSolid()) {
            blockPos2 = blockPos2.up();
        }
        return super.getPathToPos(blockPos2, n);
    }

    @Override
    public Path getPathToEntity(Entity entity2, int n) {
        return this.getPathToPos(entity2.getPosition(), n);
    }

    private int getPathablePosY() {
        if (this.entity.isInWater() && this.getCanSwim()) {
            int n = MathHelper.floor(this.entity.getPosY());
            Block block = this.world.getBlockState(new BlockPos(this.entity.getPosX(), (double)n, this.entity.getPosZ())).getBlock();
            int n2 = 0;
            while (block == Blocks.WATER) {
                block = this.world.getBlockState(new BlockPos(this.entity.getPosX(), (double)(++n), this.entity.getPosZ())).getBlock();
                if (++n2 <= 16) continue;
                return MathHelper.floor(this.entity.getPosY());
            }
            return n;
        }
        return MathHelper.floor(this.entity.getPosY() + 0.5);
    }

    @Override
    protected void trimPath() {
        super.trimPath();
        if (this.shouldAvoidSun) {
            if (this.world.canSeeSky(new BlockPos(this.entity.getPosX(), this.entity.getPosY() + 0.5, this.entity.getPosZ()))) {
                return;
            }
            for (int i = 0; i < this.currentPath.getCurrentPathLength(); ++i) {
                PathPoint pathPoint = this.currentPath.getPathPointFromIndex(i);
                if (!this.world.canSeeSky(new BlockPos(pathPoint.x, pathPoint.y, pathPoint.z))) continue;
                this.currentPath.setCurrentPathLength(i);
                return;
            }
        }
    }

    @Override
    protected boolean isDirectPathBetweenPoints(Vector3d vector3d, Vector3d vector3d2, int n, int n2, int n3) {
        int n4 = MathHelper.floor(vector3d.x);
        int n5 = MathHelper.floor(vector3d.z);
        double d = vector3d2.x - vector3d.x;
        double d2 = vector3d2.z - vector3d.z;
        double d3 = d * d + d2 * d2;
        if (d3 < 1.0E-8) {
            return true;
        }
        double d4 = 1.0 / Math.sqrt(d3);
        if (!this.isSafeToStandAt(n4, MathHelper.floor(vector3d.y), n5, n += 2, n2, n3 += 2, vector3d, d *= d4, d2 *= d4)) {
            return true;
        }
        n -= 2;
        n3 -= 2;
        double d5 = 1.0 / Math.abs(d);
        double d6 = 1.0 / Math.abs(d2);
        double d7 = (double)n4 - vector3d.x;
        double d8 = (double)n5 - vector3d.z;
        if (d >= 0.0) {
            d7 += 1.0;
        }
        if (d2 >= 0.0) {
            d8 += 1.0;
        }
        d7 /= d;
        d8 /= d2;
        int n6 = d < 0.0 ? -1 : 1;
        int n7 = d2 < 0.0 ? -1 : 1;
        int n8 = MathHelper.floor(vector3d2.x);
        int n9 = MathHelper.floor(vector3d2.z);
        int n10 = n8 - n4;
        int n11 = n9 - n5;
        while (n10 * n6 > 0 || n11 * n7 > 0) {
            if (d7 < d8) {
                d7 += d5;
                n10 = n8 - (n4 += n6);
            } else {
                d8 += d6;
                n11 = n9 - (n5 += n7);
            }
            if (this.isSafeToStandAt(n4, MathHelper.floor(vector3d.y), n5, n, n2, n3, vector3d, d, d2)) continue;
            return true;
        }
        return false;
    }

    private boolean isSafeToStandAt(int n, int n2, int n3, int n4, int n5, int n6, Vector3d vector3d, double d, double d2) {
        int n7 = n - n4 / 2;
        int n8 = n3 - n6 / 2;
        if (!this.isPositionClear(n7, n2, n8, n4, n5, n6, vector3d, d, d2)) {
            return true;
        }
        for (int i = n7; i < n7 + n4; ++i) {
            for (int j = n8; j < n8 + n6; ++j) {
                double d3 = (double)i + 0.5 - vector3d.x;
                double d4 = (double)j + 0.5 - vector3d.z;
                if (d3 * d + d4 * d2 < 0.0) continue;
                PathNodeType pathNodeType = this.nodeProcessor.getPathNodeType(this.world, i, n2 - 1, j, this.entity, n4, n5, n6, true, false);
                if (!this.func_230287_a_(pathNodeType)) {
                    return true;
                }
                pathNodeType = this.nodeProcessor.getPathNodeType(this.world, i, n2, j, this.entity, n4, n5, n6, true, false);
                float f = this.entity.getPathPriority(pathNodeType);
                if (f < 0.0f || f >= 8.0f) {
                    return true;
                }
                if (pathNodeType != PathNodeType.DAMAGE_FIRE && pathNodeType != PathNodeType.DANGER_FIRE && pathNodeType != PathNodeType.DAMAGE_OTHER) continue;
                return true;
            }
        }
        return false;
    }

    protected boolean func_230287_a_(PathNodeType pathNodeType) {
        if (pathNodeType == PathNodeType.WATER) {
            return true;
        }
        if (pathNodeType == PathNodeType.LAVA) {
            return true;
        }
        return pathNodeType != PathNodeType.OPEN;
    }

    private boolean isPositionClear(int n, int n2, int n3, int n4, int n5, int n6, Vector3d vector3d, double d, double d2) {
        for (BlockPos blockPos : BlockPos.getAllInBoxMutable(new BlockPos(n, n2, n3), new BlockPos(n + n4 - 1, n2 + n5 - 1, n3 + n6 - 1))) {
            double d3;
            double d4 = (double)blockPos.getX() + 0.5 - vector3d.x;
            if (d4 * d + (d3 = (double)blockPos.getZ() + 0.5 - vector3d.z) * d2 < 0.0 || this.world.getBlockState(blockPos).allowsMovement(this.world, blockPos, PathType.LAND)) continue;
            return true;
        }
        return false;
    }

    public void setBreakDoors(boolean bl) {
        this.nodeProcessor.setCanOpenDoors(bl);
    }

    public boolean getEnterDoors() {
        return this.nodeProcessor.getCanEnterDoors();
    }

    public void setAvoidSun(boolean bl) {
        this.shouldAvoidSun = bl;
    }
}

