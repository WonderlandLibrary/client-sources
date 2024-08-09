/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.inventory;

import javax.annotation.Nullable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.IRecipeHolder;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;

public class CraftResultInventory
implements IInventory,
IRecipeHolder {
    private final NonNullList<ItemStack> stackResult = NonNullList.withSize(1, ItemStack.EMPTY);
    @Nullable
    private IRecipe<?> recipeUsed;

    @Override
    public int getSizeInventory() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack itemStack : this.stackResult) {
            if (itemStack.isEmpty()) continue;
            return true;
        }
        return false;
    }

    @Override
    public ItemStack getStackInSlot(int n) {
        return this.stackResult.get(0);
    }

    @Override
    public ItemStack decrStackSize(int n, int n2) {
        return ItemStackHelper.getAndRemove(this.stackResult, 0);
    }

    @Override
    public ItemStack removeStackFromSlot(int n) {
        return ItemStackHelper.getAndRemove(this.stackResult, 0);
    }

    @Override
    public void setInventorySlotContents(int n, ItemStack itemStack) {
        this.stackResult.set(0, itemStack);
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
        this.stackResult.clear();
    }

    @Override
    public void setRecipeUsed(@Nullable IRecipe<?> iRecipe) {
        this.recipeUsed = iRecipe;
    }

    @Override
    @Nullable
    public IRecipe<?> getRecipeUsed() {
        return this.recipeUsed;
    }
}

