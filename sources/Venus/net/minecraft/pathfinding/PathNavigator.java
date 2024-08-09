/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.pathfinding;

import com.google.common.collect.ImmutableSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.network.DebugPacketSender;
import net.minecraft.pathfinding.NodeProcessor;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathFinder;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.pathfinding.WalkNodeProcessor;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.Region;
import net.minecraft.world.World;

public abstract class PathNavigator {
    protected final MobEntity entity;
    protected final World world;
    @Nullable
    protected Path currentPath;
    protected double speed;
    protected int totalTicks;
    protected int ticksAtLastPos;
    protected Vector3d lastPosCheck = Vector3d.ZERO;
    protected Vector3i timeoutCachedNode = Vector3i.NULL_VECTOR;
    protected long timeoutTimer;
    protected long lastTimeoutCheck;
    protected double timeoutLimit;
    protected float maxDistanceToWaypoint = 0.5f;
    protected boolean tryUpdatePath;
    protected long lastTimeUpdated;
    protected NodeProcessor nodeProcessor;
    private BlockPos targetPos;
    private int distance;
    private float rangeMultiplier = 1.0f;
    private final PathFinder pathFinder;
    private boolean field_244431_t;

    public PathNavigator(MobEntity mobEntity, World world) {
        this.entity = mobEntity;
        this.world = world;
        int n = MathHelper.floor(mobEntity.getAttributeValue(Attributes.FOLLOW_RANGE) * 16.0);
        this.pathFinder = this.getPathFinder(n);
    }

    public void resetRangeMultiplier() {
        this.rangeMultiplier = 1.0f;
    }

    public void setRangeMultiplier(float f) {
        this.rangeMultiplier = f;
    }

    public BlockPos getTargetPos() {
        return this.targetPos;
    }

    protected abstract PathFinder getPathFinder(int var1);

    public void setSpeed(double d) {
        this.speed = d;
    }

    public boolean canUpdatePathOnTimeout() {
        return this.tryUpdatePath;
    }

    public void updatePath() {
        if (this.world.getGameTime() - this.lastTimeUpdated > 20L) {
            if (this.targetPos != null) {
                this.currentPath = null;
                this.currentPath = this.getPathToPos(this.targetPos, this.distance);
                this.lastTimeUpdated = this.world.getGameTime();
                this.tryUpdatePath = false;
            }
        } else {
            this.tryUpdatePath = true;
        }
    }

    @Nullable
    public final Path getPathToPos(double d, double d2, double d3, int n) {
        return this.getPathToPos(new BlockPos(d, d2, d3), n);
    }

    @Nullable
    public Path pathfind(Stream<BlockPos> stream, int n) {
        return this.pathfind(stream.collect(Collectors.toSet()), 8, false, n);
    }

    @Nullable
    public Path pathfind(Set<BlockPos> set, int n) {
        return this.pathfind(set, 8, false, n);
    }

    @Nullable
    public Path getPathToPos(BlockPos blockPos, int n) {
        return this.pathfind(ImmutableSet.of(blockPos), 8, false, n);
    }

    @Nullable
    public Path getPathToEntity(Entity entity2, int n) {
        return this.pathfind(ImmutableSet.of(entity2.getPosition()), 16, true, n);
    }

    @Nullable
    protected Path pathfind(Set<BlockPos> set, int n, boolean bl, int n2) {
        if (set.isEmpty()) {
            return null;
        }
        if (this.entity.getPosY() < 0.0) {
            return null;
        }
        if (!this.canNavigate()) {
            return null;
        }
        if (this.currentPath != null && !this.currentPath.isFinished() && set.contains(this.targetPos)) {
            return this.currentPath;
        }
        this.world.getProfiler().startSection("pathfind");
        float f = (float)this.entity.getAttributeValue(Attributes.FOLLOW_RANGE);
        BlockPos blockPos = bl ? this.entity.getPosition().up() : this.entity.getPosition();
        int n3 = (int)(f + (float)n);
        Region region = new Region(this.world, blockPos.add(-n3, -n3, -n3), blockPos.add(n3, n3, n3));
        Path path = this.pathFinder.func_227478_a_(region, this.entity, set, f, n2, this.rangeMultiplier);
        this.world.getProfiler().endSection();
        if (path != null && path.getTarget() != null) {
            this.targetPos = path.getTarget();
            this.distance = n2;
            this.resetTimeOut();
        }
        return path;
    }

