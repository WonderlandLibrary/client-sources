package net.minecraft.inventory;

import net.minecraft.item.ItemStack;

public abstract interface ISidedInventory
  extends IInventory
{
  public abstract int[] getAccessibleSlotsFromSide(int paramInt);
  
  public abstract boolean canInsertItem(int paramInt1, ItemStack paramItemStack, int paramInt2);
  
  public abstract boolean canExtractItem(int paramInt1, ItemStack paramItemStack, int paramInt2);
}


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.inventory.ISidedInventory
 * JD-Core Version:    0.7.0.1
 */