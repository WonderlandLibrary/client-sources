package net.minecraft.world;

import net.minecraft.inventory.IInventory;

public interface ILockableContainer extends IInventory, IInteractionObject {
  boolean isLocked();
  
  void setLockCode(LockCode paramLockCode);
  
  LockCode getLockCode();
}


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\world\ILockableContainer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */