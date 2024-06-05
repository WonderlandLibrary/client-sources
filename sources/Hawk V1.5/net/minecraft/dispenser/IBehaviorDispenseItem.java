package net.minecraft.dispenser;

import net.minecraft.item.ItemStack;

public interface IBehaviorDispenseItem {
   IBehaviorDispenseItem itemDispenseBehaviorProvider = new IBehaviorDispenseItem() {
      private static final String __OBFID = "CL_00001200";

      public ItemStack dispense(IBlockSource var1, ItemStack var2) {
         return var2;
      }
   };

   ItemStack dispense(IBlockSource var1, ItemStack var2);
}
