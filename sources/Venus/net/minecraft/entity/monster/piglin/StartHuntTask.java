/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.monster.piglin;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.monster.HoglinEntity;
import net.minecraft.entity.monster.piglin.PiglinEntity;
import net.minecraft.entity.monster.piglin.PiglinTasks;
import net.minecraft.world.server.ServerWorld;

public class StartHuntTask<E extends PiglinEntity>
extends Task<E> {
    public StartHuntTask() {
        super(ImmutableMap.of(MemoryModuleType.NEAREST_VISIBLE_HUNTABLE_HOGLIN, MemoryModuleStatus.VALUE_PRESENT, MemoryModuleType.ANGRY_AT, MemoryModuleStatus.VALUE_ABSENT, MemoryModuleType.HUNTED_RECENTLY, MemoryModuleStatus.VALUE_ABSENT, MemoryModuleType.NEAREST_VISIBLE_ADULT_PIGLINS, MemoryModuleStatus.REGISTERED));
    }

    @Override
    protected boolean shouldExecute(ServerWorld serverWorld, PiglinEntity piglinEntity) {
        return !piglinEntity.isChild() && !PiglinTasks.func_234508_e_(piglinEntity);
    }

    @Override
    protected void startExecuting(ServerWorld serverWorld, E e, long l) {
        HoglinEntity hoglinEntity = ((PiglinEntity)e).getBrain().getMemory(MemoryModuleType.NEAREST_VISIBLE_HUNTABLE_HOGLIN).get();
        PiglinTasks.func_234497_c_(e, hoglinEntity);
        PiglinTasks.func_234518_h_(e);
        PiglinTasks.func_234487_b_(e, hoglinEntity);
        PiglinTasks.func_234512_f_(e);
    }

    @Override
    protected boolean shouldExecute(ServerWorld serverWorld, LivingEntity livingEntity) {
        return this.shouldExecute(serverWorld, (PiglinEntity)livingEntity);
    }

    @Override
    protected void startExecuting(ServerWorld serverWorld, LivingEntity livingEntity, long l) {
        this.startExecuting(serverWorld, (E)((PiglinEntity)livingEntity), l);
    }
}

