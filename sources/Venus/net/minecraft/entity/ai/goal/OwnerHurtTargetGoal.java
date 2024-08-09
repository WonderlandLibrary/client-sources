/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.TargetGoal;
import net.minecraft.entity.passive.TameableEntity;

public class OwnerHurtTargetGoal
extends TargetGoal {
    private final TameableEntity tameable;
    private LivingEntity attacker;
    private int timestamp;

    public OwnerHurtTargetGoal(TameableEntity tameableEntity) {
        super(tameableEntity, false);
        this.tameable = tameableEntity;
        this.setMutexFlags(EnumSet.of(Goal.Flag.TARGET));
    }

    @Override
    public boolean shouldExecute() {
        if (this.tameable.isTamed() && !this.tameable.isSitting()) {
            LivingEntity livingEntity = this.tameable.getOwner();
            if (livingEntity == null) {
                return true;
            }
            this.attacker = livingEntity.getLastAttackedEntity();
            int n = livingEntity.getLastAttackedEntityTime();
            return n != this.timestamp && this.isSuitableTarget(this.attacker, EntityPredicate.DEFAULT) && this.tameable.shouldAttackEntity(this.attacker, livingEntity);
        }
        return true;
    }

    @Override
    public void startExecuting() {
        this.goalOwner.setAttackTarget(this.attacker);
        LivingEntity livingEntity = this.tameable.getOwner();
        if (livingEntity != null) {
            this.timestamp = livingEntity.getLastAttackedEntityTime();
        }
        super.startExecuting();
    }
}

