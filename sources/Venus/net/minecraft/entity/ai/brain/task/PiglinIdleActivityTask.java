/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import java.util.function.Predicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.util.RangedInteger;
import net.minecraft.world.server.ServerWorld;

public class PiglinIdleActivityTask<E extends MobEntity, T>
extends Task<E> {
    private final Predicate<E> field_233881_b_;
    private final MemoryModuleType<? extends T> field_233882_c_;
    private final MemoryModuleType<T> field_233883_d_;
    private final RangedInteger field_233884_e_;

    public PiglinIdleActivityTask(Predicate<E> predicate, MemoryModuleType<? extends T> memoryModuleType, MemoryModuleType<T> memoryModuleType2, RangedInteger rangedInteger) {
        super(ImmutableMap.of(memoryModuleType, MemoryModuleStatus.VALUE_PRESENT, memoryModuleType2, MemoryModuleStatus.VALUE_ABSENT));
        this.field_233881_b_ = predicate;
        this.field_233882_c_ = memoryModuleType;
        this.field_233883_d_ = memoryModuleType2;
        this.field_233884_e_ = rangedInteger;
    }

    @Override
    protected boolean shouldExecute(ServerWorld serverWorld, E e) {
        return this.field_233881_b_.test(e);
    }

    @Override
    protected void startExecuting(ServerWorld serverWorld, E e, long l) {
        Brain<?> brain = ((LivingEntity)e).getBrain();
        brain.replaceMemory(this.field_233883_d_, brain.getMemory(this.field_233882_c_).get(), this.field_233884_e_.getRandomWithinRange(serverWorld.rand));
    }

    @Override
    protected boolean shouldExecute(ServerWorld serverWorld, LivingEntity livingEntity) {
        return this.shouldExecute(serverWorld, (E)((MobEntity)livingEntity));
    }

    @Override
    protected void startExecuting(ServerWorld serverWorld, LivingEntity livingEntity, long l) {
        this.startExecuting(serverWorld, (E)((MobEntity)livingEntity), l);
    }
}

