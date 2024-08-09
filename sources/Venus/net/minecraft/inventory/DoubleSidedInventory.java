/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.inventory;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class DoubleSidedInventory
implements IInventory {
    private final IInventory upperChest;
    private final IInventory lowerChest;

    public DoubleSidedInventory(IInventory iInventory, IInventory iInventory2) {
        if (iInventory == null) {
            iInventory = iInventory2;
        }
        if (iInventory2 == null) {
            iInventory2 = iInventory;
        }
        this.upperChest = iInventory;
        this.lowerChest = iInventory2;
    }

    @Override
    public int getSizeInventory() {
        return this.upperChest.getSizeInventory() + this.lowerChest.getSizeInventory();
    }

    @Override
    public boolean isEmpty() {
        return this.upperChest.isEmpty() && this.lowerChest.isEmpty();
    }

    public boolean isPartOfLargeChest(IInventory iInventory) {
        return this.upperChest == iInventory || this.lowerChest == iInventory;
    }

    @Override
    public ItemStack getStackInSlot(int n) {
        return n >= this.upperChest.getSizeInventory() ? this.lowerChest.getStackInSlot(n - this.upperChest.getSizeInventory()) : this.upperChest.getStackInSlot(n);
    }

    @Override
    public ItemStack decrStackSize(int n, int n2) {
        return n >= this.upperChest.getSizeInventory() ? this.lowerChest.decrStackSize(n - this.upperChest.getSizeInventory(), n2) : this.upperChest.decrStackSize(n, n2);
    }

    @Override
    public ItemStack removeStackFromSlot(int n) {
        return n >= this.upperChest.getSizeInventory() ? this.lowerChest.removeStackFromSlot(n - this.upperChest.getSizeInventory()) : this.upperChest.removeStackFromSlot(n);
    }

    @Override
    public void setInventorySlotContents(int n, ItemStack itemStack) {
        if (n >= this.upperChest.getSizeInventory()) {
            this.lowerChest.setInventorySlotContents(n - this.upperChest.getSizeInventory(), itemStack);
        } else {
            this.upperChest.setInventorySlotContents(n, itemStack);
        }
    }

    @Override
    public int getInventoryStackLimit() {
        return this.upperChest.getInventoryStackLimit();
    }

    @Override
    public void markDirty() {
        this.upperChest.markDirty();
        this.lowerChest.markDirty();
    }

    @Override
    public boolean isUsableByPlayer(PlayerEntity playerEntity) {
        return this.upperChest.isUsableByPlayer(playerEntity) && this.lowerChest.isUsableByPlayer(playerEntity);
    }

    @Override
    public void openInventory(PlayerEntity playerEntity) {
        this.upperChest.openInventory(playerEntity);
        this.lowerChest.openInventory(playerEntity);
    }

    @Override
    public void closeInventory(PlayerEntity playerEntity) {
        this.upperChest.closeInventory(playerEntity);
        this.lowerChest.closeInventory(playerEntity);
    }

    @Override
    public boolean isItemValidForSlot(int n, ItemStack itemStack) {
        return n >= this.upperChest.getSizeInventory() ? this.lowerChest.isItemValidForSlot(n - this.upperChest.getSizeInventory(), itemStack) : this.upperChest.isItemValidForSlot(n, itemStack);
    }

    @Override
    public void clear() {
        this.upperChest.clear();
        this.lowerChest.clear();
    }
}

