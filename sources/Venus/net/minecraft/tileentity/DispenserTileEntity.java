/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.tileentity;

import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.DispenserContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class DispenserTileEntity
extends LockableLootTileEntity {
    private static final Random RNG = new Random();
    private NonNullList<ItemStack> stacks = NonNullList.withSize(9, ItemStack.EMPTY);

    protected DispenserTileEntity(TileEntityType<?> tileEntityType) {
        super(tileEntityType);
    }

    public DispenserTileEntity() {
        this(TileEntityType.DISPENSER);
    }

    @Override
    public int getSizeInventory() {
        return 0;
    }

    public int getDispenseSlot() {
        this.fillWithLoot(null);
        int n = -1;
        int n2 = 1;
        for (int i = 0; i < this.stacks.size(); ++i) {
            if (this.stacks.get(i).isEmpty() || RNG.nextInt(n2++) != 0) continue;
            n = i;
        }
        return n;
    }

    public int addItemStack(ItemStack itemStack) {
        for (int i = 0; i < this.stacks.size(); ++i) {
            if (!this.stacks.get(i).isEmpty()) continue;
            this.setInventorySlotContents(i, itemStack);
            return i;
        }
        return 1;
    }

    @Override
    protected ITextComponent getDefaultName() {
        return new TranslationTextComponent("container.dispenser");
    }

    @Override
    public void read(BlockState blockState, CompoundNBT compoundNBT) {
        super.read(blockState, compoundNBT);
        this.stacks = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
        if (!this.checkLootAndRead(compoundNBT)) {
            ItemStackHelper.loadAllItems(compoundNBT, this.stacks);
        }
    }

    @Override
    public CompoundNBT write(CompoundNBT compoundNBT) {
        super.write(compoundNBT);
        if (!this.checkLootAndWrite(compoundNBT)) {
            ItemStackHelper.saveAllItems(compoundNBT, this.stacks);
        }
        return compoundNBT;
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return this.stacks;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> nonNullList) {
        this.stacks = nonNullList;
    }

    @Override
    protected Container createMenu(int n, PlayerInventory playerInventory) {
        return new DispenserContainer(n, playerInventory, this);
    }
}

