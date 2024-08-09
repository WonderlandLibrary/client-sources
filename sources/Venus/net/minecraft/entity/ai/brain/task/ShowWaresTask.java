/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MerchantOffer;
import net.minecraft.util.math.EntityPosWrapper;
import net.minecraft.world.server.ServerWorld;

public class ShowWaresTask
extends Task<VillagerEntity> {
    @Nullable
    private ItemStack field_220559_a;
    private final List<ItemStack> field_220560_b = Lists.newArrayList();
    private int field_220561_c;
    private int field_220562_d;
    private int field_220563_e;

    public ShowWaresTask(int n, int n2) {
        super(ImmutableMap.of(MemoryModuleType.INTERACTION_TARGET, MemoryModuleStatus.VALUE_PRESENT), n, n2);
    }

    @Override
    public boolean shouldExecute(ServerWorld serverWorld, VillagerEntity villagerEntity) {
        Brain<VillagerEntity> brain = villagerEntity.getBrain();
        if (!brain.getMemory(MemoryModuleType.INTERACTION_TARGET).isPresent()) {
            return true;
        }
        LivingEntity livingEntity = brain.getMemory(MemoryModuleType.INTERACTION_TARGET).get();
        return livingEntity.getType() == EntityType.PLAYER && villagerEntity.isAlive() && livingEntity.isAlive() && !villagerEntity.isChild() && villagerEntity.getDistanceSq(livingEntity) <= 17.0;
    }

    @Override
    public boolean shouldContinueExecuting(ServerWorld serverWorld, VillagerEntity villagerEntity, long l) {
        return this.shouldExecute(serverWorld, villagerEntity) && this.field_220563_e > 0 && villagerEntity.getBrain().getMemory(MemoryModuleType.INTERACTION_TARGET).isPresent();
    }

    @Override
    public void startExecuting(ServerWorld serverWorld, VillagerEntity villagerEntity, long l) {
        super.startExecuting(serverWorld, villagerEntity, l);
        this.func_220557_c(villagerEntity);
        this.field_220561_c = 0;
        this.field_220562_d = 0;
        this.field_220563_e = 40;
    }

    @Override
    public void updateTask(ServerWorld serverWorld, VillagerEntity villagerEntity, long l) {
        LivingEntity livingEntity = this.func_220557_c(villagerEntity);
        this.func_220556_a(livingEntity, villagerEntity);
        if (!this.field_220560_b.isEmpty()) {
            this.func_220553_d(villagerEntity);
        } else {
            villagerEntity.setItemStackToSlot(EquipmentSlotType.MAINHAND, ItemStack.EMPTY);
            this.field_220563_e = Math.min(this.field_220563_e, 40);
        }
        --this.field_220563_e;
    }

    @Override
    public void resetTask(ServerWorld serverWorld, VillagerEntity villagerEntity, long l) {
        super.resetTask(serverWorld, villagerEntity, l);
        villagerEntity.getBrain().removeMemory(MemoryModuleType.INTERACTION_TARGET);
        villagerEntity.setItemStackToSlot(EquipmentSlotType.MAINHAND, ItemStack.EMPTY);
        this.field_220559_a = null;
    }

    private void func_220556_a(LivingEntity livingEntity, VillagerEntity villagerEntity) {
        boolean bl = false;
        ItemStack itemStack = livingEntity.getHeldItemMainhand();
        if (this.field_220559_a == null || !ItemStack.areItemsEqual(this.field_220559_a, itemStack)) {
            this.field_220559_a = itemStack;
            bl = true;
            this.field_220560_b.clear();
        }
        if (bl && !this.field_220559_a.isEmpty()) {
            this.func_220555_b(villagerEntity);
            if (!this.field_220560_b.isEmpty()) {
                this.field_220563_e = 900;
                this.func_220558_a(villagerEntity);
            }
        }
    }

    private void func_220558_a(VillagerEntity villagerEntity) {
        villagerEntity.setItemStackToSlot(EquipmentSlotType.MAINHAND, this.field_220560_b.get(0));
    }

    private void func_220555_b(VillagerEntity villagerEntity) {
        for (MerchantOffer merchantOffer : villagerEntity.getOffers()) {
            if (merchantOffer.hasNoUsesLeft() || !this.func_220554_a(merchantOffer)) continue;
            this.field_220560_b.add(merchantOffer.getSellingStack());
        }
    }

    private boolean func_220554_a(MerchantOffer merchantOffer) {
        return ItemStack.areItemsEqual(this.field_220559_a, merchantOffer.getDiscountedBuyingStackFirst()) || ItemStack.areItemsEqual(this.field_220559_a, merchantOffer.getBuyingStackSecond());
    }

    private LivingEntity func_220557_c(VillagerEntity villagerEntity) {
        Brain<VillagerEntity> brain = villagerEntity.getBrain();
        LivingEntity livingEntity = brain.getMemory(MemoryModuleType.INTERACTION_TARGET).get();
        brain.setMemory(MemoryModuleType.LOOK_TARGET, new EntityPosWrapper(livingEntity, true));
        return livingEntity;
    }

    private void func_220553_d(VillagerEntity villagerEntity) {
        if (this.field_220560_b.size() >= 2 && ++this.field_220561_c >= 40) {
            ++this.field_220562_d;
            this.field_220561_c = 0;
            if (this.field_220562_d > this.field_220560_b.size() - 1) {
                this.field_220562_d = 0;
            }
            villagerEntity.setItemStackToSlot(EquipmentSlotType.MAINHAND, this.field_220560_b.get(this.field_220562_d));
        }
    }

    @Override
    public boolean shouldExecute(ServerWorld serverWorld, LivingEntity livingEntity) {
        return this.shouldExecute(serverWorld, (VillagerEntity)livingEntity);
    }

    @Override
    public boolean shouldContinueExecuting(ServerWorld serverWorld, LivingEntity livingEntity, long l) {
        return this.shouldContinueExecuting(serverWorld, (VillagerEntity)livingEntity, l);
    }

    @Override
    public void resetTask(ServerWorld serverWorld, LivingEntity livingEntity, long l) {
        this.resetTask(serverWorld, (VillagerEntity)livingEntity, l);
    }

    @Override
    public void updateTask(ServerWorld serverWorld, LivingEntity livingEntity, long l) {
        this.updateTask(serverWorld, (VillagerEntity)livingEntity, l);
    }

    @Override
    public void startExecuting(ServerWorld serverWorld, LivingEntity livingEntity, long l) {
        this.startExecuting(serverWorld, (VillagerEntity)livingEntity, l);
    }
}

