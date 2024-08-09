/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.inventory.container;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.IRecipeHolder;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.NonNullList;

public class CraftingResultSlot
extends Slot {
    private final CraftingInventory craftMatrix;
    private final PlayerEntity player;
    private int amountCrafted;

    public CraftingResultSlot(PlayerEntity playerEntity, CraftingInventory craftingInventory, IInventory iInventory, int n, int n2, int n3) {
        super(iInventory, n, n2, n3);
        this.player = playerEntity;
        this.craftMatrix = craftingInventory;
    }

    @Override
    public boolean isItemValid(ItemStack itemStack) {
        return true;
    }

    @Override
    public ItemStack decrStackSize(int n) {
        if (this.getHasStack()) {
            this.amountCrafted += Math.min(n, this.getStack().getCount());
        }
        return super.decrStackSize(n);
    }

    @Override
    protected void onCrafting(ItemStack itemStack, int n) {
        this.amountCrafted += n;
        this.onCrafting(itemStack);
    }

    @Override
    protected void onSwapCraft(int n) {
        this.amountCrafted += n;
    }

    @Override
    protected void onCrafting(ItemStack itemStack) {
        if (this.amountCrafted > 0) {
            itemStack.onCrafting(this.player.world, this.player, this.amountCrafted);
        }
        if (this.inventory instanceof IRecipeHolder) {
            ((IRecipeHolder)((Object)this.inventory)).onCrafting(this.player);
        }
        this.amountCrafted = 0;
    }

    @Override
    public ItemStack onTake(PlayerEntity playerEntity, ItemStack itemStack) {
        this.onCrafting(itemStack);
        NonNullList<ItemStack> nonNullList = playerEntity.world.getRecipeManager().getRecipeNonNull(IRecipeType.CRAFTING, this.craftMatrix, playerEntity.world);
        for (int i = 0; i < nonNullList.size(); ++i) {
            ItemStack itemStack2 = this.craftMatrix.getStackInSlot(i);
            ItemStack itemStack3 = nonNullList.get(i);
            if (!itemStack2.isEmpty()) {
                this.craftMatrix.decrStackSize(i, 1);
                itemStack2 = this.craftMatrix.getStackInSlot(i);
            }
            if (itemStack3.isEmpty()) continue;
            if (itemStack2.isEmpty()) {
                this.craftMatrix.setInventorySlotContents(i, itemStack3);
                continue;
            }
            if (ItemStack.areItemsEqual(itemStack2, itemStack3) && ItemStack.areItemStackTagsEqual(itemStack2, itemStack3)) {
                itemStack3.grow(itemStack2.getCount());
                this.craftMatrix.setInventorySlotContents(i, itemStack3);
                continue;
            }
            if (this.player.inventory.addItemStackToInventory(itemStack3)) continue;
            this.player.dropItem(itemStack3, true);
        }
        return itemStack;
    }
}

