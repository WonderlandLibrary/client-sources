package net.minecraft.inventory;

import net.minecraft.inventory.ContainerEnchantment;
import net.minecraft.inventory.InventoryBasic;

class ContainerEnchantment$1 extends InventoryBasic {
   final ContainerEnchantment this$0;

   ContainerEnchantment$1(ContainerEnchantment var1, String var2, boolean var3, int var4) {
      super(var2, var3, var4);
      this.this$0 = var1;
   }

   public int getInventoryStackLimit() {
      return 64;
   }

   public void markDirty() {
      super.markDirty();
      this.this$0.onCraftMatrixChanged(this);
   }
}
