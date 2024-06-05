package net.minecraft.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerDispenser extends Container {
   private IInventory dispenserInventory;

   public ContainerDispenser(IInventory var1, IInventory var2) {
      this.dispenserInventory = var2;

      for(int var3 = 0; var3 < 3; ++var3) {
         for(int var4 = 0; var4 < 3; ++var4) {
            this.addSlotToContainer(new Slot(var2, var4 + var3 * 3, 62 + var4 * 18, 17 + var3 * 18));
         }
      }

      for(int var5 = 0; var5 < 3; ++var5) {
         for(int var7 = 0; var7 < 9; ++var7) {
            this.addSlotToContainer(new Slot(var1, var7 + var5 * 9 + 9, 8 + var7 * 18, 84 + var5 * 18));
         }
      }

      for(int var6 = 0; var6 < 9; ++var6) {
         this.addSlotToContainer(new Slot(var1, var6, 8 + var6 * 18, 142));
      }

   }

   public boolean canInteractWith(EntityPlayer var1) {
      return this.dispenserInventory.isUseableByPlayer(var1);
   }

   public ItemStack transferStackInSlot(EntityPlayer var1, int var2) {
      ItemStack var3 = null;
      Slot var4 = (Slot)this.inventorySlots.get(var2);
      if(var4.getHasStack()) {
         ItemStack var5 = var4.getStack();
         var3 = var5.copy();
         if(var2 < 9) {
            if(!this.mergeItemStack(var5, 9, 45, true)) {
               return null;
            }
         } else if(!this.mergeItemStack(var5, 0, 9, false)) {
            return null;
         }

         if(var5.stackSize == 0) {
            var4.putStack((ItemStack)null);
         } else {
            var4.onSlotChanged();
         }

         if(var5.stackSize == var3.stackSize) {
            return null;
         }

         var4.onPickupFromSlot(var1, var5);
      }

      return var3;
   }
}