    public boolean tryMoveToXYZ(double d, double d2, double d3, double d4) {
        return this.setPath(this.getPathToPos(d, d2, d3, 1), d4);
    }

    public boolean tryMoveToEntityLiving(Entity entity2, double d) {
        Path path = this.getPathToEntity(entity2, 1);
        return path != null && this.setPath(path, d);
    }

    public boolean setPath(@Nullable Path path, double d) {
        if (path == null) {
            this.currentPath = null;
            return true;
        }
        if (!path.isSamePath(this.currentPath)) {
            this.currentPath = path;
        }
        if (this.noPath()) {
            return true;
        }
        this.trimPath();
        if (this.currentPath.getCurrentPathLength() <= 0) {
            return true;
        }
        this.speed = d;
        Vector3d vector3d = this.getEntityPosition();
        this.ticksAtLastPos = this.totalTicks;
        this.lastPosCheck = vector3d;
        return false;
    }

    @Nullable
    public Path getPath() {
        return this.currentPath;
    }

    public void tick() {
        ++this.totalTicks;
        if (this.tryUpdatePath) {
            this.updatePath();
        }
        if (!this.noPath()) {
            Object object;
            Vector3d vector3d;
            if (this.canNavigate()) {
                this.pathFollow();
            } else if (this.currentPath != null && !this.currentPath.isFinished()) {
                vector3d = this.getEntityPosition();
                object = this.currentPath.getPosition(this.entity);
                if (vector3d.y > ((Vector3d)object).y && !this.entity.isOnGround() && MathHelper.floor(vector3d.x) == MathHelper.floor(((Vector3d)object).x) && MathHelper.floor(vector3d.z) == MathHelper.floor(((Vector3d)object).z)) {
                    this.currentPath.incrementPathIndex();
                }
            }
            DebugPacketSender.sendPath(this.world, this.entity, this.currentPath, this.maxDistanceToWaypoint);
            if (!this.noPath()) {
                vector3d = this.currentPath.getPosition(this.entity);
                object = new BlockPos(vector3d);
                this.entity.getMoveHelper().setMoveTo(vector3d.x, this.world.getBlockState(((BlockPos)object).down()).isAir() ? vector3d.y : WalkNodeProcessor.getGroundY(this.world, (BlockPos)object), vector3d.z, this.speed);
            }
        }
    }

    protected void pathFollow() {
        boolean bl;
        Vector3d vector3d = this.getEntityPosition();
        this.maxDistanceToWaypoint = this.entity.getWidth() > 0.75f ? this.entity.getWidth() / 2.0f : 0.75f - this.entity.getWidth() / 2.0f;
        BlockPos blockPos = this.currentPath.func_242948_g();
        double d = Math.abs(this.entity.getPosX() - ((double)blockPos.getX() + 0.5));
        double d2 = Math.abs(this.entity.getPosY() - (double)blockPos.getY());
        double d3 = Math.abs(this.entity.getPosZ() - ((double)blockPos.getZ() + 0.5));
        boolean bl2 = bl = d < (double)this.maxDistanceToWaypoint && d3 < (double)this.maxDistanceToWaypoint && d2 < 1.0;
        if (bl || this.entity.func_233660_b_(this.currentPath.func_237225_h_().nodeType) && this.func_234112_b_(vector3d)) {
            this.currentPath.incrementPathIndex();
        }
        this.checkForStuck(vector3d);
    }

    private boolean func_234112_b_(Vector3d vector3d) {
        Vector3d vector3d2;
        if (this.currentPath.getCurrentPathIndex() + 1 >= this.currentPath.getCurrentPathLength()) {
            return true;
        }
        Vector3d vector3d3 = Vector3d.copyCenteredHorizontally(this.currentPath.func_242948_g());
        if (!vector3d.isWithinDistanceOf(vector3d3, 2.0)) {
            return true;
        }
        Vector3d vector3d4 = Vector3d.copyCenteredHorizontally(this.currentPath.func_242947_d(this.currentPath.getCurrentPathIndex() + 1));
        Vector3d vector3d5 = vector3d4.subtract(vector3d3);
        return vector3d5.dotProduct(vector3d2 = vector3d.subtract(vector3d3)) > 0.0;
    }

