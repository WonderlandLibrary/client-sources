package net.minecraft.tileentity;

import net.minecraft.inventory.IInventory;
import net.minecraft.world.World;

public interface IHopper extends IInventory {
  World getWorld();
  
  double getXPos();
  
  double getYPos();
  
  double getZPos();
}


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\tileentity\IHopper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */