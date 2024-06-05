package net.minecraft.entity.passive;

import java.util.Random;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.entity.passive.EntityVillager$ITradeList;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;

class EntityVillager$ListEnchantedBookForEmeralds implements EntityVillager$ITradeList {
   public void modifyMerchantRecipeList(MerchantRecipeList var1, Random var2) {
      Enchantment var3 = Enchantment.enchantmentsBookList[var2.nextInt(Enchantment.enchantmentsBookList.length)];
      int var4 = MathHelper.getRandomIntegerInRange(var2, var3.getMinLevel(), var3.getMaxLevel());
      ItemStack var5 = Items.enchanted_book.getEnchantedItemStack(new EnchantmentData(var3, var4));
      int var6 = 2 + var2.nextInt(5 + var4 * 10) + 3 * var4;
      if(var6 > 64) {
         var6 = 64;
      }

      var1.add(new MerchantRecipe(new ItemStack(Items.book), new ItemStack(Items.emerald, var6), var5));
   }
}
