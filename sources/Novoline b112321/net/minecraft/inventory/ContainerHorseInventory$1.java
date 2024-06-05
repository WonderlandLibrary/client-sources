package net.minecraft.inventory;

import net.minecraft.init.Items;
import net.minecraft.inventory.ContainerHorseInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

class ContainerHorseInventory$1 extends Slot {
   final ContainerHorseInventory this$0;

   ContainerHorseInventory$1(ContainerHorseInventory var1, IInventory var2, int var3, int var4, int var5) {
      super(var2, var3, var4, var5);
      this.this$0 = var1;
   }

   public boolean isItemValid(ItemStack var1) {
      return super.isItemValid(var1) && var1.getItem() == Items.saddle && !this.getHasStack();
   }
}
