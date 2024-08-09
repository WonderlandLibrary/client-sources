/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.goal;

import java.util.List;
import java.util.function.Predicate;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.fish.AbstractGroupFishEntity;

public class FollowSchoolLeaderGoal
extends Goal {
    private final AbstractGroupFishEntity taskOwner;
    private int navigateTimer;
    private int cooldown;

    public FollowSchoolLeaderGoal(AbstractGroupFishEntity abstractGroupFishEntity) {
        this.taskOwner = abstractGroupFishEntity;
        this.cooldown = this.getNewCooldown(abstractGroupFishEntity);
    }

    protected int getNewCooldown(AbstractGroupFishEntity abstractGroupFishEntity) {
        return 200 + abstractGroupFishEntity.getRNG().nextInt(200) % 20;
    }

    @Override
    public boolean shouldExecute() {
        if (this.taskOwner.isGroupLeader()) {
            return true;
        }
        if (this.taskOwner.hasGroupLeader()) {
            return false;
        }
        if (this.cooldown > 0) {
            --this.cooldown;
            return true;
        }
        this.cooldown = this.getNewCooldown(this.taskOwner);
        Predicate<AbstractGroupFishEntity> predicate = FollowSchoolLeaderGoal::lambda$shouldExecute$0;
        List<AbstractGroupFishEntity> list = this.taskOwner.world.getEntitiesWithinAABB(this.taskOwner.getClass(), this.taskOwner.getBoundingBox().grow(8.0, 8.0, 8.0), predicate);
        AbstractGroupFishEntity abstractGroupFishEntity = list.stream().filter(AbstractGroupFishEntity::canGroupGrow).findAny().orElse(this.taskOwner);
        abstractGroupFishEntity.func_212810_a(list.stream().filter(FollowSchoolLeaderGoal::lambda$shouldExecute$1));
        return this.taskOwner.hasGroupLeader();
    }

    @Override
    public boolean shouldContinueExecuting() {
        return this.taskOwner.hasGroupLeader() && this.taskOwner.inRangeOfGroupLeader();
    }

    @Override
    public void startExecuting() {
        this.navigateTimer = 0;
    }

    @Override
    public void resetTask() {
        this.taskOwner.leaveGroup();
    }

    @Override
    public void tick() {
        if (--this.navigateTimer <= 0) {
            this.navigateTimer = 10;
            this.taskOwner.moveToGroupLeader();
        }
    }

    private static boolean lambda$shouldExecute$1(AbstractGroupFishEntity abstractGroupFishEntity) {
        return !abstractGroupFishEntity.hasGroupLeader();
    }

    private static boolean lambda$shouldExecute$0(AbstractGroupFishEntity abstractGroupFishEntity) {
        return abstractGroupFishEntity.canGroupGrow() || !abstractGroupFishEntity.hasGroupLeader();
    }
}

