package net.minecraft.entity.passive;

import java.util.Random;
import net.minecraft.entity.passive.EntityVillager$ITradeList;
import net.minecraft.entity.passive.EntityVillager$PriceInfo;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;

class EntityVillager$ListItemForEmeralds implements EntityVillager$ITradeList {
   public ItemStack field_179403_a;
   public EntityVillager$PriceInfo field_179402_b;

   public EntityVillager$ListItemForEmeralds(Item var1, EntityVillager$PriceInfo var2) {
      this.field_179403_a = new ItemStack(var1);
      this.field_179402_b = var2;
   }

   public EntityVillager$ListItemForEmeralds(ItemStack var1, EntityVillager$PriceInfo var2) {
      this.field_179403_a = var1;
      this.field_179402_b = var2;
   }

   public void modifyMerchantRecipeList(MerchantRecipeList var1, Random var2) {
      int var3 = 1;
      if(this.field_179402_b != null) {
         var3 = this.field_179402_b.getPrice(var2);
      }

      ItemStack var4 = new ItemStack(Items.emerald, 1, 0);
      ItemStack var5 = new ItemStack(this.field_179403_a.getItem(), -var3, this.field_179403_a.getMetadata());
      var1.add(new MerchantRecipe(var4, var5));
   }
}
