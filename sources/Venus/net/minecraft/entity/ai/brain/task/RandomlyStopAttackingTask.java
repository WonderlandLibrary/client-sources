/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.world.server.ServerWorld;

public class RandomlyStopAttackingTask
extends Task<LivingEntity> {
    private final int field_233858_b_;

    public RandomlyStopAttackingTask(MemoryModuleType<?> memoryModuleType, int n) {
        super(ImmutableMap.of(MemoryModuleType.ATTACK_TARGET, MemoryModuleStatus.REGISTERED, MemoryModuleType.PACIFIED, MemoryModuleStatus.VALUE_ABSENT, memoryModuleType, MemoryModuleStatus.VALUE_PRESENT));
        this.field_233858_b_ = n;
    }

    @Override
    protected void startExecuting(ServerWorld serverWorld, LivingEntity livingEntity, long l) {
        livingEntity.getBrain().replaceMemory(MemoryModuleType.PACIFIED, true, this.field_233858_b_);
        livingEntity.getBrain().removeMemory(MemoryModuleType.ATTACK_TARGET);
    }
}

