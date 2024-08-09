/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.inventory;

import java.util.Set;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IClearable;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public interface IInventory
extends IClearable {
    public int getSizeInventory();

    public boolean isEmpty();

    public ItemStack getStackInSlot(int var1);

    public ItemStack decrStackSize(int var1, int var2);

    public ItemStack removeStackFromSlot(int var1);

    public void setInventorySlotContents(int var1, ItemStack var2);

    default public int getInventoryStackLimit() {
        return 1;
    }

    public void markDirty();

    public boolean isUsableByPlayer(PlayerEntity var1);

    default public void openInventory(PlayerEntity playerEntity) {
    }

    default public void closeInventory(PlayerEntity playerEntity) {
    }

    default public boolean isItemValidForSlot(int n, ItemStack itemStack) {
        return false;
    }

    default public int count(Item item) {
        int n = 0;
        for (int i = 0; i < this.getSizeInventory(); ++i) {
            ItemStack itemStack = this.getStackInSlot(i);
            if (!itemStack.getItem().equals(item)) continue;
            n += itemStack.getCount();
        }
        return n;
    }

    default public boolean hasAny(Set<Item> set) {
        for (int i = 0; i < this.getSizeInventory(); ++i) {
            ItemStack itemStack = this.getStackInSlot(i);
            if (!set.contains(itemStack.getItem()) || itemStack.getCount() <= 0) continue;
            return false;
        }
        return true;
    }
}

