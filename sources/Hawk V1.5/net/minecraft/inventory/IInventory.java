package net.minecraft.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IWorldNameable;

public interface IInventory extends IWorldNameable {
   void openInventory(EntityPlayer var1);

   void setField(int var1, int var2);

   void clearInventory();

   ItemStack decrStackSize(int var1, int var2);

   int getInventoryStackLimit();

   boolean isItemValidForSlot(int var1, ItemStack var2);

   void markDirty();

   ItemStack getStackInSlot(int var1);

   int getSizeInventory();

   int getField(int var1);

   int getFieldCount();

   boolean isUseableByPlayer(EntityPlayer var1);

   ItemStack getStackInSlotOnClosing(int var1);

   void closeInventory(EntityPlayer var1);

   void setInventorySlotContents(int var1, ItemStack var2);
}
