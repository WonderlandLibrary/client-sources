/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.pathfinding;

import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.network.DebugPacketSender;
import net.minecraft.pathfinding.FlyingNodeProcessor;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathFinder;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class FlyingPathNavigator
extends PathNavigator {
    public FlyingPathNavigator(MobEntity mobEntity, World world) {
        super(mobEntity, world);
    }

    @Override
    protected PathFinder getPathFinder(int n) {
        this.nodeProcessor = new FlyingNodeProcessor();
        this.nodeProcessor.setCanEnterDoors(false);
        return new PathFinder(this.nodeProcessor, n);
    }

    @Override
    protected boolean canNavigate() {
        return this.getCanSwim() && this.isInLiquid() || !this.entity.isPassenger();
    }

    @Override
    protected Vector3d getEntityPosition() {
        return this.entity.getPositionVec();
    }

    @Override
    public Path getPathToEntity(Entity entity2, int n) {
        return this.getPathToPos(entity2.getPosition(), n);
    }

    @Override
    public void tick() {
        ++this.totalTicks;
        if (this.tryUpdatePath) {
            this.updatePath();
        }
        if (!this.noPath()) {
            Vector3d vector3d;
            if (this.canNavigate()) {
                this.pathFollow();
            } else if (this.currentPath != null && !this.currentPath.isFinished()) {
                vector3d = this.currentPath.getPosition(this.entity);
                if (MathHelper.floor(this.entity.getPosX()) == MathHelper.floor(vector3d.x) && MathHelper.floor(this.entity.getPosY()) == MathHelper.floor(vector3d.y) && MathHelper.floor(this.entity.getPosZ()) == MathHelper.floor(vector3d.z)) {
                    this.currentPath.incrementPathIndex();
                }
            }
            DebugPacketSender.sendPath(this.world, this.entity, this.currentPath, this.maxDistanceToWaypoint);
            if (!this.noPath()) {
                vector3d = this.currentPath.getPosition(this.entity);
                this.entity.getMoveHelper().setMoveTo(vector3d.x, vector3d.y, vector3d.z, this.speed);
            }
        }
    }

    @Override
    protected boolean isDirectPathBetweenPoints(Vector3d vector3d, Vector3d vector3d2, int n, int n2, int n3) {
        int n4 = MathHelper.floor(vector3d.x);
        int n5 = MathHelper.floor(vector3d.y);
        int n6 = MathHelper.floor(vector3d.z);
        double d = vector3d2.x - vector3d.x;
        double d2 = vector3d2.y - vector3d.y;
        double d3 = vector3d2.z - vector3d.z;
        double d4 = d * d + d2 * d2 + d3 * d3;
        if (d4 < 1.0E-8) {
            return true;
        }
        double d5 = 1.0 / Math.sqrt(d4);
        double d6 = 1.0 / Math.abs(d *= d5);
        double d7 = 1.0 / Math.abs(d2 *= d5);
        double d8 = 1.0 / Math.abs(d3 *= d5);
        double d9 = (double)n4 - vector3d.x;
        double d10 = (double)n5 - vector3d.y;
        double d11 = (double)n6 - vector3d.z;
        if (d >= 0.0) {
            d9 += 1.0;
        }
        if (d2 >= 0.0) {
            d10 += 1.0;
        }
        if (d3 >= 0.0) {
            d11 += 1.0;
        }
        d9 /= d;
        d10 /= d2;
        d11 /= d3;
        int n7 = d < 0.0 ? -1 : 1;
        int n8 = d2 < 0.0 ? -1 : 1;
        int n9 = d3 < 0.0 ? -1 : 1;
        int n10 = MathHelper.floor(vector3d2.x);
        int n11 = MathHelper.floor(vector3d2.y);
        int n12 = MathHelper.floor(vector3d2.z);
        int n13 = n10 - n4;
        int n14 = n11 - n5;
        int n15 = n12 - n6;
        while (n13 * n7 > 0 || n14 * n8 > 0 || n15 * n9 > 0) {
            if (d9 < d11 && d9 <= d10) {
                d9 += d6;
                n13 = n10 - (n4 += n7);
                continue;
            }
            if (d10 < d9 && d10 <= d11) {
                d10 += d7;
                n14 = n11 - (n5 += n8);
                continue;
            }
            d11 += d8;
            n15 = n12 - (n6 += n9);
        }
        return false;
    }

    public void setCanOpenDoors(boolean bl) {
        this.nodeProcessor.setCanOpenDoors(bl);
    }

    public void setCanEnterDoors(boolean bl) {
        this.nodeProcessor.setCanEnterDoors(bl);
    }

    @Override
    public boolean canEntityStandOnPos(BlockPos blockPos) {
        return this.world.getBlockState(blockPos).canSpawnMobs(this.world, blockPos, this.entity);
    }
}

