package net.minecraft.tileentity;

import net.minecraft.inventory.IInventory;
import net.minecraft.world.World;

public abstract interface IHopper
  extends IInventory
{
  public abstract World getWorldObj();
  
  public abstract double getXPos();
  
  public abstract double getYPos();
  
  public abstract double getZPos();
}


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.tileentity.IHopper
 * JD-Core Version:    0.7.0.1
 */