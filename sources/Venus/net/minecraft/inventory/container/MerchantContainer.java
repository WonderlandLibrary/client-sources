/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.inventory.container;

import net.minecraft.entity.Entity;
import net.minecraft.entity.NPCMerchant;
import net.minecraft.entity.merchant.IMerchant;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.MerchantInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.MerchantResultSlot;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MerchantOffer;
import net.minecraft.item.MerchantOffers;
import net.minecraft.util.SoundCategory;

public class MerchantContainer
extends Container {
    private final IMerchant merchant;
    private final MerchantInventory merchantInventory;
    private int merchantLevel;
    private boolean field_217055_f;
    private boolean field_223433_g;

    public MerchantContainer(int n, PlayerInventory playerInventory) {
        this(n, playerInventory, new NPCMerchant(playerInventory.player));
    }

    public MerchantContainer(int n, PlayerInventory playerInventory, IMerchant iMerchant) {
        super(ContainerType.MERCHANT, n);
        int n2;
        this.merchant = iMerchant;
        this.merchantInventory = new MerchantInventory(iMerchant);
        this.addSlot(new Slot(this.merchantInventory, 0, 136, 37));
        this.addSlot(new Slot(this.merchantInventory, 1, 162, 37));
        this.addSlot(new MerchantResultSlot(playerInventory.player, iMerchant, this.merchantInventory, 2, 220, 37));
        for (n2 = 0; n2 < 3; ++n2) {
            for (int i = 0; i < 9; ++i) {
                this.addSlot(new Slot(playerInventory, i + n2 * 9 + 9, 108 + i * 18, 84 + n2 * 18));
            }
        }
        for (n2 = 0; n2 < 9; ++n2) {
            this.addSlot(new Slot(playerInventory, n2, 108 + n2 * 18, 142));
        }
    }

    public void func_217045_a(boolean bl) {
        this.field_217055_f = bl;
    }

    @Override
    public void onCraftMatrixChanged(IInventory iInventory) {
        this.merchantInventory.resetRecipeAndSlots();
        super.onCraftMatrixChanged(iInventory);
    }

    public void setCurrentRecipeIndex(int n) {
        this.merchantInventory.setCurrentRecipeIndex(n);
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerEntity) {
        return this.merchant.getCustomer() == playerEntity;
    }

    public int getXp() {
        return this.merchant.getXp();
    }

    public int getPendingExp() {
        return this.merchantInventory.getClientSideExp();
    }

    public void setXp(int n) {
        this.merchant.setXP(n);
    }

    public int getMerchantLevel() {
        return this.merchantLevel;
    }

    public void setMerchantLevel(int n) {
        this.merchantLevel = n;
    }

    public void func_223431_b(boolean bl) {
        this.field_223433_g = bl;
    }

    public boolean func_223432_h() {
        return this.field_223433_g;
    }

    @Override
    public boolean canMergeSlot(ItemStack itemStack, Slot slot) {
        return true;
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerEntity, int n) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = (Slot)this.inventorySlots.get(n);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemStack2 = slot.getStack();
            itemStack = itemStack2.copy();
            if (n == 2) {
                if (!this.mergeItemStack(itemStack2, 3, 39, false)) {
                    return ItemStack.EMPTY;
                }
                slot.onSlotChange(itemStack2, itemStack);
                this.playMerchantYesSound();
            } else if (n != 0 && n != 1 ? (n >= 3 && n < 30 ? !this.mergeItemStack(itemStack2, 30, 39, true) : n >= 30 && n < 39 && !this.mergeItemStack(itemStack2, 3, 30, true)) : !this.mergeItemStack(itemStack2, 3, 39, true)) {
                return ItemStack.EMPTY;
            }
            if (itemStack2.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
            if (itemStack2.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }
            slot.onTake(playerEntity, itemStack2);
        }
        return itemStack;
    }

    private void playMerchantYesSound() {
        if (!this.merchant.getWorld().isRemote) {
            Entity entity2 = (Entity)((Object)this.merchant);
            this.merchant.getWorld().playSound(entity2.getPosX(), entity2.getPosY(), entity2.getPosZ(), this.merchant.getYesSound(), SoundCategory.NEUTRAL, 1.0f, 1.0f, true);
        }
    }

    @Override
    public void onContainerClosed(PlayerEntity playerEntity) {
        super.onContainerClosed(playerEntity);
        this.merchant.setCustomer(null);
        if (!this.merchant.getWorld().isRemote) {
            if (!playerEntity.isAlive() || playerEntity instanceof ServerPlayerEntity && ((ServerPlayerEntity)playerEntity).hasDisconnected()) {
                ItemStack itemStack = this.merchantInventory.removeStackFromSlot(0);
                if (!itemStack.isEmpty()) {
                    playerEntity.dropItem(itemStack, true);
                }
                if (!(itemStack = this.merchantInventory.removeStackFromSlot(1)).isEmpty()) {
                    playerEntity.dropItem(itemStack, true);
                }
            } else {
                playerEntity.inventory.placeItemBackInInventory(playerEntity.world, this.merchantInventory.removeStackFromSlot(0));
                playerEntity.inventory.placeItemBackInInventory(playerEntity.world, this.merchantInventory.removeStackFromSlot(1));
            }
        }
    }

    public void func_217046_g(int n) {
        if (this.getOffers().size() > n) {
            ItemStack itemStack;
            ItemStack itemStack2 = this.merchantInventory.getStackInSlot(0);
            if (!itemStack2.isEmpty()) {
                if (!this.mergeItemStack(itemStack2, 3, 39, false)) {
                    return;
                }
                this.merchantInventory.setInventorySlotContents(0, itemStack2);
            }
            if (!(itemStack = this.merchantInventory.getStackInSlot(1)).isEmpty()) {
                if (!this.mergeItemStack(itemStack, 3, 39, false)) {
                    return;
                }
                this.merchantInventory.setInventorySlotContents(1, itemStack);
            }
            if (this.merchantInventory.getStackInSlot(0).isEmpty() && this.merchantInventory.getStackInSlot(1).isEmpty()) {
                ItemStack itemStack3 = ((MerchantOffer)this.getOffers().get(n)).getDiscountedBuyingStackFirst();
                this.func_217053_c(0, itemStack3);
                ItemStack itemStack4 = ((MerchantOffer)this.getOffers().get(n)).getBuyingStackSecond();
                this.func_217053_c(1, itemStack4);
            }
        }
    }

    private void func_217053_c(int n, ItemStack itemStack) {
        if (!itemStack.isEmpty()) {
            for (int i = 3; i < 39; ++i) {
                ItemStack itemStack2 = ((Slot)this.inventorySlots.get(i)).getStack();
                if (itemStack2.isEmpty() || !this.areItemStacksEqual(itemStack, itemStack2)) continue;
                ItemStack itemStack3 = this.merchantInventory.getStackInSlot(n);
                int n2 = itemStack3.isEmpty() ? 0 : itemStack3.getCount();
                int n3 = Math.min(itemStack.getMaxStackSize() - n2, itemStack2.getCount());
                ItemStack itemStack4 = itemStack2.copy();
                int n4 = n2 + n3;
                itemStack2.shrink(n3);
                itemStack4.setCount(n4);
                this.merchantInventory.setInventorySlotContents(n, itemStack4);
                if (n4 >= itemStack.getMaxStackSize()) break;
            }
        }
    }

    private boolean areItemStacksEqual(ItemStack itemStack, ItemStack itemStack2) {
        return itemStack.getItem() == itemStack2.getItem() && ItemStack.areItemStackTagsEqual(itemStack, itemStack2);
    }

    public void setClientSideOffers(MerchantOffers merchantOffers) {
        this.merchant.setClientSideOffers(merchantOffers);
    }

    public MerchantOffers getOffers() {
        return this.merchant.getOffers();
    }

    public boolean func_217042_i() {
        return this.field_217055_f;
    }
}

