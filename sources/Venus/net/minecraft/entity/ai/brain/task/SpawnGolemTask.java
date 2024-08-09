/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.util.math.BlockPosWrapper;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.world.server.ServerWorld;

public class SpawnGolemTask
extends Task<VillagerEntity> {
    private long field_225461_a;

    public SpawnGolemTask() {
        super(ImmutableMap.of(MemoryModuleType.JOB_SITE, MemoryModuleStatus.VALUE_PRESENT, MemoryModuleType.LOOK_TARGET, MemoryModuleStatus.REGISTERED));
    }

    @Override
    protected boolean shouldExecute(ServerWorld serverWorld, VillagerEntity villagerEntity) {
        if (serverWorld.getGameTime() - this.field_225461_a < 300L) {
            return true;
        }
        if (serverWorld.rand.nextInt(2) != 0) {
            return true;
        }
        this.field_225461_a = serverWorld.getGameTime();
        GlobalPos globalPos = villagerEntity.getBrain().getMemory(MemoryModuleType.JOB_SITE).get();
        return globalPos.getDimension() == serverWorld.getDimensionKey() && globalPos.getPos().withinDistance(villagerEntity.getPositionVec(), 1.73);
    }

    @Override
    protected void startExecuting(ServerWorld serverWorld, VillagerEntity villagerEntity, long l) {
        Brain<VillagerEntity> brain = villagerEntity.getBrain();
        brain.setMemory(MemoryModuleType.LAST_WORKED_AT_POI, l);
        brain.getMemory(MemoryModuleType.JOB_SITE).ifPresent(arg_0 -> SpawnGolemTask.lambda$startExecuting$0(brain, arg_0));
        villagerEntity.playWorkstationSound();
        this.execute(serverWorld, villagerEntity);
        if (villagerEntity.canResetStock()) {
            villagerEntity.restock();
        }
    }

    protected void execute(ServerWorld serverWorld, VillagerEntity villagerEntity) {
    }

    @Override
    protected boolean shouldContinueExecuting(ServerWorld serverWorld, VillagerEntity villagerEntity, long l) {
        Optional<GlobalPos> optional = villagerEntity.getBrain().getMemory(MemoryModuleType.JOB_SITE);
        if (!optional.isPresent()) {
            return true;
        }
        GlobalPos globalPos = optional.get();
        return globalPos.getDimension() == serverWorld.getDimensionKey() && globalPos.getPos().withinDistance(villagerEntity.getPositionVec(), 1.73);
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
    protected void startExecuting(ServerWorld serverWorld, LivingEntity livingEntity, long l) {
        this.startExecuting(serverWorld, (VillagerEntity)livingEntity, l);
    }

    private static void lambda$startExecuting$0(Brain brain, GlobalPos globalPos) {
        brain.setMemory(MemoryModuleType.LOOK_TARGET, new BlockPosWrapper(globalPos.getPos()));
    }
}

