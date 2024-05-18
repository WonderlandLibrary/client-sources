package net.minecraft.world;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;

public interface IInteractionObject extends IWorldNameable {
  Container createContainer(InventoryPlayer paramInventoryPlayer, EntityPlayer paramEntityPlayer);
  
  String getGuiID();
}


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\world\IInteractionObject.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */