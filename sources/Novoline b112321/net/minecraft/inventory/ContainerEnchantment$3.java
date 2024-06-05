package net.minecraft.inventory;

import net.minecraft.init.Items;
import net.minecraft.inventory.ContainerEnchantment;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;

class ContainerEnchantment$3 extends Slot {
   final ContainerEnchantment this$0;

   ContainerEnchantment$3(ContainerEnchantment var1, IInventory var2, int var3, int var4, int var5) {
      super(var2, var3, var4, var5);
      this.this$0 = var1;
   }

   public boolean isItemValid(ItemStack var1) {
      return var1.getItem() == Items.dye && EnumDyeColor.byDyeDamage(var1.getMetadata()) == EnumDyeColor.BLUE;
   }
}
