package net.minecraft.inventory;

import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

class ContainerPlayer$1 extends Slot {
   final int val$k_f;
   final ContainerPlayer this$0;

   ContainerPlayer$1(ContainerPlayer var1, IInventory var2, int var3, int var4, int var5, int var6) {
      super(var2, var3, var4, var5);
      this.this$0 = var1;
      this.val$k_f = var6;
   }

   public int getSlotStackLimit() {
      return 1;
   }

   public boolean isItemValid(ItemStack var1) {
      return false;
   }

   public String getSlotTexture() {
      return ItemArmor.EMPTY_SLOT_NAMES[this.val$k_f];
   }
}
