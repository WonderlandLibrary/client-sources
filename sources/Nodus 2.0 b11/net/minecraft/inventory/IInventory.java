package net.minecraft.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public abstract interface IInventory
{
  public abstract int getSizeInventory();
  
  public abstract ItemStack getStackInSlot(int paramInt);
  
  public abstract ItemStack decrStackSize(int paramInt1, int paramInt2);
  
  public abstract ItemStack getStackInSlotOnClosing(int paramInt);
  
  public abstract void setInventorySlotContents(int paramInt, ItemStack paramItemStack);
  
  public abstract String getInventoryName();
  
  public abstract boolean isInventoryNameLocalized();
  
  public abstract int getInventoryStackLimit();
  
  public abstract void onInventoryChanged();
  
  public abstract boolean isUseableByPlayer(EntityPlayer paramEntityPlayer);
  
  public abstract void openInventory();
  
  public abstract void closeInventory();
  
  public abstract boolean isItemValidForSlot(int paramInt, ItemStack paramItemStack);
}


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.inventory.IInventory
 * JD-Core Version:    0.7.0.1
 */