/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.pathfinding.WalkNodeProcessor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;

public class FollowOwnerGoal
extends Goal {
    private final TameableEntity tameable;
    private LivingEntity owner;
    private final IWorldReader world;
    private final double followSpeed;
    private final PathNavigator navigator;
    private int timeToRecalcPath;
    private final float maxDist;
    private final float minDist;
    private float oldWaterCost;
    private final boolean teleportToLeaves;

    public FollowOwnerGoal(TameableEntity tameableEntity, double d, float f, float f2, boolean bl) {
        this.tameable = tameableEntity;
        this.world = tameableEntity.world;
        this.followSpeed = d;
        this.navigator = tameableEntity.getNavigator();
        this.minDist = f;
        this.maxDist = f2;
        this.teleportToLeaves = bl;
        this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        if (!(tameableEntity.getNavigator() instanceof GroundPathNavigator) && !(tameableEntity.getNavigator() instanceof FlyingPathNavigator)) {
            throw new IllegalArgumentException("Unsupported mob type for FollowOwnerGoal");
        }
    }

    @Override
    public boolean shouldExecute() {
        LivingEntity livingEntity = this.tameable.getOwner();
        if (livingEntity == null) {
            return true;
        }
        if (livingEntity.isSpectator()) {
            return true;
        }
        if (this.tameable.isSitting()) {
            return true;
        }
        if (this.tameable.getDistanceSq(livingEntity) < (double)(this.minDist * this.minDist)) {
            return true;
        }
        this.owner = livingEntity;
        return false;
    }

    @Override
    public boolean shouldContinueExecuting() {
        if (this.navigator.noPath()) {
            return true;
        }
        if (this.tameable.isSitting()) {
            return true;
        }
        return !(this.tameable.getDistanceSq(this.owner) <= (double)(this.maxDist * this.maxDist));
    }

    @Override
    public void startExecuting() {
        this.timeToRecalcPath = 0;
        this.oldWaterCost = this.tameable.getPathPriority(PathNodeType.WATER);
        this.tameable.setPathPriority(PathNodeType.WATER, 0.0f);
    }

    @Override
    public void resetTask() {
        this.owner = null;
        this.navigator.clearPath();
        this.tameable.setPathPriority(PathNodeType.WATER, this.oldWaterCost);
    }

    @Override
    public void tick() {
        this.tameable.getLookController().setLookPositionWithEntity(this.owner, 10.0f, this.tameable.getVerticalFaceSpeed());
        if (--this.timeToRecalcPath <= 0) {
            this.timeToRecalcPath = 10;
            if (!this.tameable.getLeashed() && !this.tameable.isPassenger()) {
                if (this.tameable.getDistanceSq(this.owner) >= 144.0) {
                    this.tryToTeleportNearEntity();
                } else {
                    this.navigator.tryMoveToEntityLiving(this.owner, this.followSpeed);
                }
            }
        }
    }

    private void tryToTeleportNearEntity() {
        BlockPos blockPos = this.owner.getPosition();
        for (int i = 0; i < 10; ++i) {
            int n = this.getRandomNumber(-3, 3);
            int n2 = this.getRandomNumber(-1, 1);
            int n3 = this.getRandomNumber(-3, 3);
            boolean bl = this.tryToTeleportToLocation(blockPos.getX() + n, blockPos.getY() + n2, blockPos.getZ() + n3);
            if (!bl) continue;
            return;
        }
    }

    private boolean tryToTeleportToLocation(int n, int n2, int n3) {
        if (Math.abs((double)n - this.owner.getPosX()) < 2.0 && Math.abs((double)n3 - this.owner.getPosZ()) < 2.0) {
            return true;
        }
        if (!this.isTeleportFriendlyBlock(new BlockPos(n, n2, n3))) {
            return true;
        }
        this.tameable.setLocationAndAngles((double)n + 0.5, n2, (double)n3 + 0.5, this.tameable.rotationYaw, this.tameable.rotationPitch);
        this.navigator.clearPath();
        return false;
    }

    private boolean isTeleportFriendlyBlock(BlockPos blockPos) {
        PathNodeType pathNodeType = WalkNodeProcessor.func_237231_a_(this.world, blockPos.toMutable());
        if (pathNodeType != PathNodeType.WALKABLE) {
            return true;
        }
        BlockState blockState = this.world.getBlockState(blockPos.down());
        if (!this.teleportToLeaves && blockState.getBlock() instanceof LeavesBlock) {
            return true;
        }
        BlockPos blockPos2 = blockPos.subtract(this.tameable.getPosition());
        return this.world.hasNoCollisions(this.tameable, this.tameable.getBoundingBox().offset(blockPos2));
    }

    private int getRandomNumber(int n, int n2) {
        return this.tameable.getRNG().nextInt(n2 - n + 1) + n;
    }
}

