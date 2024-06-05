package net.minecraft.entity.passive;

import java.util.Random;
import net.minecraft.entity.passive.EntityVillager$ITradeList;
import net.minecraft.entity.passive.EntityVillager$PriceInfo;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;

class EntityVillager$ItemAndEmeraldToItem implements EntityVillager$ITradeList {
   public ItemStack field_179411_a;
   public EntityVillager$PriceInfo field_179409_b;
   public ItemStack field_179410_c;
   public EntityVillager$PriceInfo field_179408_d;

   public EntityVillager$ItemAndEmeraldToItem(Item var1, EntityVillager$PriceInfo var2, Item var3, EntityVillager$PriceInfo var4) {
      this.field_179411_a = new ItemStack(var1);
      this.field_179409_b = var2;
      this.field_179410_c = new ItemStack(var3);
      this.field_179408_d = var4;
   }

   public void modifyMerchantRecipeList(MerchantRecipeList var1, Random var2) {
      int var3 = 1;
      if(this.field_179409_b != null) {
         var3 = this.field_179409_b.getPrice(var2);
      }

      int var4 = 1;
      if(this.field_179408_d != null) {
         var4 = this.field_179408_d.getPrice(var2);
      }

      var1.add(new MerchantRecipe(new ItemStack(this.field_179411_a.getItem(), var3, this.field_179411_a.getMetadata()), new ItemStack(Items.emerald), new ItemStack(this.field_179410_c.getItem(), var4, this.field_179410_c.getMetadata())));
   }
}
