/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.memory.WalkTarget;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.EntityPosWrapper;
import net.minecraft.world.server.ServerWorld;

public class TradeTask
extends Task<VillagerEntity> {
    private final float speed;

    public TradeTask(float f) {
        super(ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryModuleStatus.REGISTERED, MemoryModuleType.LOOK_TARGET, MemoryModuleStatus.REGISTERED), Integer.MAX_VALUE);
        this.speed = f;
    }

    @Override
    protected boolean shouldExecute(ServerWorld serverWorld, VillagerEntity villagerEntity) {
        PlayerEntity playerEntity = villagerEntity.getCustomer();
        return villagerEntity.isAlive() && playerEntity != null && !villagerEntity.isInWater() && !villagerEntity.velocityChanged && villagerEntity.getDistanceSq(playerEntity) <= 16.0 && playerEntity.openContainer != null;
    }

    @Override
    protected boolean shouldContinueExecuting(ServerWorld serverWorld, VillagerEntity villagerEntity, long l) {
        return this.shouldExecute(serverWorld, villagerEntity);
    }

    @Override
    protected void startExecuting(ServerWorld serverWorld, VillagerEntity villagerEntity, long l) {
        this.walkAndLookCustomer(villagerEntity);
    }

    @Override
    protected void resetTask(ServerWorld serverWorld, VillagerEntity villagerEntity, long l) {
        Brain<VillagerEntity> brain = villagerEntity.getBrain();
        brain.removeMemory(MemoryModuleType.WALK_TARGET);
        brain.removeMemory(MemoryModuleType.LOOK_TARGET);
    }

    @Override
    protected void updateTask(ServerWorld serverWorld, VillagerEntity villagerEntity, long l) {
        this.walkAndLookCustomer(villagerEntity);
    }

    @Override
    protected boolean isTimedOut(long l) {
        return true;
    }

    private void walkAndLookCustomer(VillagerEntity villagerEntity) {
        Brain<VillagerEntity> brain = villagerEntity.getBrain();
        brain.setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(new EntityPosWrapper(villagerEntity.getCustomer(), false), this.speed, 2));
        brain.setMemory(MemoryModuleType.LOOK_TARGET, new EntityPosWrapper(villagerEntity.getCustomer(), true));
    }

    @Override
    protected boolean shouldExecute(ServerWorld serverWorld, LivingEntity livingEntity) {
        return this.shouldExecute(serverWorld, (VillagerEntity)livingEntity);
    }

    @Override
    protected boolean shouldContinueExecuting(ServerWorld serverWorld, LivingEntity livingEntity, long l) {
        return this.shouldContinueExecuting(serverWorld, (VillagerEntity)livingEntity, l);
    }

    @Override
    protected void resetTask(ServerWorld serverWorld, LivingEntity livingEntity, long l) {
        this.resetTask(serverWorld, (VillagerEntity)livingEntity, l);
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

