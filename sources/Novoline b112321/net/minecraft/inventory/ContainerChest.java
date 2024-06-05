package net.minecraft.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerChest extends Container {
   private IInventory lowerChestInventory;
   private int numRows;

   public ContainerChest(IInventory var1, IInventory var2, EntityPlayer var3) {
      this.lowerChestInventory = var2;
      this.numRows = var2.getSizeInventory() / 9;
      var2.openInventory(var3);
      int var4 = (this.numRows - 4) * 18;

      for(int var5 = 0; var5 < this.numRows; ++var5) {
         for(int var6 = 0; var6 < 9; ++var6) {
            this.addSlotToContainer(new Slot(var2, var6 + var5 * 9, 8 + var6 * 18, 18 + var5 * 18));
         }
      }

      for(int var7 = 0; var7 < 3; ++var7) {
         for(int var9 = 0; var9 < 9; ++var9) {
            this.addSlotToContainer(new Slot(var1, var9 + var7 * 9 + 9, 8 + var9 * 18, 103 + var7 * 18 + var4));
         }
      }

      for(int var8 = 0; var8 < 9; ++var8) {
         this.addSlotToContainer(new Slot(var1, var8, 8 + var8 * 18, 161 + var4));
      }

   }

   public boolean canInteractWith(EntityPlayer var1) {
      return this.lowerChestInventory.isUseableByPlayer(var1);
   }

   public ItemStack transferStackInSlot(EntityPlayer var1, int var2) {
      ItemStack var3 = null;
      Slot var4 = (Slot)this.inventorySlots.get(var2);
      if(var4.getHasStack()) {
         ItemStack var5 = var4.getStack();
         var3 = var5.copy();
         if(var2 < this.numRows * 9) {
            if(!this.mergeItemStack(var5, this.numRows * 9, this.inventorySlots.size(), true)) {
               return null;
            }
         } else if(!this.mergeItemStack(var5, 0, this.numRows * 9, false)) {
            return null;
         }

         if(var5.stackSize == 0) {
            var4.putStack((ItemStack)null);
         } else {
            var4.onSlotChanged();
         }
      }

      return var3;
   }

   public void onContainerClosed(EntityPlayer var1) {
      super.onContainerClosed(var1);
      this.lowerChestInventory.closeInventory(var1);
   }

   public IInventory getLowerChestInventory() {
      return this.lowerChestInventory;
   }
}
