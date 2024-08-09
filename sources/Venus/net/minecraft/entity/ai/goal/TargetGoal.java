/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.goal;

import javax.annotation.Nullable;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.math.MathHelper;

public abstract class TargetGoal
extends Goal {
    protected final MobEntity goalOwner;
    protected final boolean shouldCheckSight;
    private final boolean nearbyOnly;
    private int targetSearchStatus;
    private int targetSearchDelay;
    private int targetUnseenTicks;
    protected LivingEntity target;
    protected int unseenMemoryTicks = 60;

    public TargetGoal(MobEntity mobEntity, boolean bl) {
        this(mobEntity, bl, false);
    }

    public TargetGoal(MobEntity mobEntity, boolean bl, boolean bl2) {
        this.goalOwner = mobEntity;
        this.shouldCheckSight = bl;
        this.nearbyOnly = bl2;
    }

    @Override
    public boolean shouldContinueExecuting() {
        LivingEntity livingEntity = this.goalOwner.getAttackTarget();
        if (livingEntity == null) {
            livingEntity = this.target;
        }
        if (livingEntity == null) {
            return true;
        }
        if (!livingEntity.isAlive()) {
            return true;
        }
        Team team = this.goalOwner.getTeam();
        Team team2 = livingEntity.getTeam();
        if (team != null && team2 == team) {
            return true;
        }
        double d = this.getTargetDistance();
        if (this.goalOwner.getDistanceSq(livingEntity) > d * d) {
            return true;
        }
        if (this.shouldCheckSight) {
            if (this.goalOwner.getEntitySenses().canSee(livingEntity)) {
                this.targetUnseenTicks = 0;
            } else if (++this.targetUnseenTicks > this.unseenMemoryTicks) {
                return true;
            }
        }
        if (livingEntity instanceof PlayerEntity && ((PlayerEntity)livingEntity).abilities.disableDamage) {
            return true;
        }
        this.goalOwner.setAttackTarget(livingEntity);
        return false;
    }

    protected double getTargetDistance() {
        return this.goalOwner.getAttributeValue(Attributes.FOLLOW_RANGE);
    }

    @Override
    public void startExecuting() {
        this.targetSearchStatus = 0;
        this.targetSearchDelay = 0;
        this.targetUnseenTicks = 0;
    }

    @Override
    public void resetTask() {
        this.goalOwner.setAttackTarget(null);
        this.target = null;
    }

    protected boolean isSuitableTarget(@Nullable LivingEntity livingEntity, EntityPredicate entityPredicate) {
        if (livingEntity == null) {
            return true;
        }
        if (!entityPredicate.canTarget(this.goalOwner, livingEntity)) {
            return true;
        }
        if (!this.goalOwner.isWithinHomeDistanceFromPosition(livingEntity.getPosition())) {
            return true;
        }
        if (this.nearbyOnly) {
            if (--this.targetSearchDelay <= 0) {
                this.targetSearchStatus = 0;
            }
            if (this.targetSearchStatus == 0) {
                int n = this.targetSearchStatus = this.canEasilyReach(livingEntity) ? 1 : 2;
            }
            if (this.targetSearchStatus == 2) {
                return true;
            }
        }
        return false;
    }

    private boolean canEasilyReach(LivingEntity livingEntity) {
        int n;
        this.targetSearchDelay = 10 + this.goalOwner.getRNG().nextInt(5);
        Path path = this.goalOwner.getNavigator().getPathToEntity(livingEntity, 0);
        if (path == null) {
            return true;
        }
        PathPoint pathPoint = path.getFinalPathPoint();
        if (pathPoint == null) {
            return true;
        }
        int n2 = pathPoint.x - MathHelper.floor(livingEntity.getPosX());
        return (double)(n2 * n2 + (n = pathPoint.z - MathHelper.floor(livingEntity.getPosZ())) * n) <= 2.25;
    }

    public TargetGoal setUnseenMemoryTicks(int n) {
        this.unseenMemoryTicks = n;
        return this;
    }
}

