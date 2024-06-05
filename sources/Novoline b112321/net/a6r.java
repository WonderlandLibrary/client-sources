package net;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.village.MerchantRecipe;

public class a6r {
   public static boolean d(MerchantRecipe var0) {
      return var0.isRecipeDisabled();
   }

   public static void a(MerchantRecipe var0, int var1) {
      var0.increaseMaxTradeUses(var1);
   }

   public static void e(MerchantRecipe var0) {
      var0.incrementToolUses();
   }

   public static int a(MerchantRecipe var0) {
      return var0.getToolUses();
   }

   public static ItemStack k(MerchantRecipe var0) {
      return var0.getItemToBuy();
   }

   public static boolean g(MerchantRecipe var0) {
      return var0.getRewardsExp();
   }

   public static ItemStack i(MerchantRecipe var0) {
      return var0.getSecondItemToBuy();
   }

   public static ItemStack j(MerchantRecipe var0) {
      return var0.getItemToSell();
   }

   public static boolean h(MerchantRecipe var0) {
      return var0.hasSecondItemToBuy();
   }

   public static int f(MerchantRecipe var0) {
      return var0.getMaxTradeUses();
   }

   public static void b(MerchantRecipe var0) {
      var0.compensateToolUses();
   }

   public static NBTTagCompound c(MerchantRecipe var0) {
      return var0.writeToTags();
   }
}
