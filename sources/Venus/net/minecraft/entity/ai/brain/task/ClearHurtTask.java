/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.PanicTask;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.world.server.ServerWorld;

public class ClearHurtTask
extends Task<VillagerEntity> {
    public ClearHurtTask() {
        super(ImmutableMap.of());
    }

    @Override
    protected void startExecuting(ServerWorld serverWorld, VillagerEntity villagerEntity, long l) {
        boolean bl;
        boolean bl2 = bl = PanicTask.hasBeenHurt(villagerEntity) || PanicTask.hostileNearby(villagerEntity) || ClearHurtTask.isAttackerWithinDistance(villagerEntity);
        if (!bl) {
            villagerEntity.getBrain().removeMemory(MemoryModuleType.HURT_BY);
            villagerEntity.getBrain().removeMemory(MemoryModuleType.HURT_BY_ENTITY);
            villagerEntity.getBrain().updateActivity(serverWorld.getDayTime(), serverWorld.getGameTime());
        }
    }

    private static boolean isAttackerWithinDistance(VillagerEntity villagerEntity) {
        return villagerEntity.getBrain().getMemory(MemoryModuleType.HURT_BY_ENTITY).filter(arg_0 -> ClearHurtTask.lambda$isAttackerWithinDistance$0(villagerEntity, arg_0)).isPresent();
    }

    @Override
    protected void startExecuting(ServerWorld serverWorld, LivingEntity livingEntity, long l) {
        this.startExecuting(serverWorld, (VillagerEntity)livingEntity, l);
    }

    private static boolean lambda$isAttackerWithinDistance$0(VillagerEntity villagerEntity, LivingEntity livingEntity) {
        return livingEntity.getDistanceSq(villagerEntity) <= 36.0;
    }
}

