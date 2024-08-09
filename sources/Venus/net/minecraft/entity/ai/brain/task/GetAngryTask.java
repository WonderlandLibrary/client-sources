/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.brain.BrainUtil;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.world.GameRules;
import net.minecraft.world.server.ServerWorld;

public class GetAngryTask<E extends MobEntity>
extends Task<E> {
    public GetAngryTask() {
        super(ImmutableMap.of(MemoryModuleType.ANGRY_AT, MemoryModuleStatus.VALUE_PRESENT));
    }

    @Override
    protected void startExecuting(ServerWorld serverWorld, E e, long l) {
        BrainUtil.getTargetFromMemory(e, MemoryModuleType.ANGRY_AT).ifPresent(arg_0 -> GetAngryTask.lambda$startExecuting$0(serverWorld, e, arg_0));
    }

    @Override
    protected void startExecuting(ServerWorld serverWorld, LivingEntity livingEntity, long l) {
        this.startExecuting(serverWorld, (E)((MobEntity)livingEntity), l);
    }

    private static void lambda$startExecuting$0(ServerWorld serverWorld, MobEntity mobEntity, LivingEntity livingEntity) {
        if (livingEntity.getShouldBeDead() && (livingEntity.getType() != EntityType.PLAYER || serverWorld.getGameRules().getBoolean(GameRules.FORGIVE_DEAD_PLAYERS))) {
            mobEntity.getBrain().removeMemory(MemoryModuleType.ANGRY_AT);
        }
    }
}

