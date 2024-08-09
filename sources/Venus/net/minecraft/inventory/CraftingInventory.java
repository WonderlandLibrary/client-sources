/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.inventory;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.IRecipeHelperPopulator;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.RecipeItemHelper;
import net.minecraft.util.NonNullList;

public class CraftingInventory
implements IInventory,
IRecipeHelperPopulator {
    private final NonNullList<ItemStack> stackList;
    private final int width;
    private final int height;
    private final Container eventHandler;

    public CraftingInventory(Container container, int n, int n2) {
        this.stackList = NonNullList.withSize(n * n2, ItemStack.EMPTY);
        this.eventHandler = container;
        this.width = n;
        this.height = n2;
    }

    @Override
    public int getSizeInventory() {
        return this.stackList.size();
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack itemStack : this.stackList) {
            if (itemStack.isEmpty()) continue;
            return true;
        }
        return false;
    }

    @Override
    public ItemStack getStackInSlot(int n) {
        return n >= this.getSizeInventory() ? ItemStack.EMPTY : this.stackList.get(n);
    }

    @Override
    public ItemStack removeStackFromSlot(int n) {
        return ItemStackHelper.getAndRemove(this.stackList, n);
    }

    @Override
    public ItemStack decrStackSize(int n, int n2) {
        ItemStack itemStack = ItemStackHelper.getAndSplit(this.stackList, n, n2);
        if (!itemStack.isEmpty()) {
            this.eventHandler.onCraftMatrixChanged(this);
        }
        return itemStack;
    }

    @Override
    public void setInventorySlotContents(int n, ItemStack itemStack) {
        this.stackList.set(n, itemStack);
        this.eventHandler.onCraftMatrixChanged(this);
    }

    @Override
    public void markDirty() {
    }

    @Override
    public boolean isUsableByPlayer(PlayerEntity playerEntity) {
        return false;
    }

    @Override
    public void clear() {
        this.stackList.clear();
    }

    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }

    @Override
    public void fillStackedContents(RecipeItemHelper recipeItemHelper) {
        for (ItemStack itemStack : this.stackList) {
            recipeItemHelper.accountPlainStack(itemStack);
        }
    }
}

