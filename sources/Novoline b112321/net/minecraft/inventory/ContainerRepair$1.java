package net.minecraft.inventory;

import net.minecraft.inventory.ContainerRepair;
import net.minecraft.inventory.InventoryBasic;

class ContainerRepair$1 extends InventoryBasic {
   final ContainerRepair this$0;

   ContainerRepair$1(ContainerRepair var1, String var2, boolean var3, int var4) {
      super(var2, var3, var4);
      this.this$0 = var1;
   }

   public void markDirty() {
      super.markDirty();
      this.this$0.onCraftMatrixChanged(this);
   }
}
