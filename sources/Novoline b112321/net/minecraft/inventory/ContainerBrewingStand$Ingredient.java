package net.minecraft.inventory;

import net.minecraft.inventory.ContainerBrewingStand;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

class ContainerBrewingStand$Ingredient extends Slot {
   final ContainerBrewingStand this$0;

   public ContainerBrewingStand$Ingredient(ContainerBrewingStand var1, IInventory var2, int var3, int var4, int var5) {
      super(var2, var3, var4, var5);
      this.this$0 = var1;
   }

   public boolean isItemValid(ItemStack var1) {
      return var1.getItem().isPotionIngredient(var1);
   }

   public int getSlotStackLimit() {
      return 64;
   }
}
