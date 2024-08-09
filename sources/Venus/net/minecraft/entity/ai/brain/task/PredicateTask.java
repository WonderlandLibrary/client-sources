/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import java.util.function.Predicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.world.server.ServerWorld;

public class PredicateTask<E extends LivingEntity>
extends Task<E> {
    private final Predicate<E> field_233895_b_;
    private final MemoryModuleType<?> field_233896_c_;

    public PredicateTask(Predicate<E> predicate, MemoryModuleType<?> memoryModuleType) {
        super(ImmutableMap.of(memoryModuleType, MemoryModuleStatus.VALUE_PRESENT));
        this.field_233895_b_ = predicate;
        this.field_233896_c_ = memoryModuleType;
    }

    @Override
    protected boolean shouldExecute(ServerWorld serverWorld, E e) {
        return this.field_233895_b_.test(e);
    }

    @Override
    protected void startExecuting(ServerWorld serverWorld, E e, long l) {
        ((LivingEntity)e).getBrain().removeMemory(this.field_233896_c_);
    }
}

