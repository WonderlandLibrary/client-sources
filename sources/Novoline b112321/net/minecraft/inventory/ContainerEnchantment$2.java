package net.minecraft.inventory;

import net.minecraft.inventory.ContainerEnchantment;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

class ContainerEnchantment$2 extends Slot {
   final ContainerEnchantment this$0;

   ContainerEnchantment$2(ContainerEnchantment var1, IInventory var2, int var3, int var4, int var5) {
      super(var2, var3, var4, var5);
      this.this$0 = var1;
   }

   public boolean isItemValid(ItemStack var1) {
      return true;
   }

   public int getSlotStackLimit() {
      return 1;
   }
}
