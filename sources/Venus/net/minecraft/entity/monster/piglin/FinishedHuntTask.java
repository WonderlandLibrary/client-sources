/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.monster.piglin;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.monster.piglin.PiglinEntity;
import net.minecraft.entity.monster.piglin.PiglinTasks;
import net.minecraft.world.server.ServerWorld;

public class FinishedHuntTask<E extends PiglinEntity>
extends Task<E> {
    public FinishedHuntTask() {
        super(ImmutableMap.of(MemoryModuleType.ATTACK_TARGET, MemoryModuleStatus.VALUE_PRESENT, MemoryModuleType.HUNTED_RECENTLY, MemoryModuleStatus.REGISTERED));
    }

    @Override
    protected void startExecuting(ServerWorld serverWorld, E e, long l) {
        if (this.func_234539_a_(e)) {
            PiglinTasks.func_234518_h_(e);
        }
    }

    private boolean func_234539_a_(E e) {
        LivingEntity livingEntity = ((PiglinEntity)e).getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).get();
        return livingEntity.getType() == EntityType.HOGLIN && livingEntity.getShouldBeDead();
    }

    @Override
    protected void startExecuting(ServerWorld serverWorld, LivingEntity livingEntity, long l) {
        this.startExecuting(serverWorld, (E)((PiglinEntity)livingEntity), l);
    }
}

