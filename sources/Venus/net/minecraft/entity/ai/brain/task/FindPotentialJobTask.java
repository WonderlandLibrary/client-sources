/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.BrainUtil;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.schedule.Activity;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.network.DebugPacketSender;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.village.PointOfInterestManager;
import net.minecraft.village.PointOfInterestType;
import net.minecraft.world.server.ServerWorld;

public class FindPotentialJobTask
extends Task<VillagerEntity> {
    final float speed;

    public FindPotentialJobTask(float f) {
        super(ImmutableMap.of(MemoryModuleType.POTENTIAL_JOB_SITE, MemoryModuleStatus.VALUE_PRESENT), 1200);
        this.speed = f;
    }

    @Override
    protected boolean shouldExecute(ServerWorld serverWorld, VillagerEntity villagerEntity) {
        return villagerEntity.getBrain().getTemporaryActivity().map(FindPotentialJobTask::lambda$shouldExecute$0).orElse(true);
    }

    @Override
    protected boolean shouldContinueExecuting(ServerWorld serverWorld, VillagerEntity villagerEntity, long l) {
        return villagerEntity.getBrain().hasMemory(MemoryModuleType.POTENTIAL_JOB_SITE);
    }

    @Override
    protected void updateTask(ServerWorld serverWorld, VillagerEntity villagerEntity, long l) {
        BrainUtil.setTargetPosition(villagerEntity, villagerEntity.getBrain().getMemory(MemoryModuleType.POTENTIAL_JOB_SITE).get().getPos(), this.speed, 1);
    }

    @Override
    protected void resetTask(ServerWorld serverWorld, VillagerEntity villagerEntity, long l) {
        Optional<GlobalPos> optional = villagerEntity.getBrain().getMemory(MemoryModuleType.POTENTIAL_JOB_SITE);
        optional.ifPresent(arg_0 -> FindPotentialJobTask.lambda$resetTask$2(serverWorld, arg_0));
        villagerEntity.getBrain().removeMemory(MemoryModuleType.POTENTIAL_JOB_SITE);
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

    private static void lambda$resetTask$2(ServerWorld serverWorld, GlobalPos globalPos) {
        BlockPos blockPos = globalPos.getPos();
        ServerWorld serverWorld2 = serverWorld.getServer().getWorld(globalPos.getDimension());
        if (serverWorld2 != null) {
            PointOfInterestManager pointOfInterestManager = serverWorld2.getPointOfInterestManager();
            if (pointOfInterestManager.exists(blockPos, FindPotentialJobTask::lambda$resetTask$1)) {
                pointOfInterestManager.release(blockPos);
            }
            DebugPacketSender.func_218801_c(serverWorld, blockPos);
        }
    }

    private static boolean lambda$resetTask$1(PointOfInterestType pointOfInterestType) {
        return false;
    }

    private static Boolean lambda$shouldExecute$0(Activity activity) {
        return activity == Activity.IDLE || activity == Activity.WORK || activity == Activity.PLAY;
    }
}

