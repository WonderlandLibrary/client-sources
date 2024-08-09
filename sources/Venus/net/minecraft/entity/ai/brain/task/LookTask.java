/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.util.math.IPosWrapper;
import net.minecraft.world.server.ServerWorld;

public class LookTask
extends Task<MobEntity> {
    public LookTask(int n, int n2) {
        super(ImmutableMap.of(MemoryModuleType.LOOK_TARGET, MemoryModuleStatus.VALUE_PRESENT), n, n2);
    }

    @Override
    protected boolean shouldContinueExecuting(ServerWorld serverWorld, MobEntity mobEntity, long l) {
        return mobEntity.getBrain().getMemory(MemoryModuleType.LOOK_TARGET).filter(arg_0 -> LookTask.lambda$shouldContinueExecuting$0(mobEntity, arg_0)).isPresent();
    }

    @Override
    protected void resetTask(ServerWorld serverWorld, MobEntity mobEntity, long l) {
        mobEntity.getBrain().removeMemory(MemoryModuleType.LOOK_TARGET);
    }

    @Override
    protected void updateTask(ServerWorld serverWorld, MobEntity mobEntity, long l) {
        mobEntity.getBrain().getMemory(MemoryModuleType.LOOK_TARGET).ifPresent(arg_0 -> LookTask.lambda$updateTask$1(mobEntity, arg_0));
    }

    @Override
    protected boolean shouldContinueExecuting(ServerWorld serverWorld, LivingEntity livingEntity, long l) {
        return this.shouldContinueExecuting(serverWorld, (MobEntity)livingEntity, l);
    }

    @Override
    protected void resetTask(ServerWorld serverWorld, LivingEntity livingEntity, long l) {
        this.resetTask(serverWorld, (MobEntity)livingEntity, l);
    }

    @Override
    protected void updateTask(ServerWorld serverWorld, LivingEntity livingEntity, long l) {
        this.updateTask(serverWorld, (MobEntity)livingEntity, l);
    }

    private static void lambda$updateTask$1(MobEntity mobEntity, IPosWrapper iPosWrapper) {
        mobEntity.getLookController().setLookPosition(iPosWrapper.getPos());
    }

    private static boolean lambda$shouldContinueExecuting$0(MobEntity mobEntity, IPosWrapper iPosWrapper) {
        return iPosWrapper.isVisibleTo(mobEntity);
    }
}

