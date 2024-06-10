package net.minecraft.inventory;

import java.util.List;
import net.minecraft.item.ItemStack;

public abstract interface ICrafting
{
  public abstract void sendContainerAndContentsToPlayer(Container paramContainer, List paramList);
  
  public abstract void sendSlotContents(Container paramContainer, int paramInt, ItemStack paramItemStack);
  
  public abstract void sendProgressBarUpdate(Container paramContainer, int paramInt1, int paramInt2);
}


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.inventory.ICrafting
 * JD-Core Version:    0.7.0.1
 */