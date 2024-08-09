/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropsBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.memory.WalkTarget;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.BoneMealItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPosWrapper;
import net.minecraft.world.server.ServerWorld;

public class BoneMealCropsTask
extends Task<VillagerEntity> {
    private long taskDelay;
    private long taskCooldown;
    private int grownObjects;
    private Optional<BlockPos> growableTarget = Optional.empty();

    public BoneMealCropsTask() {
        super(ImmutableMap.of(MemoryModuleType.LOOK_TARGET, MemoryModuleStatus.VALUE_ABSENT, MemoryModuleType.WALK_TARGET, MemoryModuleStatus.VALUE_ABSENT));
    }

    @Override
    protected boolean shouldExecute(ServerWorld serverWorld, VillagerEntity villagerEntity) {
        if (villagerEntity.ticksExisted % 10 == 0 && (this.taskCooldown == 0L || this.taskCooldown + 160L <= (long)villagerEntity.ticksExisted)) {
            if (villagerEntity.getVillagerInventory().count(Items.BONE_MEAL) <= 0) {
                return true;
            }
            this.growableTarget = this.findGrowablePosition(serverWorld, villagerEntity);
            return this.growableTarget.isPresent();
        }
        return true;
    }

    @Override
    protected boolean shouldContinueExecuting(ServerWorld serverWorld, VillagerEntity villagerEntity, long l) {
        return this.grownObjects < 80 && this.growableTarget.isPresent();
    }

    private Optional<BlockPos> findGrowablePosition(ServerWorld serverWorld, VillagerEntity villagerEntity) {
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        Optional<BlockPos> optional = Optional.empty();
        int n = 0;
        for (int i = -1; i <= 1; ++i) {
            for (int j = -1; j <= 1; ++j) {
                for (int k = -1; k <= 1; ++k) {
                    mutable.setAndOffset(villagerEntity.getPosition(), i, j, k);
                    if (!this.isGrowable(mutable, serverWorld) || serverWorld.rand.nextInt(++n) != 0) continue;
                    optional = Optional.of(mutable.toImmutable());
                }
            }
        }
        return optional;
    }

    private boolean isGrowable(BlockPos blockPos, ServerWorld serverWorld) {
        BlockState blockState = serverWorld.getBlockState(blockPos);
        Block block = blockState.getBlock();
        return block instanceof CropsBlock && !((CropsBlock)block).isMaxAge(blockState);
    }

    @Override
    protected void startExecuting(ServerWorld serverWorld, VillagerEntity villagerEntity, long l) {
        this.updateMemory(villagerEntity);
        villagerEntity.setItemStackToSlot(EquipmentSlotType.MAINHAND, new ItemStack(Items.BONE_MEAL));
        this.taskDelay = l;
        this.grownObjects = 0;
    }

    private void updateMemory(VillagerEntity villagerEntity) {
        this.growableTarget.ifPresent(arg_0 -> BoneMealCropsTask.lambda$updateMemory$0(villagerEntity, arg_0));
    }

    @Override
    protected void resetTask(ServerWorld serverWorld, VillagerEntity villagerEntity, long l) {
        villagerEntity.setItemStackToSlot(EquipmentSlotType.MAINHAND, ItemStack.EMPTY);
        this.taskCooldown = villagerEntity.ticksExisted;
    }

    @Override
    protected void updateTask(ServerWorld serverWorld, VillagerEntity villagerEntity, long l) {
        BlockPos blockPos = this.growableTarget.get();
        if (l >= this.taskDelay && blockPos.withinDistance(villagerEntity.getPositionVec(), 1.0)) {
            ItemStack itemStack = ItemStack.EMPTY;
            Inventory inventory = villagerEntity.getVillagerInventory();
            int n = inventory.getSizeInventory();
            for (int i = 0; i < n; ++i) {
                ItemStack itemStack2 = inventory.getStackInSlot(i);
                if (itemStack2.getItem() != Items.BONE_MEAL) continue;
                itemStack = itemStack2;
                break;
            }
            if (!itemStack.isEmpty() && BoneMealItem.applyBonemeal(itemStack, serverWorld, blockPos)) {
                serverWorld.playEvent(2005, blockPos, 0);
                this.growableTarget = this.findGrowablePosition(serverWorld, villagerEntity);
                this.updateMemory(villagerEntity);
                this.taskDelay = l + 40L;
            }
            ++this.grownObjects;
        }
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

    private static void lambda$updateMemory$0(VillagerEntity villagerEntity, BlockPos blockPos) {
        BlockPosWrapper blockPosWrapper = new BlockPosWrapper(blockPos);
        villagerEntity.getBrain().setMemory(MemoryModuleType.LOOK_TARGET, blockPosWrapper);
        villagerEntity.getBrain().setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(blockPosWrapper, 0.5f, 1));
    }
}

