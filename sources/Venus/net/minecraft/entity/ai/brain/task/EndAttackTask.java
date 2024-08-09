/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import java.util.function.BiPredicate;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.world.GameRules;
import net.minecraft.world.server.ServerWorld;

public class EndAttackTask
extends Task<LivingEntity> {
    private final int field_233978_b_;
    private final BiPredicate<LivingEntity, LivingEntity> field_233979_c_;

    public EndAttackTask(int n, BiPredicate<LivingEntity, LivingEntity> biPredicate) {
        super(ImmutableMap.of(MemoryModuleType.ATTACK_TARGET, MemoryModuleStatus.VALUE_PRESENT, MemoryModuleType.ANGRY_AT, MemoryModuleStatus.REGISTERED, MemoryModuleType.CELEBRATE_LOCATION, MemoryModuleStatus.VALUE_ABSENT, MemoryModuleType.DANCING, MemoryModuleStatus.REGISTERED));
        this.field_233978_b_ = n;
        this.field_233979_c_ = biPredicate;
    }

    @Override
    protected boolean shouldExecute(ServerWorld serverWorld, LivingEntity livingEntity) {
        return this.getAttackTarget(livingEntity).getShouldBeDead();
    }

    @Override
    protected void startExecuting(ServerWorld serverWorld, LivingEntity livingEntity, long l) {
        LivingEntity livingEntity2 = this.getAttackTarget(livingEntity);
        if (this.field_233979_c_.test(livingEntity, livingEntity2)) {
            livingEntity.getBrain().replaceMemory(MemoryModuleType.DANCING, true, this.field_233978_b_);
        }
        livingEntity.getBrain().replaceMemory(MemoryModuleType.CELEBRATE_LOCATION, livingEntity2.getPosition(), this.field_233978_b_);
        if (livingEntity2.getType() != EntityType.PLAYER || serverWorld.getGameRules().getBoolean(GameRules.FORGIVE_DEAD_PLAYERS)) {
            livingEntity.getBrain().removeMemory(MemoryModuleType.ATTACK_TARGET);
            livingEntity.getBrain().removeMemory(MemoryModuleType.ANGRY_AT);
        }
    }

    private LivingEntity getAttackTarget(LivingEntity livingEntity) {
        return livingEntity.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).get();
    }
}

