/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.goal;

import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.controller.LookController;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.PathNodeType;

public class FollowMobGoal
extends Goal {
    private final MobEntity entity;
    private final Predicate<MobEntity> followPredicate;
    private MobEntity followingEntity;
    private final double speedModifier;
    private final PathNavigator navigation;
    private int timeToRecalcPath;
    private final float stopDistance;
    private float oldWaterCost;
    private final float areaSize;

    public FollowMobGoal(MobEntity mobEntity, double d, float f, float f2) {
        this.entity = mobEntity;
        this.followPredicate = arg_0 -> FollowMobGoal.lambda$new$0(mobEntity, arg_0);
        this.speedModifier = d;
        this.navigation = mobEntity.getNavigator();
        this.stopDistance = f;
        this.areaSize = f2;
        this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        if (!(mobEntity.getNavigator() instanceof GroundPathNavigator) && !(mobEntity.getNavigator() instanceof FlyingPathNavigator)) {
            throw new IllegalArgumentException("Unsupported mob type for FollowMobGoal");
        }
    }

    @Override
    public boolean shouldExecute() {
        List<MobEntity> list = this.entity.world.getEntitiesWithinAABB(MobEntity.class, this.entity.getBoundingBox().grow(this.areaSize), this.followPredicate);
        if (!list.isEmpty()) {
            for (MobEntity mobEntity : list) {
                if (mobEntity.isInvisible()) continue;
                this.followingEntity = mobEntity;
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean shouldContinueExecuting() {
        return this.followingEntity != null && !this.navigation.noPath() && this.entity.getDistanceSq(this.followingEntity) > (double)(this.stopDistance * this.stopDistance);
    }

    @Override
    public void startExecuting() {
        this.timeToRecalcPath = 0;
        this.oldWaterCost = this.entity.getPathPriority(PathNodeType.WATER);
        this.entity.setPathPriority(PathNodeType.WATER, 0.0f);
    }

    @Override
    public void resetTask() {
        this.followingEntity = null;
        this.navigation.clearPath();
        this.entity.setPathPriority(PathNodeType.WATER, this.oldWaterCost);
    }

    @Override
    public void tick() {
        if (this.followingEntity != null && !this.entity.getLeashed()) {
            this.entity.getLookController().setLookPositionWithEntity(this.followingEntity, 10.0f, this.entity.getVerticalFaceSpeed());
            if (--this.timeToRecalcPath <= 0) {
                double d;
                double d2;
                this.timeToRecalcPath = 10;
                double d3 = this.entity.getPosX() - this.followingEntity.getPosX();
                double d4 = d3 * d3 + (d2 = this.entity.getPosY() - this.followingEntity.getPosY()) * d2 + (d = this.entity.getPosZ() - this.followingEntity.getPosZ()) * d;
                if (!(d4 <= (double)(this.stopDistance * this.stopDistance))) {
                    this.navigation.tryMoveToEntityLiving(this.followingEntity, this.speedModifier);
                } else {
                    this.navigation.clearPath();
                    LookController lookController = this.followingEntity.getLookController();
                    if (d4 <= (double)this.stopDistance || lookController.getLookPosX() == this.entity.getPosX() && lookController.getLookPosY() == this.entity.getPosY() && lookController.getLookPosZ() == this.entity.getPosZ()) {
                        double d5 = this.followingEntity.getPosX() - this.entity.getPosX();
                        double d6 = this.followingEntity.getPosZ() - this.entity.getPosZ();
                        this.navigation.tryMoveToXYZ(this.entity.getPosX() - d5, this.entity.getPosY(), this.entity.getPosZ() - d6, this.speedModifier);
                    }
                }
            }
        }
    }

    private static boolean lambda$new$0(MobEntity mobEntity, MobEntity mobEntity2) {
        return mobEntity2 != null && mobEntity.getClass() != mobEntity2.getClass();
    }
}

