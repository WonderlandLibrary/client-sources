/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.monster.piglin;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.piglin.PiglinEntity;
import net.minecraft.entity.monster.piglin.PiglinTasks;
import net.minecraft.world.server.ServerWorld;

public class AdmireItemTask<E extends PiglinEntity>
extends Task<E> {
    private final int field_234540_b_;

    public AdmireItemTask(int n) {
        super(ImmutableMap.of(MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM, MemoryModuleStatus.VALUE_PRESENT, MemoryModuleType.ADMIRING_ITEM, MemoryModuleStatus.VALUE_ABSENT, MemoryModuleType.ADMIRING_DISABLED, MemoryModuleStatus.VALUE_ABSENT, MemoryModuleType.DISABLE_WALK_TO_ADMIRE_ITEM, MemoryModuleStatus.VALUE_ABSENT));
        this.field_234540_b_ = n;
    }

    @Override
    protected boolean shouldExecute(ServerWorld serverWorld, E e) {
        ItemEntity itemEntity = ((PiglinEntity)e).getBrain().getMemory(MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM).get();
        return PiglinTasks.func_234480_a_(itemEntity.getItem().getItem());
    }

    @Override
    protected void startExecuting(ServerWorld serverWorld, E e, long l) {
        ((PiglinEntity)e).getBrain().replaceMemory(MemoryModuleType.ADMIRING_ITEM, true, this.field_234540_b_);
    }

    @Override
    protected boolean shouldExecute(ServerWorld serverWorld, LivingEntity livingEntity) {
        return this.shouldExecute(serverWorld, (E)((PiglinEntity)livingEntity));
    }

    @Override
    protected void startExecuting(ServerWorld serverWorld, LivingEntity livingEntity, long l) {
        this.startExecuting(serverWorld, (E)((PiglinEntity)livingEntity), l);
    }
}

