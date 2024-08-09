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
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.network.DebugPacketSender;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.village.PointOfInterestType;
import net.minecraft.world.server.ServerWorld;

public class FindJobTask
extends Task<VillagerEntity> {
    private final float field_234017_b_;

    public FindJobTask(float f) {
        super(ImmutableMap.of(MemoryModuleType.POTENTIAL_JOB_SITE, MemoryModuleStatus.VALUE_PRESENT, MemoryModuleType.JOB_SITE, MemoryModuleStatus.VALUE_ABSENT, MemoryModuleType.MOBS, MemoryModuleStatus.VALUE_PRESENT));
        this.field_234017_b_ = f;
    }

    @Override
    protected boolean shouldExecute(ServerWorld serverWorld, VillagerEntity villagerEntity) {
        if (villagerEntity.isChild()) {
            return true;
        }
        return villagerEntity.getVillagerData().getProfession() == VillagerProfession.NONE;
    }

    @Override
    protected void startExecuting(ServerWorld serverWorld, VillagerEntity villagerEntity, long l) {
        BlockPos blockPos = villagerEntity.getBrain().getMemory(MemoryModuleType.POTENTIAL_JOB_SITE).get().getPos();
        Optional<PointOfInterestType> optional = serverWorld.getPointOfInterestManager().getType(blockPos);
        if (optional.isPresent()) {
            BrainUtil.getNearbyVillagers(villagerEntity, arg_0 -> this.lambda$startExecuting$0(optional, blockPos, arg_0)).findFirst().ifPresent(arg_0 -> this.lambda$startExecuting$1(serverWorld, villagerEntity, blockPos, arg_0));
        }
    }

    private boolean func_234018_a_(PointOfInterestType pointOfInterestType, VillagerEntity villagerEntity, BlockPos blockPos) {
        boolean bl = villagerEntity.getBrain().getMemory(MemoryModuleType.POTENTIAL_JOB_SITE).isPresent();
        if (bl) {
            return true;
        }
        Optional<GlobalPos> optional = villagerEntity.getBrain().getMemory(MemoryModuleType.JOB_SITE);
        VillagerProfession villagerProfession = villagerEntity.getVillagerData().getProfession();
        if (villagerEntity.getVillagerData().getProfession() != VillagerProfession.NONE && villagerProfession.getPointOfInterest().getPredicate().test(pointOfInterestType)) {
            return !optional.isPresent() ? this.func_234020_a_(villagerEntity, blockPos, pointOfInterestType) : optional.get().getPos().equals(blockPos);
        }
        return true;
    }

    private void func_234022_a_(ServerWorld serverWorld, VillagerEntity villagerEntity, VillagerEntity villagerEntity2, BlockPos blockPos, boolean bl) {
        this.func_234019_a_(villagerEntity);
        if (!bl) {
            BrainUtil.setTargetPosition(villagerEntity2, blockPos, this.field_234017_b_, 1);
            villagerEntity2.getBrain().setMemory(MemoryModuleType.POTENTIAL_JOB_SITE, GlobalPos.getPosition(serverWorld.getDimensionKey(), blockPos));
            DebugPacketSender.func_218801_c(serverWorld, blockPos);
        }
    }

    private boolean func_234020_a_(VillagerEntity villagerEntity, BlockPos blockPos, PointOfInterestType pointOfInterestType) {
        Path path = villagerEntity.getNavigator().getPathToPos(blockPos, pointOfInterestType.getValidRange());
        return path != null && path.reachesTarget();
    }

    private void func_234019_a_(VillagerEntity villagerEntity) {
        villagerEntity.getBrain().removeMemory(MemoryModuleType.WALK_TARGET);
        villagerEntity.getBrain().removeMemory(MemoryModuleType.LOOK_TARGET);
        villagerEntity.getBrain().removeMemory(MemoryModuleType.POTENTIAL_JOB_SITE);
    }

    @Override
    protected boolean shouldExecute(ServerWorld serverWorld, LivingEntity livingEntity) {
        return this.shouldExecute(serverWorld, (VillagerEntity)livingEntity);
    }

    @Override
    protected void startExecuting(ServerWorld serverWorld, LivingEntity livingEntity, long l) {
        this.startExecuting(serverWorld, (VillagerEntity)livingEntity, l);
    }

    private void lambda$startExecuting$1(ServerWorld serverWorld, VillagerEntity villagerEntity, BlockPos blockPos, VillagerEntity villagerEntity2) {
        this.func_234022_a_(serverWorld, villagerEntity, villagerEntity2, blockPos, villagerEntity2.getBrain().getMemory(MemoryModuleType.JOB_SITE).isPresent());
    }

    private boolean lambda$startExecuting$0(Optional optional, BlockPos blockPos, VillagerEntity villagerEntity) {
        return this.func_234018_a_((PointOfInterestType)optional.get(), villagerEntity, blockPos);
    }
}

