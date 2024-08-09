/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.inventory.container;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.AbstractFurnaceTileEntity;

public class FurnaceResultSlot
extends Slot {
    private final PlayerEntity player;
    private int removeCount;

    public FurnaceResultSlot(PlayerEntity playerEntity, IInventory iInventory, int n, int n2, int n3) {
        super(iInventory, n, n2, n3);
        this.player = playerEntity;
    }

    @Override
    public boolean isItemValid(ItemStack itemStack) {
        return true;
    }

    @Override
    public ItemStack decrStackSize(int n) {
        if (this.getHasStack()) {
            this.removeCount += Math.min(n, this.getStack().getCount());
        }
        return super.decrStackSize(n);
    }

    @Override
    public ItemStack onTake(PlayerEntity playerEntity, ItemStack itemStack) {
        this.onCrafting(itemStack);
        super.onTake(playerEntity, itemStack);
        return itemStack;
    }

    @Override
    protected void onCrafting(ItemStack itemStack, int n) {
        this.removeCount += n;
        this.onCrafting(itemStack);
    }

    @Override
    protected void onCrafting(ItemStack itemStack) {
        itemStack.onCrafting(this.player.world, this.player, this.removeCount);
        if (!this.player.world.isRemote && this.inventory instanceof AbstractFurnaceTileEntity) {
            ((AbstractFurnaceTileEntity)this.inventory).unlockRecipes(this.player);
        }
        this.removeCount = 0;
    }
}

