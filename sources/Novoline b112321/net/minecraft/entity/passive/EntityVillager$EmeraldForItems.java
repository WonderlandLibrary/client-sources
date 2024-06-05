package net.minecraft.entity.passive;

import java.util.Random;
import net.minecraft.entity.passive.EntityVillager$ITradeList;
import net.minecraft.entity.passive.EntityVillager$PriceInfo;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;

class EntityVillager$EmeraldForItems implements EntityVillager$ITradeList {
   public Item sellItem;
   public EntityVillager$PriceInfo price;

   public EntityVillager$EmeraldForItems(Item var1, EntityVillager$PriceInfo var2) {
      this.sellItem = var1;
      this.price = var2;
   }

   public void modifyMerchantRecipeList(MerchantRecipeList var1, Random var2) {
      int var3 = 1;
      if(this.price != null) {
         var3 = this.price.getPrice(var2);
      }

      var1.add(new MerchantRecipe(new ItemStack(this.sellItem, var3, 0), Items.emerald));
   }
}
