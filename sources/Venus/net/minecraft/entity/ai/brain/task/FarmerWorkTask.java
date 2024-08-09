/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.Optional;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ComposterBlock;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.SpawnGolemTask;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.world.server.ServerWorld;

public class FarmerWorkTask
extends SpawnGolemTask {
    private static final List<Item> field_234014_b_ = ImmutableList.of(Items.WHEAT_SEEDS, Items.BEETROOT_SEEDS);

    @Override
    protected void execute(ServerWorld serverWorld, VillagerEntity villagerEntity) {
        GlobalPos globalPos;
        BlockState blockState;
        Optional<GlobalPos> optional = villagerEntity.getBrain().getMemory(MemoryModuleType.JOB_SITE);
        if (optional.isPresent() && (blockState = serverWorld.getBlockState((globalPos = optional.get()).getPos())).isIn(Blocks.COMPOSTER)) {
            this.bakeBread(villagerEntity);
            this.compost(serverWorld, villagerEntity, globalPos, blockState);
        }
    }

    private void compost(ServerWorld serverWorld, VillagerEntity villagerEntity, GlobalPos globalPos, BlockState blockState) {
        BlockPos blockPos = globalPos.getPos();
        if (blockState.get(ComposterBlock.LEVEL) == 8) {
            blockState = ComposterBlock.empty(blockState, serverWorld, blockPos);
        }
        int n = 20;
        int n2 = 10;
        int[] nArray = new int[field_234014_b_.size()];
        Inventory inventory = villagerEntity.getVillagerInventory();
        int n3 = inventory.getSizeInventory();
        BlockState blockState2 = blockState;
        for (int i = n3 - 1; i >= 0 && n > 0; --i) {
            int n4;
            ItemStack itemStack = inventory.getStackInSlot(i);
            int n5 = field_234014_b_.indexOf(itemStack.getItem());
            if (n5 == -1) continue;
            int n6 = itemStack.getCount();
            nArray[n5] = n4 = nArray[n5] + n6;
            int n7 = Math.min(Math.min(n4 - 10, n), n6);
            if (n7 <= 0) continue;
            n -= n7;
            for (int j = 0; j < n7; ++j) {
                if ((blockState2 = ComposterBlock.attemptFill(blockState2, serverWorld, itemStack, blockPos)).get(ComposterBlock.LEVEL) != 7) continue;
                this.func_242308_a(serverWorld, blockState, blockPos, blockState2);
                return;
            }
        }
        this.func_242308_a(serverWorld, blockState, blockPos, blockState2);
    }

    private void func_242308_a(ServerWorld serverWorld, BlockState blockState, BlockPos blockPos, BlockState blockState2) {
        serverWorld.playEvent(1500, blockPos, blockState2 != blockState ? 1 : 0);
    }

    private void bakeBread(VillagerEntity villagerEntity) {
        Inventory inventory = villagerEntity.getVillagerInventory();
        if (inventory.count(Items.BREAD) <= 36) {
            int n = inventory.count(Items.WHEAT);
            int n2 = 3;
            int n3 = 3;
            int n4 = Math.min(3, n / 3);
            if (n4 != 0) {
                int n5 = n4 * 3;
                inventory.func_223374_a(Items.WHEAT, n5);
                ItemStack itemStack = inventory.addItem(new ItemStack(Items.BREAD, n4));
                if (!itemStack.isEmpty()) {
                    villagerEntity.entityDropItem(itemStack, 0.5f);
                }
            }
        }
    }
}

