/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.BrainUtil;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.server.ServerWorld;

public class ShareItemsTask
extends Task<VillagerEntity> {
    private Set<Item> field_220588_a = ImmutableSet.of();

    public ShareItemsTask() {
        super(ImmutableMap.of(MemoryModuleType.INTERACTION_TARGET, MemoryModuleStatus.VALUE_PRESENT, MemoryModuleType.VISIBLE_MOBS, MemoryModuleStatus.VALUE_PRESENT));
    }

    @Override
    protected boolean shouldExecute(ServerWorld serverWorld, VillagerEntity villagerEntity) {
        return BrainUtil.isCorrectVisibleType(villagerEntity.getBrain(), MemoryModuleType.INTERACTION_TARGET, EntityType.VILLAGER);
    }

    @Override
    protected boolean shouldContinueExecuting(ServerWorld serverWorld, VillagerEntity villagerEntity, long l) {
        return this.shouldExecute(serverWorld, villagerEntity);
    }

    @Override
    protected void startExecuting(ServerWorld serverWorld, VillagerEntity villagerEntity, long l) {
        VillagerEntity villagerEntity2 = (VillagerEntity)villagerEntity.getBrain().getMemory(MemoryModuleType.INTERACTION_TARGET).get();
        BrainUtil.lookApproachEachOther(villagerEntity, villagerEntity2, 0.5f);
        this.field_220588_a = ShareItemsTask.func_220585_a(villagerEntity, villagerEntity2);
    }

    @Override
    protected void updateTask(ServerWorld serverWorld, VillagerEntity villagerEntity, long l) {
        VillagerEntity villagerEntity2 = (VillagerEntity)villagerEntity.getBrain().getMemory(MemoryModuleType.INTERACTION_TARGET).get();
        if (!(villagerEntity.getDistanceSq(villagerEntity2) > 5.0)) {
            BrainUtil.lookApproachEachOther(villagerEntity, villagerEntity2, 0.5f);
            villagerEntity.func_242368_a(serverWorld, villagerEntity2, l);
            if (villagerEntity.canAbondonItems() && (villagerEntity.getVillagerData().getProfession() == VillagerProfession.FARMER || villagerEntity2.wantsMoreFood())) {
                ShareItemsTask.func_220586_a(villagerEntity, VillagerEntity.FOOD_VALUES.keySet(), villagerEntity2);
            }
            if (villagerEntity2.getVillagerData().getProfession() == VillagerProfession.FARMER && villagerEntity.getVillagerInventory().count(Items.WHEAT) > Items.WHEAT.getMaxStackSize() / 2) {
                ShareItemsTask.func_220586_a(villagerEntity, ImmutableSet.of(Items.WHEAT), villagerEntity2);
            }
            if (!this.field_220588_a.isEmpty() && villagerEntity.getVillagerInventory().hasAny(this.field_220588_a)) {
                ShareItemsTask.func_220586_a(villagerEntity, this.field_220588_a, villagerEntity2);
            }
        }
    }

    @Override
    protected void resetTask(ServerWorld serverWorld, VillagerEntity villagerEntity, long l) {
        villagerEntity.getBrain().removeMemory(MemoryModuleType.INTERACTION_TARGET);
    }

    private static Set<Item> func_220585_a(VillagerEntity villagerEntity, VillagerEntity villagerEntity2) {
        ImmutableSet<Item> immutableSet = villagerEntity2.getVillagerData().getProfession().getSpecificItems();
        ImmutableSet<Item> immutableSet2 = villagerEntity.getVillagerData().getProfession().getSpecificItems();
        return immutableSet.stream().filter(arg_0 -> ShareItemsTask.lambda$func_220585_a$0(immutableSet2, arg_0)).collect(Collectors.toSet());
    }

    private static void func_220586_a(VillagerEntity villagerEntity, Set<Item> set, LivingEntity livingEntity) {
        Inventory inventory = villagerEntity.getVillagerInventory();
        ItemStack itemStack = ItemStack.EMPTY;
        for (int i = 0; i < inventory.getSizeInventory(); ++i) {
            int n;
            Item item;
            ItemStack itemStack2 = inventory.getStackInSlot(i);
            if (itemStack2.isEmpty() || !set.contains(item = itemStack2.getItem())) continue;
            if (itemStack2.getCount() > itemStack2.getMaxStackSize() / 2) {
                n = itemStack2.getCount() / 2;
            } else {
                if (itemStack2.getCount() <= 24) continue;
                n = itemStack2.getCount() - 24;
            }
            itemStack2.shrink(n);
            itemStack = new ItemStack(item, n);
            break;
        }
        if (!itemStack.isEmpty()) {
            BrainUtil.spawnItemNearEntity(villagerEntity, itemStack, livingEntity.getPositionVec());
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

    private static boolean lambda$func_220585_a$0(ImmutableSet immutableSet, Item item) {
        return !immutableSet.contains(item);
    }
}

