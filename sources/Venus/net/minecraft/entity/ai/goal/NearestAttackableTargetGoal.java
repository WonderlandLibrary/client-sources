/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.goal;

import java.util.EnumSet;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.TargetGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.AxisAlignedBB;

public class NearestAttackableTargetGoal<T extends LivingEntity>
extends TargetGoal {
    protected final Class<T> targetClass;
    protected final int targetChance;
    protected LivingEntity nearestTarget;
    protected EntityPredicate targetEntitySelector;

    public NearestAttackableTargetGoal(MobEntity mobEntity, Class<T> clazz, boolean bl) {
        this(mobEntity, clazz, bl, false);
    }

    public NearestAttackableTargetGoal(MobEntity mobEntity, Class<T> clazz, boolean bl, boolean bl2) {
        this(mobEntity, clazz, 10, bl, bl2, null);
    }

    public NearestAttackableTargetGoal(MobEntity mobEntity, Class<T> clazz, int n, boolean bl, boolean bl2, @Nullable Predicate<LivingEntity> predicate) {
        super(mobEntity, bl, bl2);
        this.targetClass = clazz;
        this.targetChance = n;
        this.setMutexFlags(EnumSet.of(Goal.Flag.TARGET));
        this.targetEntitySelector = new EntityPredicate().setDistance(this.getTargetDistance()).setCustomPredicate(predicate);
    }

    @Override
    public boolean shouldExecute() {
        if (this.targetChance > 0 && this.goalOwner.getRNG().nextInt(this.targetChance) != 0) {
            return true;
        }
        this.findNearestTarget();
        return this.nearestTarget != null;
    }

    protected AxisAlignedBB getTargetableArea(double d) {
        return this.goalOwner.getBoundingBox().grow(d, 4.0, d);
    }

    protected void findNearestTarget() {
        this.nearestTarget = this.targetClass != PlayerEntity.class && this.targetClass != ServerPlayerEntity.class ? this.goalOwner.world.func_225318_b(this.targetClass, this.targetEntitySelector, this.goalOwner, this.goalOwner.getPosX(), this.goalOwner.getPosYEye(), this.goalOwner.getPosZ(), this.getTargetableArea(this.getTargetDistance())) : this.goalOwner.world.getClosestPlayer(this.targetEntitySelector, this.goalOwner, this.goalOwner.getPosX(), this.goalOwner.getPosYEye(), this.goalOwner.getPosZ());
    }

    @Override
    public void startExecuting() {
        this.goalOwner.setAttackTarget(this.nearestTarget);
        super.startExecuting();
    }

    public void setNearestTarget(@Nullable LivingEntity livingEntity) {
        this.nearestTarget = livingEntity;
    }
}

