package net.minecraft.entity.passive;

import java.util.Random;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.passive.EntityVillager$ITradeList;
import net.minecraft.entity.passive.EntityVillager$PriceInfo;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;

class EntityVillager$ListEnchantedItemForEmeralds implements EntityVillager$ITradeList {
   public ItemStack field_179407_a;
   public EntityVillager$PriceInfo field_179406_b;

   public EntityVillager$ListEnchantedItemForEmeralds(Item var1, EntityVillager$PriceInfo var2) {
      this.field_179407_a = new ItemStack(var1);
      this.field_179406_b = var2;
   }

   public void modifyMerchantRecipeList(MerchantRecipeList var1, Random var2) {
      int var3 = 1;
      if(this.field_179406_b != null) {
         var3 = this.field_179406_b.getPrice(var2);
      }

      ItemStack var4 = new ItemStack(Items.emerald, var3, 0);
      ItemStack var5 = new ItemStack(this.field_179407_a.getItem(), 1, this.field_179407_a.getMetadata());
      var5 = EnchantmentHelper.addRandomEnchantment(var2, var5, 5 + var2.nextInt(15));
      var1.add(new MerchantRecipe(var4, var5));
   }
}
