/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.village.PointOfInterestType;
import net.minecraft.world.server.ServerWorld;

public class AssignProfessionTask
extends Task<VillagerEntity> {
    public AssignProfessionTask() {
        super(ImmutableMap.of(MemoryModuleType.POTENTIAL_JOB_SITE, MemoryModuleStatus.VALUE_PRESENT));
    }

    @Override
    protected boolean shouldExecute(ServerWorld serverWorld, VillagerEntity villagerEntity) {
        BlockPos blockPos = villagerEntity.getBrain().getMemory(MemoryModuleType.POTENTIAL_JOB_SITE).get().getPos();
        return blockPos.withinDistance(villagerEntity.getPositionVec(), 2.0) || villagerEntity.shouldAssignProfessionOnSpawn();
    }

    @Override
    protected void startExecuting(ServerWorld serverWorld, VillagerEntity villagerEntity, long l) {
        GlobalPos globalPos = villagerEntity.getBrain().getMemory(MemoryModuleType.POTENTIAL_JOB_SITE).get();
        villagerEntity.getBrain().removeMemory(MemoryModuleType.POTENTIAL_JOB_SITE);
        villagerEntity.getBrain().setMemory(MemoryModuleType.JOB_SITE, globalPos);
        serverWorld.setEntityState(villagerEntity, (byte)14);
        if (villagerEntity.getVillagerData().getProfession() == VillagerProfession.NONE) {
            MinecraftServer minecraftServer = serverWorld.getServer();
            Optional.ofNullable(minecraftServer.getWorld(globalPos.getDimension())).flatMap(arg_0 -> AssignProfessionTask.lambda$startExecuting$0(globalPos, arg_0)).flatMap(AssignProfessionTask::lambda$startExecuting$2).ifPresent(arg_0 -> AssignProfessionTask.lambda$startExecuting$3(villagerEntity, serverWorld, arg_0));
        }
    }

    @Override
    protected boolean shouldExecute(ServerWorld serverWorld, LivingEntity livingEntity) {
        return this.shouldExecute(serverWorld, (VillagerEntity)livingEntity);
    }

    @Override
    protected void startExecuting(ServerWorld serverWorld, LivingEntity livingEntity, long l) {
        this.startExecuting(serverWorld, (VillagerEntity)livingEntity, l);
    }

    private static void lambda$startExecuting$3(VillagerEntity villagerEntity, ServerWorld serverWorld, VillagerProfession villagerProfession) {
        villagerEntity.setVillagerData(villagerEntity.getVillagerData().withProfession(villagerProfession));
        villagerEntity.resetBrain(serverWorld);
    }

    private static Optional lambda$startExecuting$2(PointOfInterestType pointOfInterestType) {
        return Registry.VILLAGER_PROFESSION.stream().filter(arg_0 -> AssignProfessionTask.lambda$startExecuting$1(pointOfInterestType, arg_0)).findFirst();
    }

    private static boolean lambda$startExecuting$1(PointOfInterestType pointOfInterestType, VillagerProfession villagerProfession) {
        return villagerProfession.getPointOfInterest() == pointOfInterestType;
    }

    private static Optional lambda$startExecuting$0(GlobalPos globalPos, ServerWorld serverWorld) {
        return serverWorld.getPointOfInterestManager().getType(globalPos.getPos());
    }
}

