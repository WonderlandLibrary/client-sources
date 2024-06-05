package net;

import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.item.ItemStack;

public class a61 {
   public static ItemStack a(IBehaviorDispenseItem var0, IBlockSource var1, ItemStack var2) {
      return var0.dispense(var1, var2);
   }
}
