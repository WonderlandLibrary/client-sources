/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.inventory.container;

import com.mojang.datafixers.util.Pair;
import javax.annotation.Nullable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class Slot {
    private final int slotIndex;
    public final IInventory inventory;
    public int slotNumber;
    public final int xPos;
    public final int yPos;

    public Slot(IInventory iInventory, int n, int n2, int n3) {
        this.inventory = iInventory;
        this.slotIndex = n;
        this.xPos = n2;
        this.yPos = n3;
    }

    public void onSlotChange(ItemStack itemStack, ItemStack itemStack2) {
        int n = itemStack2.getCount() - itemStack.getCount();
        if (n > 0) {
            this.onCrafting(itemStack2, n);
        }
    }

    protected void onCrafting(ItemStack itemStack, int n) {
    }

    protected void onSwapCraft(int n) {
    }

    protected void onCrafting(ItemStack itemStack) {
    }

    public ItemStack onTake(PlayerEntity playerEntity, ItemStack itemStack) {
        this.onSlotChanged();
        return itemStack;
    }

    public boolean isItemValid(ItemStack itemStack) {
        return false;
    }

    public ItemStack getStack() {
        return this.inventory.getStackInSlot(this.slotIndex);
    }

    public boolean getHasStack() {
        return !this.getStack().isEmpty();
    }

    public void putStack(ItemStack itemStack) {
        this.inventory.setInventorySlotContents(this.slotIndex, itemStack);
        this.onSlotChanged();
    }

    public void onSlotChanged() {
        this.inventory.markDirty();
    }

    public int getSlotStackLimit() {
        return this.inventory.getInventoryStackLimit();
    }

    public int getItemStackLimit(ItemStack itemStack) {
        return this.getSlotStackLimit();
    }

    @Nullable
    public Pair<ResourceLocation, ResourceLocation> getBackground() {
        return null;
    }

    public ItemStack decrStackSize(int n) {
        return this.inventory.decrStackSize(this.slotIndex, n);
    }

    public boolean canTakeStack(PlayerEntity playerEntity) {
        return false;
    }

    public boolean isEnabled() {
        return false;
    }
}

