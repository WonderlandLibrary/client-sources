/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.schedule.Activity;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.world.server.ServerWorld;

public class PanicTask
extends Task<VillagerEntity> {
    public PanicTask() {
        super(ImmutableMap.of());
    }

    @Override
    protected boolean shouldContinueExecuting(ServerWorld serverWorld, VillagerEntity villagerEntity, long l) {
        return PanicTask.hasBeenHurt(villagerEntity) || PanicTask.hostileNearby(villagerEntity);
    }

    @Override
    protected void startExecuting(ServerWorld serverWorld, VillagerEntity villagerEntity, long l) {
        if (PanicTask.hasBeenHurt(villagerEntity) || PanicTask.hostileNearby(villagerEntity)) {
            Brain<VillagerEntity> brain = villagerEntity.getBrain();
            if (!brain.hasActivity(Activity.PANIC)) {
                brain.removeMemory(MemoryModuleType.PATH);
                brain.removeMemory(MemoryModuleType.WALK_TARGET);
                brain.removeMemory(MemoryModuleType.LOOK_TARGET);
                brain.removeMemory(MemoryModuleType.BREED_TARGET);
                brain.removeMemory(MemoryModuleType.INTERACTION_TARGET);
            }
            brain.switchTo(Activity.PANIC);
        }
    }

    @Override
    protected void updateTask(ServerWorld serverWorld, VillagerEntity villagerEntity, long l) {
        if (l % 100L == 0L) {
            villagerEntity.func_242367_a(serverWorld, l, 3);
        }
    }

    public static boolean hostileNearby(LivingEntity livingEntity) {
        return livingEntity.getBrain().hasMemory(MemoryModuleType.NEAREST_HOSTILE);
    }

    public static boolean hasBeenHurt(LivingEntity livingEntity) {
        return livingEntity.getBrain().hasMemory(MemoryModuleType.HURT_BY);
    }

    @Override
    protected boolean shouldContinueExecuting(ServerWorld serverWorld, LivingEntity livingEntity, long l) {
        return this.shouldContinueExecuting(serverWorld, (VillagerEntity)livingEntity, l);
    }

    @Override
    protected void updateTask(ServerWorld serverWorld, LivingEntity livingEntity, long l) {
        this.updateTask(serverWorld, (VillagerEntity)livingEntity, l);
    }

    @Override
    protected void startExecuting(ServerWorld serverWorld, LivingEntity livingEntity, long l) {
        this.startExecuting(serverWorld, (VillagerEntity)livingEntity, l);
    }
}

