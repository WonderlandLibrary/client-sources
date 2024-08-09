/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.inventory;

import javax.annotation.Nullable;
import net.minecraft.entity.merchant.IMerchant;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MerchantOffer;
import net.minecraft.item.MerchantOffers;
import net.minecraft.util.NonNullList;

public class MerchantInventory
implements IInventory {
    private final IMerchant merchant;
    private final NonNullList<ItemStack> slots = NonNullList.withSize(3, ItemStack.EMPTY);
    @Nullable
    private MerchantOffer field_214026_c;
    private int currentRecipeIndex;
    private int exp;

    public MerchantInventory(IMerchant iMerchant) {
        this.merchant = iMerchant;
    }

    @Override
    public int getSizeInventory() {
        return this.slots.size();
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack itemStack : this.slots) {
            if (itemStack.isEmpty()) continue;
            return true;
        }
        return false;
    }

    @Override
    public ItemStack getStackInSlot(int n) {
        return this.slots.get(n);
    }

    @Override
    public ItemStack decrStackSize(int n, int n2) {
        ItemStack itemStack = this.slots.get(n);
        if (n == 2 && !itemStack.isEmpty()) {
            return ItemStackHelper.getAndSplit(this.slots, n, itemStack.getCount());
        }
        ItemStack itemStack2 = ItemStackHelper.getAndSplit(this.slots, n, n2);
        if (!itemStack2.isEmpty() && this.inventoryResetNeededOnSlotChange(n)) {
            this.resetRecipeAndSlots();
        }
        return itemStack2;
    }

    private boolean inventoryResetNeededOnSlotChange(int n) {
        return n == 0 || n == 1;
    }

    @Override
    public ItemStack removeStackFromSlot(int n) {
        return ItemStackHelper.getAndRemove(this.slots, n);
    }

    @Override
    public void setInventorySlotContents(int n, ItemStack itemStack) {
        this.slots.set(n, itemStack);
        if (!itemStack.isEmpty() && itemStack.getCount() > this.getInventoryStackLimit()) {
            itemStack.setCount(this.getInventoryStackLimit());
        }
        if (this.inventoryResetNeededOnSlotChange(n)) {
            this.resetRecipeAndSlots();
        }
    }

    @Override
    public boolean isUsableByPlayer(PlayerEntity playerEntity) {
        return this.merchant.getCustomer() == playerEntity;
    }

    @Override
    public void markDirty() {
        this.resetRecipeAndSlots();
    }

    public void resetRecipeAndSlots() {
        ItemStack itemStack;
        ItemStack itemStack2;
        this.field_214026_c = null;
        if (this.slots.get(0).isEmpty()) {
            itemStack2 = this.slots.get(1);
            itemStack = ItemStack.EMPTY;
        } else {
            itemStack2 = this.slots.get(0);
            itemStack = this.slots.get(1);
        }
        if (itemStack2.isEmpty()) {
            this.setInventorySlotContents(2, ItemStack.EMPTY);
            this.exp = 0;
        } else {
            MerchantOffers merchantOffers = this.merchant.getOffers();
            if (!merchantOffers.isEmpty()) {
                MerchantOffer merchantOffer = merchantOffers.func_222197_a(itemStack2, itemStack, this.currentRecipeIndex);
                if (merchantOffer == null || merchantOffer.hasNoUsesLeft()) {
                    this.field_214026_c = merchantOffer;
                    merchantOffer = merchantOffers.func_222197_a(itemStack, itemStack2, this.currentRecipeIndex);
                }
                if (merchantOffer != null && !merchantOffer.hasNoUsesLeft()) {
                    this.field_214026_c = merchantOffer;
                    this.setInventorySlotContents(2, merchantOffer.getCopyOfSellingStack());
                    this.exp = merchantOffer.getGivenExp();
                } else {
                    this.setInventorySlotContents(2, ItemStack.EMPTY);
                    this.exp = 0;
                }
            }
            this.merchant.verifySellingItem(this.getStackInSlot(2));
        }
    }

    @Nullable
    public MerchantOffer func_214025_g() {
        return this.field_214026_c;
    }

    public void setCurrentRecipeIndex(int n) {
        this.currentRecipeIndex = n;
        this.resetRecipeAndSlots();
    }

    @Override
    public void clear() {
        this.slots.clear();
    }

    public int getClientSideExp() {
        return this.exp;
    }
}

