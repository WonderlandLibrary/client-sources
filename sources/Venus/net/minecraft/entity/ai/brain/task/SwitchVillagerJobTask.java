/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.BrainUtil;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.village.PointOfInterestType;
import net.minecraft.world.server.ServerWorld;

public class SwitchVillagerJobTask
extends Task<VillagerEntity> {
    final VillagerProfession field_233929_b_;

    public SwitchVillagerJobTask(VillagerProfession villagerProfession) {
        super(ImmutableMap.of(MemoryModuleType.JOB_SITE, MemoryModuleStatus.VALUE_PRESENT, MemoryModuleType.MOBS, MemoryModuleStatus.VALUE_PRESENT));
        this.field_233929_b_ = villagerProfession;
    }

    @Override
    protected void startExecuting(ServerWorld serverWorld, VillagerEntity villagerEntity, long l) {
        GlobalPos globalPos = villagerEntity.getBrain().getMemory(MemoryModuleType.JOB_SITE).get();
        serverWorld.getPointOfInterestManager().getType(globalPos.getPos()).ifPresent(arg_0 -> this.lambda$startExecuting$1(villagerEntity, globalPos, arg_0));
    }

    private static VillagerEntity func_233932_a_(VillagerEntity villagerEntity, VillagerEntity villagerEntity2) {
        VillagerEntity villagerEntity3;
        VillagerEntity villagerEntity4;
        if (villagerEntity.getXp() > villagerEntity2.getXp()) {
            villagerEntity4 = villagerEntity;
            villagerEntity3 = villagerEntity2;
        } else {
            villagerEntity4 = villagerEntity2;
            villagerEntity3 = villagerEntity;
        }
        villagerEntity3.getBrain().removeMemory(MemoryModuleType.JOB_SITE);
        return villagerEntity4;
    }

    private boolean func_233934_a_(GlobalPos globalPos, PointOfInterestType pointOfInterestType, VillagerEntity villagerEntity) {
        return this.func_233931_a_(villagerEntity) && globalPos.equals(villagerEntity.getBrain().getMemory(MemoryModuleType.JOB_SITE).get()) && this.func_233930_a_(pointOfInterestType, villagerEntity.getVillagerData().getProfession());
    }

    private boolean func_233930_a_(PointOfInterestType pointOfInterestType, VillagerProfession villagerProfession) {
        return villagerProfession.getPointOfInterest().getPredicate().test(pointOfInterestType);
    }

    private boolean func_233931_a_(VillagerEntity villagerEntity) {
        return villagerEntity.getBrain().getMemory(MemoryModuleType.JOB_SITE).isPresent();
    }

    @Override
    protected void startExecuting(ServerWorld serverWorld, LivingEntity livingEntity, long l) {
        this.startExecuting(serverWorld, (VillagerEntity)livingEntity, l);
    }

    private void lambda$startExecuting$1(VillagerEntity villagerEntity, GlobalPos globalPos, PointOfInterestType pointOfInterestType) {
        BrainUtil.getNearbyVillagers(villagerEntity, arg_0 -> this.lambda$startExecuting$0(globalPos, pointOfInterestType, arg_0)).reduce(villagerEntity, SwitchVillagerJobTask::func_233932_a_);
    }

    private boolean lambda$startExecuting$0(GlobalPos globalPos, PointOfInterestType pointOfInterestType, VillagerEntity villagerEntity) {
        return this.func_233934_a_(globalPos, pointOfInterestType, villagerEntity);
    }
}

