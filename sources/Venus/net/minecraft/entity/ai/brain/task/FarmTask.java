/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CropsBlock;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.memory.WalkTarget;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPosWrapper;
import net.minecraft.world.GameRules;
import net.minecraft.world.server.ServerWorld;

public class FarmTask
extends Task<VillagerEntity> {
    @Nullable
    private BlockPos field_220422_a;
    private long taskCooldown;
    private int idleTime;
    private final List<BlockPos> farmableBlocks = Lists.newArrayList();

    public FarmTask() {
        super(ImmutableMap.of(MemoryModuleType.LOOK_TARGET, MemoryModuleStatus.VALUE_ABSENT, MemoryModuleType.WALK_TARGET, MemoryModuleStatus.VALUE_ABSENT, MemoryModuleType.SECONDARY_JOB_SITE, MemoryModuleStatus.VALUE_PRESENT));
    }

    @Override
    protected boolean shouldExecute(ServerWorld serverWorld, VillagerEntity villagerEntity) {
        if (!serverWorld.getGameRules().getBoolean(GameRules.MOB_GRIEFING)) {
            return true;
        }
        if (villagerEntity.getVillagerData().getProfession() != VillagerProfession.FARMER) {
            return true;
        }
        BlockPos.Mutable mutable = villagerEntity.getPosition().toMutable();
        this.farmableBlocks.clear();
        for (int i = -1; i <= 1; ++i) {
            for (int j = -1; j <= 1; ++j) {
                for (int k = -1; k <= 1; ++k) {
                    mutable.setPos(villagerEntity.getPosX() + (double)i, villagerEntity.getPosY() + (double)j, villagerEntity.getPosZ() + (double)k);
                    if (!this.isValidPosForFarming(mutable, serverWorld)) continue;
                    this.farmableBlocks.add(new BlockPos(mutable));
                }
            }
        }
        this.field_220422_a = this.getNextPosForFarming(serverWorld);
        return this.field_220422_a != null;
    }

    @Nullable
    private BlockPos getNextPosForFarming(ServerWorld serverWorld) {
        return this.farmableBlocks.isEmpty() ? null : this.farmableBlocks.get(serverWorld.getRandom().nextInt(this.farmableBlocks.size()));
    }

    private boolean isValidPosForFarming(BlockPos blockPos, ServerWorld serverWorld) {
        BlockState blockState = serverWorld.getBlockState(blockPos);
        Block block = blockState.getBlock();
        Block block2 = serverWorld.getBlockState(blockPos.down()).getBlock();
        return block instanceof CropsBlock && ((CropsBlock)block).isMaxAge(blockState) || blockState.isAir() && block2 instanceof FarmlandBlock;
    }

    @Override
    protected void startExecuting(ServerWorld serverWorld, VillagerEntity villagerEntity, long l) {
        if (l > this.taskCooldown && this.field_220422_a != null) {
            villagerEntity.getBrain().setMemory(MemoryModuleType.LOOK_TARGET, new BlockPosWrapper(this.field_220422_a));
            villagerEntity.getBrain().setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(new BlockPosWrapper(this.field_220422_a), 0.5f, 1));
        }
    }

    @Override
    protected void resetTask(ServerWorld serverWorld, VillagerEntity villagerEntity, long l) {
        villagerEntity.getBrain().removeMemory(MemoryModuleType.LOOK_TARGET);
        villagerEntity.getBrain().removeMemory(MemoryModuleType.WALK_TARGET);
        this.idleTime = 0;
        this.taskCooldown = l + 40L;
    }

    @Override
    protected void updateTask(ServerWorld serverWorld, VillagerEntity villagerEntity, long l) {
        if (this.field_220422_a == null || this.field_220422_a.withinDistance(villagerEntity.getPositionVec(), 1.0)) {
            if (this.field_220422_a != null && l > this.taskCooldown) {
                BlockState blockState = serverWorld.getBlockState(this.field_220422_a);
                Block block = blockState.getBlock();
                Block block2 = serverWorld.getBlockState(this.field_220422_a.down()).getBlock();
                if (block instanceof CropsBlock && ((CropsBlock)block).isMaxAge(blockState)) {
                    serverWorld.destroyBlock(this.field_220422_a, true, villagerEntity);
                }
                if (blockState.isAir() && block2 instanceof FarmlandBlock && villagerEntity.isFarmItemInInventory()) {
                    Inventory inventory = villagerEntity.getVillagerInventory();
                    for (int i = 0; i < inventory.getSizeInventory(); ++i) {
                        ItemStack itemStack = inventory.getStackInSlot(i);
                        boolean bl = false;
                        if (!itemStack.isEmpty()) {
                            if (itemStack.getItem() == Items.WHEAT_SEEDS) {
                                serverWorld.setBlockState(this.field_220422_a, Blocks.WHEAT.getDefaultState(), 0);
                                bl = true;
                            } else if (itemStack.getItem() == Items.POTATO) {
                                serverWorld.setBlockState(this.field_220422_a, Blocks.POTATOES.getDefaultState(), 0);
                                bl = true;
                            } else if (itemStack.getItem() == Items.CARROT) {
                                serverWorld.setBlockState(this.field_220422_a, Blocks.CARROTS.getDefaultState(), 0);
                                bl = true;
                            } else if (itemStack.getItem() == Items.BEETROOT_SEEDS) {
                                serverWorld.setBlockState(this.field_220422_a, Blocks.BEETROOTS.getDefaultState(), 0);
                                bl = true;
                            }
                        }
                        if (!bl) continue;
                        serverWorld.playSound(null, (double)this.field_220422_a.getX(), (double)this.field_220422_a.getY(), this.field_220422_a.getZ(), SoundEvents.ITEM_CROP_PLANT, SoundCategory.BLOCKS, 1.0f, 1.0f);
                        itemStack.shrink(1);
                        if (!itemStack.isEmpty()) break;
                        inventory.setInventorySlotContents(i, ItemStack.EMPTY);
                        break;
                    }
                }
                if (block instanceof CropsBlock && !((CropsBlock)block).isMaxAge(blockState)) {
                    this.farmableBlocks.remove(this.field_220422_a);
                    this.field_220422_a = this.getNextPosForFarming(serverWorld);
                    if (this.field_220422_a != null) {
                        this.taskCooldown = l + 20L;
                        villagerEntity.getBrain().setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(new BlockPosWrapper(this.field_220422_a), 0.5f, 1));
                        villagerEntity.getBrain().setMemory(MemoryModuleType.LOOK_TARGET, new BlockPosWrapper(this.field_220422_a));
                    }
                }
            }
            ++this.idleTime;
        }
    }

    @Override
    protected boolean shouldContinueExecuting(ServerWorld serverWorld, VillagerEntity villagerEntity, long l) {
        return this.idleTime < 200;
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

