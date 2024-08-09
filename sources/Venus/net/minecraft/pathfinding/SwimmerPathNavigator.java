/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.pathfinding;

import net.minecraft.entity.MobEntity;
import net.minecraft.entity.passive.DolphinEntity;
import net.minecraft.network.DebugPacketSender;
import net.minecraft.pathfinding.PathFinder;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.SwimNodeProcessor;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.World;

public class SwimmerPathNavigator
extends PathNavigator {
    private boolean field_205155_i;

    public SwimmerPathNavigator(MobEntity mobEntity, World world) {
        super(mobEntity, world);
    }

    @Override
    protected PathFinder getPathFinder(int n) {
        this.field_205155_i = this.entity instanceof DolphinEntity;
        this.nodeProcessor = new SwimNodeProcessor(this.field_205155_i);
        return new PathFinder(this.nodeProcessor, n);
    }

    @Override
    protected boolean canNavigate() {
        return this.field_205155_i || this.isInLiquid();
    }

    @Override
    protected Vector3d getEntityPosition() {
        return new Vector3d(this.entity.getPosX(), this.entity.getPosYHeight(0.5), this.entity.getPosZ());
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
    protected void pathFollow() {
        if (this.currentPath != null) {
            Vector3d vector3d = this.getEntityPosition();
            float f = this.entity.getWidth();
            float f2 = f > 0.75f ? f / 2.0f : 0.75f - f / 2.0f;
            Vector3d vector3d2 = this.entity.getMotion();
            if (Math.abs(vector3d2.x) > 0.2 || Math.abs(vector3d2.z) > 0.2) {
                f2 = (float)((double)f2 * vector3d2.length() * 6.0);
            }
            int n = 6;
            Vector3d vector3d3 = Vector3d.copyCenteredHorizontally(this.currentPath.func_242948_g());
            if (Math.abs(this.entity.getPosX() - vector3d3.x) < (double)f2 && Math.abs(this.entity.getPosZ() - vector3d3.z) < (double)f2 && Math.abs(this.entity.getPosY() - vector3d3.y) < (double)(f2 * 2.0f)) {
                this.currentPath.incrementPathIndex();
            }
            for (int i = Math.min(this.currentPath.getCurrentPathIndex() + 6, this.currentPath.getCurrentPathLength() - 1); i > this.currentPath.getCurrentPathIndex(); --i) {
                vector3d3 = this.currentPath.getVectorFromIndex(this.entity, i);
                if (vector3d3.squareDistanceTo(vector3d) > 36.0 || !this.isDirectPathBetweenPoints(vector3d, vector3d3, 0, 0, 1)) continue;
                this.currentPath.setCurrentPathIndex(i);
                break;
            }
            this.checkForStuck(vector3d);
        }
    }

    @Override
    protected void checkForStuck(Vector3d vector3d) {
        if (this.totalTicks - this.ticksAtLastPos > 100) {
            if (vector3d.squareDistanceTo(this.lastPosCheck) < 2.25) {
                this.clearPath();
            }
            this.ticksAtLastPos = this.totalTicks;
            this.lastPosCheck = vector3d;
        }
        if (this.currentPath != null && !this.currentPath.isFinished()) {
            BlockPos blockPos = this.currentPath.func_242948_g();
            if (blockPos.equals(this.timeoutCachedNode)) {
                this.timeoutTimer += Util.milliTime() - this.lastTimeoutCheck;
            } else {
                this.timeoutCachedNode = blockPos;
                double d = vector3d.distanceTo(Vector3d.copyCentered(this.timeoutCachedNode));
                double d2 = this.timeoutLimit = this.entity.getAIMoveSpeed() > 0.0f ? d / (double)this.entity.getAIMoveSpeed() * 100.0 : 0.0;
            }
            if (this.timeoutLimit > 0.0 && (double)this.timeoutTimer > this.timeoutLimit * 2.0) {
                this.timeoutCachedNode = Vector3i.NULL_VECTOR;
                this.timeoutTimer = 0L;
                this.timeoutLimit = 0.0;
                this.clearPath();
            }
            this.lastTimeoutCheck = Util.milliTime();
        }
    }

    @Override
    protected boolean isDirectPathBetweenPoints(Vector3d vector3d, Vector3d vector3d2, int n, int n2, int n3) {
        Vector3d vector3d3 = new Vector3d(vector3d2.x, vector3d2.y + (double)this.entity.getHeight() * 0.5, vector3d2.z);
        return this.world.rayTraceBlocks(new RayTraceContext(vector3d, vector3d3, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, this.entity)).getType() == RayTraceResult.Type.MISS;
    }

    @Override
    public boolean canEntityStandOnPos(BlockPos blockPos) {
        return !this.world.getBlockState(blockPos).isOpaqueCube(this.world, blockPos);
    }

    @Override
    public void setCanSwim(boolean bl) {
    }
}

