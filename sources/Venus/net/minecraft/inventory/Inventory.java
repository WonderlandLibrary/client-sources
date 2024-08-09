/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.inventory;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.IInventoryChangedListener;
import net.minecraft.inventory.IRecipeHelperPopulator;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.RecipeItemHelper;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.NonNullList;

public class Inventory
implements IInventory,
IRecipeHelperPopulator {
    private final int slotsCount;
    private final NonNullList<ItemStack> inventoryContents;
    private List<IInventoryChangedListener> listeners;

    public Inventory(int n) {
        this.slotsCount = n;
        this.inventoryContents = NonNullList.withSize(n, ItemStack.EMPTY);
    }

    public Inventory(ItemStack ... itemStackArray) {
        this.slotsCount = itemStackArray.length;
        this.inventoryContents = NonNullList.from(ItemStack.EMPTY, itemStackArray);
    }

    public void addListener(IInventoryChangedListener iInventoryChangedListener) {
        if (this.listeners == null) {
            this.listeners = Lists.newArrayList();
        }
        this.listeners.add(iInventoryChangedListener);
    }

    public void removeListener(IInventoryChangedListener iInventoryChangedListener) {
        this.listeners.remove(iInventoryChangedListener);
    }

    @Override
    public ItemStack getStackInSlot(int n) {
        return n >= 0 && n < this.inventoryContents.size() ? this.inventoryContents.get(n) : ItemStack.EMPTY;
    }

    public List<ItemStack> func_233543_f_() {
        List<ItemStack> list = this.inventoryContents.stream().filter(Inventory::lambda$func_233543_f_$0).collect(Collectors.toList());
        this.clear();
        return list;
    }

    @Override
    public ItemStack decrStackSize(int n, int n2) {
        ItemStack itemStack = ItemStackHelper.getAndSplit(this.inventoryContents, n, n2);
        if (!itemStack.isEmpty()) {
            this.markDirty();
        }
        return itemStack;
    }

    public ItemStack func_223374_a(Item item, int n) {
        ItemStack itemStack = new ItemStack(item, 0);
        for (int i = this.slotsCount - 1; i >= 0; --i) {
            ItemStack itemStack2 = this.getStackInSlot(i);
            if (!itemStack2.getItem().equals(item)) continue;
            int n2 = n - itemStack.getCount();
            ItemStack itemStack3 = itemStack2.split(n2);
            itemStack.grow(itemStack3.getCount());
            if (itemStack.getCount() == n) break;
        }
        if (!itemStack.isEmpty()) {
            this.markDirty();
        }
        return itemStack;
    }

    public ItemStack addItem(ItemStack itemStack) {
        ItemStack itemStack2 = itemStack.copy();
        this.func_223372_c(itemStack2);
        if (itemStack2.isEmpty()) {
            return ItemStack.EMPTY;
        }
        this.func_223375_b(itemStack2);
        return itemStack2.isEmpty() ? ItemStack.EMPTY : itemStack2;
    }

    public boolean func_233541_b_(ItemStack itemStack) {
        boolean bl = false;
        for (ItemStack itemStack2 : this.inventoryContents) {
            if (!itemStack2.isEmpty() && (!this.func_233540_a_(itemStack2, itemStack) || itemStack2.getCount() >= itemStack2.getMaxStackSize())) continue;
            bl = true;
            break;
        }
        return bl;
    }

    @Override
    public ItemStack removeStackFromSlot(int n) {
        ItemStack itemStack = this.inventoryContents.get(n);
        if (itemStack.isEmpty()) {
            return ItemStack.EMPTY;
        }
        this.inventoryContents.set(n, ItemStack.EMPTY);
        return itemStack;
    }

    @Override
    public void setInventorySlotContents(int n, ItemStack itemStack) {
        this.inventoryContents.set(n, itemStack);
        if (!itemStack.isEmpty() && itemStack.getCount() > this.getInventoryStackLimit()) {
            itemStack.setCount(this.getInventoryStackLimit());
        }
        this.markDirty();
    }

    @Override
    public int getSizeInventory() {
        return this.slotsCount;
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack itemStack : this.inventoryContents) {
            if (itemStack.isEmpty()) continue;
            return true;
        }
        return false;
    }

    @Override
    public void markDirty() {
        if (this.listeners != null) {
            for (IInventoryChangedListener iInventoryChangedListener : this.listeners) {
                iInventoryChangedListener.onInventoryChanged(this);
            }
        }
    }

    @Override
    public boolean isUsableByPlayer(PlayerEntity playerEntity) {
        return false;
    }

    @Override
    public void clear() {
        this.inventoryContents.clear();
        this.markDirty();
    }

    @Override
    public void fillStackedContents(RecipeItemHelper recipeItemHelper) {
        for (ItemStack itemStack : this.inventoryContents) {
            recipeItemHelper.accountStack(itemStack);
        }
    }

    public String toString() {
        return this.inventoryContents.stream().filter(Inventory::lambda$toString$1).collect(Collectors.toList()).toString();
    }

    private void func_223375_b(ItemStack itemStack) {
        for (int i = 0; i < this.slotsCount; ++i) {
            ItemStack itemStack2 = this.getStackInSlot(i);
            if (!itemStack2.isEmpty()) continue;
            this.setInventorySlotContents(i, itemStack.copy());
            itemStack.setCount(0);
            return;
        }
    }

    private void func_223372_c(ItemStack itemStack) {
        for (int i = 0; i < this.slotsCount; ++i) {
            ItemStack itemStack2 = this.getStackInSlot(i);
            if (!this.func_233540_a_(itemStack2, itemStack)) continue;
            this.func_223373_a(itemStack, itemStack2);
            if (!itemStack.isEmpty()) continue;
            return;
        }
    }

    private boolean func_233540_a_(ItemStack itemStack, ItemStack itemStack2) {
        return itemStack.getItem() == itemStack2.getItem() && ItemStack.areItemStackTagsEqual(itemStack, itemStack2);
    }

    private void func_223373_a(ItemStack itemStack, ItemStack itemStack2) {
        int n = Math.min(this.getInventoryStackLimit(), itemStack2.getMaxStackSize());
        int n2 = Math.min(itemStack.getCount(), n - itemStack2.getCount());
        if (n2 > 0) {
            itemStack2.grow(n2);
            itemStack.shrink(n2);
            this.markDirty();
        }
    }

    public void read(ListNBT listNBT) {
        for (int i = 0; i < listNBT.size(); ++i) {
            ItemStack itemStack = ItemStack.read(listNBT.getCompound(i));
            if (itemStack.isEmpty()) continue;
            this.addItem(itemStack);
        }
    }

    public ListNBT write() {
        ListNBT listNBT = new ListNBT();
        for (int i = 0; i < this.getSizeInventory(); ++i) {
            ItemStack itemStack = this.getStackInSlot(i);
            if (itemStack.isEmpty()) continue;
            listNBT.add(itemStack.write(new CompoundNBT()));
        }
        return listNBT;
    }

    private static boolean lambda$toString$1(ItemStack itemStack) {
        return !itemStack.isEmpty();
    }

    private static boolean lambda$func_233543_f_$0(ItemStack itemStack) {
        return !itemStack.isEmpty();
    }
}

