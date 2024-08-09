/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.BrainUtil;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.network.DebugPacketSender;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.village.PointOfInterestType;
import net.minecraft.world.server.ServerWorld;

public class CreateBabyVillagerTask
extends Task<VillagerEntity> {
    private long duration;

    public CreateBabyVillagerTask() {
        super(ImmutableMap.of(MemoryModuleType.BREED_TARGET, MemoryModuleStatus.VALUE_PRESENT, MemoryModuleType.VISIBLE_MOBS, MemoryModuleStatus.VALUE_PRESENT), 350, 350);
    }

    @Override
    protected boolean shouldExecute(ServerWorld serverWorld, VillagerEntity villagerEntity) {
        return this.canBreed(villagerEntity);
    }

    @Override
    protected boolean shouldContinueExecuting(ServerWorld serverWorld, VillagerEntity villagerEntity, long l) {
        return l <= this.duration && this.canBreed(villagerEntity);
    }

    @Override
    protected void startExecuting(ServerWorld serverWorld, VillagerEntity villagerEntity, long l) {
        AgeableEntity ageableEntity = villagerEntity.getBrain().getMemory(MemoryModuleType.BREED_TARGET).get();
        BrainUtil.lookApproachEachOther(villagerEntity, ageableEntity, 0.5f);
        serverWorld.setEntityState(ageableEntity, (byte)18);
        serverWorld.setEntityState(villagerEntity, (byte)18);
        int n = 275 + villagerEntity.getRNG().nextInt(50);
        this.duration = l + (long)n;
    }

    @Override
    protected void updateTask(ServerWorld serverWorld, VillagerEntity villagerEntity, long l) {
        VillagerEntity villagerEntity2 = (VillagerEntity)villagerEntity.getBrain().getMemory(MemoryModuleType.BREED_TARGET).get();
        if (!(villagerEntity.getDistanceSq(villagerEntity2) > 5.0)) {
            BrainUtil.lookApproachEachOther(villagerEntity, villagerEntity2, 0.5f);
            if (l >= this.duration) {
                villagerEntity.func_223346_ep();
                villagerEntity2.func_223346_ep();
                this.breed(serverWorld, villagerEntity, villagerEntity2);
            } else if (villagerEntity.getRNG().nextInt(35) == 0) {
                serverWorld.setEntityState(villagerEntity2, (byte)12);
                serverWorld.setEntityState(villagerEntity, (byte)12);
            }
        }
    }

    private void breed(ServerWorld serverWorld, VillagerEntity villagerEntity, VillagerEntity villagerEntity2) {
        Optional<BlockPos> optional = this.findHomePosition(serverWorld, villagerEntity);
        if (!optional.isPresent()) {
            serverWorld.setEntityState(villagerEntity2, (byte)13);
            serverWorld.setEntityState(villagerEntity, (byte)13);
        } else {
            Optional<VillagerEntity> optional2 = this.createChild(serverWorld, villagerEntity, villagerEntity2);
            if (optional2.isPresent()) {
                this.setHomePosition(serverWorld, optional2.get(), optional.get());
            } else {
                serverWorld.getPointOfInterestManager().release(optional.get());
                DebugPacketSender.func_218801_c(serverWorld, optional.get());
            }
        }
    }

    @Override
    protected void resetTask(ServerWorld serverWorld, VillagerEntity villagerEntity, long l) {
        villagerEntity.getBrain().removeMemory(MemoryModuleType.BREED_TARGET);
    }

    private boolean canBreed(VillagerEntity villagerEntity) {
        Brain<VillagerEntity> brain = villagerEntity.getBrain();
        Optional<AgeableEntity> optional = brain.getMemory(MemoryModuleType.BREED_TARGET).filter(CreateBabyVillagerTask::lambda$canBreed$0);
        if (!optional.isPresent()) {
            return true;
        }
        return BrainUtil.isCorrectVisibleType(brain, MemoryModuleType.BREED_TARGET, EntityType.VILLAGER) && villagerEntity.canBreed() && optional.get().canBreed();
    }

    private Optional<BlockPos> findHomePosition(ServerWorld serverWorld, VillagerEntity villagerEntity) {
        return serverWorld.getPointOfInterestManager().take(PointOfInterestType.HOME.getPredicate(), arg_0 -> this.lambda$findHomePosition$1(villagerEntity, arg_0), villagerEntity.getPosition(), 48);
    }

    private boolean canReachHomePosition(VillagerEntity villagerEntity, BlockPos blockPos) {
        Path path = villagerEntity.getNavigator().getPathToPos(blockPos, PointOfInterestType.HOME.getValidRange());
        return path != null && path.reachesTarget();
    }

    private Optional<VillagerEntity> createChild(ServerWorld serverWorld, VillagerEntity villagerEntity, VillagerEntity villagerEntity2) {
        VillagerEntity villagerEntity3 = villagerEntity.func_241840_a(serverWorld, villagerEntity2);
        if (villagerEntity3 == null) {
            return Optional.empty();
        }
        villagerEntity.setGrowingAge(6000);
        villagerEntity2.setGrowingAge(6000);
        villagerEntity3.setGrowingAge(-24000);
        villagerEntity3.setLocationAndAngles(villagerEntity.getPosX(), villagerEntity.getPosY(), villagerEntity.getPosZ(), 0.0f, 0.0f);
        serverWorld.func_242417_l(villagerEntity3);
        serverWorld.setEntityState(villagerEntity3, (byte)12);
        return Optional.of(villagerEntity3);
    }

    private void setHomePosition(ServerWorld serverWorld, VillagerEntity villagerEntity, BlockPos blockPos) {
        GlobalPos globalPos = GlobalPos.getPosition(serverWorld.getDimensionKey(), blockPos);
        villagerEntity.getBrain().setMemory(MemoryModuleType.HOME, globalPos);
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

    private boolean lambda$findHomePosition$1(VillagerEntity villagerEntity, BlockPos blockPos) {
        return this.canReachHomePosition(villagerEntity, blockPos);
    }

    private static boolean lambda$canBreed$0(AgeableEntity ageableEntity) {
        return ageableEntity.getType() == EntityType.VILLAGER;
    }
}

