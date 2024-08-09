/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.monster.piglin;

import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.monster.piglin.PiglinEntity;
import net.minecraft.world.server.ServerWorld;

public class StopReachingItemTask<E extends PiglinEntity>
extends Task<E> {
    private final int field_242365_b;
    private final int field_242366_c;

    public StopReachingItemTask(int n, int n2) {
        super(ImmutableMap.of(MemoryModuleType.ADMIRING_ITEM, MemoryModuleStatus.VALUE_PRESENT, MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM, MemoryModuleStatus.VALUE_PRESENT, MemoryModuleType.TIME_TRYING_TO_REACH_ADMIRE_ITEM, MemoryModuleStatus.REGISTERED, MemoryModuleType.DISABLE_WALK_TO_ADMIRE_ITEM, MemoryModuleStatus.REGISTERED));
        this.field_242365_b = n;
        this.field_242366_c = n2;
    }

    @Override
    protected boolean shouldExecute(ServerWorld serverWorld, E e) {
        return ((LivingEntity)e).getHeldItemOffhand().isEmpty();
    }

    @Override
    protected void startExecuting(ServerWorld serverWorld, E e, long l) {
        Brain<PiglinEntity> brain = ((PiglinEntity)e).getBrain();
        Optional<Integer> optional = brain.getMemory(MemoryModuleType.TIME_TRYING_TO_REACH_ADMIRE_ITEM);
        if (!optional.isPresent()) {
            brain.setMemory(MemoryModuleType.TIME_TRYING_TO_REACH_ADMIRE_ITEM, 0);
        } else {
            int n = optional.get();
            if (n > this.field_242365_b) {
                brain.removeMemory(MemoryModuleType.ADMIRING_ITEM);
                brain.removeMemory(MemoryModuleType.TIME_TRYING_TO_REACH_ADMIRE_ITEM);
                brain.replaceMemory(MemoryModuleType.DISABLE_WALK_TO_ADMIRE_ITEM, true, this.field_242366_c);
            } else {
                brain.setMemory(MemoryModuleType.TIME_TRYING_TO_REACH_ADMIRE_ITEM, n + 1);
            }
        }
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

