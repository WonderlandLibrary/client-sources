/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.inventory;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.tileentity.EnderChestTileEntity;

public class EnderChestInventory
extends Inventory {
    private EnderChestTileEntity associatedChest;

    public EnderChestInventory() {
        super(27);
    }

    public void setChestTileEntity(EnderChestTileEntity enderChestTileEntity) {
        this.associatedChest = enderChestTileEntity;
    }

    @Override
    public void read(ListNBT listNBT) {
        int n;
        for (n = 0; n < this.getSizeInventory(); ++n) {
            this.setInventorySlotContents(n, ItemStack.EMPTY);
        }
        for (n = 0; n < listNBT.size(); ++n) {
            CompoundNBT compoundNBT = listNBT.getCompound(n);
            int n2 = compoundNBT.getByte("Slot") & 0xFF;
            if (n2 < 0 || n2 >= this.getSizeInventory()) continue;
            this.setInventorySlotContents(n2, ItemStack.read(compoundNBT));
        }
    }

    @Override
    public ListNBT write() {
        ListNBT listNBT = new ListNBT();
        for (int i = 0; i < this.getSizeInventory(); ++i) {
            ItemStack itemStack = this.getStackInSlot(i);
            if (itemStack.isEmpty()) continue;
            CompoundNBT compoundNBT = new CompoundNBT();
            compoundNBT.putByte("Slot", (byte)i);
            itemStack.write(compoundNBT);
            listNBT.add(compoundNBT);
        }
        return listNBT;
    }

    @Override
    public boolean isUsableByPlayer(PlayerEntity playerEntity) {
        return this.associatedChest != null && !this.associatedChest.canBeUsed(playerEntity) ? false : super.isUsableByPlayer(playerEntity);
    }

    @Override
    public void openInventory(PlayerEntity playerEntity) {
        if (this.associatedChest != null) {
            this.associatedChest.openChest();
        }
        super.openInventory(playerEntity);
    }

    @Override
    public void closeInventory(PlayerEntity playerEntity) {
        if (this.associatedChest != null) {
            this.associatedChest.closeChest();
        }
        super.closeInventory(playerEntity);
        this.associatedChest = null;
    }
}

