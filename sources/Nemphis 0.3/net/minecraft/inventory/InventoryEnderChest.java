/*
 * Decompiled with CFR 0_118.
 */
package net.minecraft.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntityEnderChest;

public class InventoryEnderChest
extends InventoryBasic {
    private TileEntityEnderChest associatedChest;
    private static final String __OBFID = "CL_00001759";

    public InventoryEnderChest() {
        super("container.enderchest", false, 27);
    }

    public void setChestTileEntity(TileEntityEnderChest chestTileEntity) {
        this.associatedChest = chestTileEntity;
    }

    public void loadInventoryFromNBT(NBTTagList p_70486_1_) {
        int var2 = 0;
        while (var2 < this.getSizeInventory()) {
            this.setInventorySlotContents(var2, null);
            ++var2;
        }
        var2 = 0;
        while (var2 < p_70486_1_.tagCount()) {
            NBTTagCompound var3 = p_70486_1_.getCompoundTagAt(var2);
            int var4 = var3.getByte("Slot") & 255;
            if (var4 >= 0 && var4 < this.getSizeInventory()) {
                this.setInventorySlotContents(var4, ItemStack.loadItemStackFromNBT(var3));
            }
            ++var2;
        }
    }

    public NBTTagList saveInventoryToNBT() {
        NBTTagList var1 = new NBTTagList();
        int var2 = 0;
        while (var2 < this.getSizeInventory()) {
            ItemStack var3 = this.getStackInSlot(var2);
            if (var3 != null) {
                NBTTagCompound var4 = new NBTTagCompound();
                var4.setByte("Slot", (byte)var2);
                var3.writeToNBT(var4);
                var1.appendTag(var4);
            }
            ++var2;
        }
        return var1;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer playerIn) {
        return this.associatedChest != null && !this.associatedChest.func_145971_a(playerIn) ? false : super.isUseableByPlayer(playerIn);
    }

    @Override
    public void openInventory(EntityPlayer playerIn) {
        if (this.associatedChest != null) {
            this.associatedChest.func_145969_a();
        }
        super.openInventory(playerIn);
    }

    @Override
    public void closeInventory(EntityPlayer playerIn) {
        if (this.associatedChest != null) {
            this.associatedChest.func_145970_b();
        }
        super.closeInventory(playerIn);
        this.associatedChest = null;
    }
}

