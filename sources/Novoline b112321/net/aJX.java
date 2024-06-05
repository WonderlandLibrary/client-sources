package net;

import java.util.Map;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

public class aJX {
   public static FurnaceRecipes a() {
      return FurnaceRecipes.instance();
   }

   public static float b(FurnaceRecipes var0, ItemStack var1) {
      return var0.getSmeltingExperience(var1);
   }

   public static ItemStack a(FurnaceRecipes var0, ItemStack var1) {
      return var0.getSmeltingResult(var1);
   }

   public static Map a(FurnaceRecipes var0) {
      return var0.getSmeltingList();
   }
}