    protected void checkForStuck(Vector3d vector3d) {
        if (this.totalTicks - this.ticksAtLastPos > 100) {
            if (vector3d.squareDistanceTo(this.lastPosCheck) < 2.25) {
                this.field_244431_t = true;
                this.clearPath();
            } else {
                this.field_244431_t = false;
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
                double d = vector3d.distanceTo(Vector3d.copyCenteredHorizontally(this.timeoutCachedNode));
                double d2 = this.timeoutLimit = this.entity.getAIMoveSpeed() > 0.0f ? d / (double)this.entity.getAIMoveSpeed() * 1000.0 : 0.0;
            }
            if (this.timeoutLimit > 0.0 && (double)this.timeoutTimer > this.timeoutLimit * 3.0) {
                this.func_244427_e();
            }
            this.lastTimeoutCheck = Util.milliTime();
        }
    }

    private void func_244427_e() {
        this.resetTimeOut();
        this.clearPath();
    }

    private void resetTimeOut() {
        this.timeoutCachedNode = Vector3i.NULL_VECTOR;
        this.timeoutTimer = 0L;
        this.timeoutLimit = 0.0;
        this.field_244431_t = false;
    }

    public boolean noPath() {
        return this.currentPath == null || this.currentPath.isFinished();
    }

    public boolean hasPath() {
        return !this.noPath();
    }

    public void clearPath() {
        this.currentPath = null;
    }

    protected abstract Vector3d getEntityPosition();

    protected abstract boolean canNavigate();

    protected boolean isInLiquid() {
        return this.entity.isInWaterOrBubbleColumn() || this.entity.isInLava();
    }

    protected void trimPath() {
        if (this.currentPath != null) {
            for (int i = 0; i < this.currentPath.getCurrentPathLength(); ++i) {
                PathPoint pathPoint = this.currentPath.getPathPointFromIndex(i);
                PathPoint pathPoint2 = i + 1 < this.currentPath.getCurrentPathLength() ? this.currentPath.getPathPointFromIndex(i + 1) : null;
                BlockState blockState = this.world.getBlockState(new BlockPos(pathPoint.x, pathPoint.y, pathPoint.z));
                if (!blockState.isIn(Blocks.CAULDRON)) continue;
                this.currentPath.setPoint(i, pathPoint.cloneMove(pathPoint.x, pathPoint.y + 1, pathPoint.z));
                if (pathPoint2 == null || pathPoint.y < pathPoint2.y) continue;
                this.currentPath.setPoint(i + 1, pathPoint.cloneMove(pathPoint2.x, pathPoint.y + 1, pathPoint2.z));
            }
        }
    }

    protected abstract boolean isDirectPathBetweenPoints(Vector3d var1, Vector3d var2, int var3, int var4, int var5);

    public boolean canEntityStandOnPos(BlockPos blockPos) {
        BlockPos blockPos2 = blockPos.down();
        return this.world.getBlockState(blockPos2).isOpaqueCube(this.world, blockPos2);
    }

    public NodeProcessor getNodeProcessor() {
        return this.nodeProcessor;
    }

    public void setCanSwim(boolean bl) {
        this.nodeProcessor.setCanSwim(bl);
    }

    public boolean getCanSwim() {
        return this.nodeProcessor.getCanSwim();
    }

    public void onUpdateNavigation(BlockPos blockPos) {
        if (this.currentPath != null && !this.currentPath.isFinished() && this.currentPath.getCurrentPathLength() != 0) {
            PathPoint pathPoint = this.currentPath.getFinalPathPoint();
            Vector3d vector3d = new Vector3d(((double)pathPoint.x + this.entity.getPosX()) / 2.0, ((double)pathPoint.y + this.entity.getPosY()) / 2.0, ((double)pathPoint.z + this.entity.getPosZ()) / 2.0);
            if (blockPos.withinDistance(vector3d, (double)(this.currentPath.getCurrentPathLength() - this.currentPath.getCurrentPathIndex()))) {
                this.updatePath();
            }
        }
    }

    public boolean func_244428_t() {
        return this.field_244431_t;
    }
}

