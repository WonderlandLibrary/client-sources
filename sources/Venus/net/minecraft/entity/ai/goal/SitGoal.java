/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.TameableEntity;

public class SitGoal
extends Goal {
    private final TameableEntity tameable;

    public SitGoal(TameableEntity tameableEntity) {
        this.tameable = tameableEntity;
        this.setMutexFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
    }

    @Override
    public boolean shouldContinueExecuting() {
        return this.tameable.isSitting();
    }

    @Override
    public boolean shouldExecute() {
        if (!this.tameable.isTamed()) {
            return true;
        }
        if (this.tameable.isInWaterOrBubbleColumn()) {
            return true;
        }
        if (!this.tameable.isOnGround()) {
            return true;
        }
        LivingEntity livingEntity = this.tameable.getOwner();
        if (livingEntity == null) {
            return false;
        }
        return this.tameable.getDistanceSq(livingEntity) < 144.0 && livingEntity.getRevengeTarget() != null ? false : this.tameable.isSitting();
    }

    @Override
    public void startExecuting() {
        this.tameable.getNavigator().clearPath();
        this.tameable.setSleeping(false);
    }

    @Override
    public void resetTask() {
        this.tameable.setSleeping(true);
    }
}

