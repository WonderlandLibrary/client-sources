package net;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class _C {
   public static boolean a(IRecipe var0, InventoryCrafting var1, World var2) {
      return var0.matches(var1, var2);
   }

   public static ItemStack b(IRecipe var0, InventoryCrafting var1) {
      return var0.getCraftingResult(var1);
   }

   public static ItemStack[] a(IRecipe var0, InventoryCrafting var1) {
      return var0.getRemainingItems(var1);
   }

   public static int b(IRecipe var0) {
      return var0.getRecipeSize();
   }

   public static ItemStack a(IRecipe var0) {
      return var0.getRecipeOutput();
   }
}
