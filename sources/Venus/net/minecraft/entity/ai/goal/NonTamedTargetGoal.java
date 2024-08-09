/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.goal;

import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.passive.TameableEntity;

public class NonTamedTargetGoal<T extends LivingEntity>
extends NearestAttackableTargetGoal<T> {
    private final TameableEntity tameable;

    public NonTamedTargetGoal(TameableEntity tameableEntity, Class<T> clazz, boolean bl, @Nullable Predicate<LivingEntity> predicate) {
        super(tameableEntity, clazz, 10, bl, false, predicate);
        this.tameable = tameableEntity;
    }

    @Override
    public boolean shouldExecute() {
        return !this.tameable.isTamed() && super.shouldExecute();
    }

    @Override
    public boolean shouldContinueExecuting() {
        return this.targetEntitySelector != null ? this.targetEntitySelector.canTarget(this.goalOwner, this.nearestTarget) : super.shouldContinueExecuting();
    }
}

