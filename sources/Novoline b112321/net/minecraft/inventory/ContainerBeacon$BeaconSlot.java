package net.minecraft.inventory;

import net.minecraft.init.Items;
import net.minecraft.inventory.ContainerBeacon;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

class ContainerBeacon$BeaconSlot extends Slot {
   final ContainerBeacon this$0;

   public ContainerBeacon$BeaconSlot(ContainerBeacon var1, IInventory var2, int var3, int var4, int var5) {
      super(var2, var3, var4, var5);
      this.this$0 = var1;
   }

   public boolean isItemValid(ItemStack var1) {
      return var1.getItem() == Items.emerald || var1.getItem() == Items.diamond || var1.getItem() == Items.gold_ingot || var1.getItem() == Items.iron_ingot;
   }

   public int getSlotStackLimit() {
      return 1;
   }
}
