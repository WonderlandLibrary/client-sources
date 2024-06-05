package net.minecraft.item;

import java.util.List;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFishFood$FishType;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemFishFood extends ItemFood {
   private final boolean cooked;

   public ItemFishFood(boolean var1) {
      super(0, 0.0F, false);
      this.cooked = var1;
   }

   public int getHealAmount(ItemStack var1) {
      ItemFishFood$FishType var2 = ItemFishFood$FishType.byItemStack(var1);
      return this.cooked && var2.canCook()?var2.getCookedHealAmount():var2.getUncookedHealAmount();
   }

   public float getSaturationModifier(ItemStack var1) {
      ItemFishFood$FishType var2 = ItemFishFood$FishType.byItemStack(var1);
      return this.cooked && var2.canCook()?var2.getCookedSaturationModifier():var2.getUncookedSaturationModifier();
   }

   public String getPotionEffect(ItemStack var1) {
      return ItemFishFood$FishType.byItemStack(var1) == ItemFishFood$FishType.PUFFERFISH?"+0-1+2+3+13&4-4":null;
   }

   protected void onFoodEaten(ItemStack var1, World var2, EntityPlayer var3) {
      ItemFishFood$FishType var4 = ItemFishFood$FishType.byItemStack(var1);
      if(var4 == ItemFishFood$FishType.PUFFERFISH) {
         var3.addPotionEffect(new PotionEffect(Potion.poison.getId(), 1200, 3));
         var3.addPotionEffect(new PotionEffect(Potion.hunger.getId(), 300, 2));
         var3.addPotionEffect(new PotionEffect(Potion.confusion.getId(), 300, 1));
      }

      super.onFoodEaten(var1, var2, var3);
   }

   public void getSubItems(Item var1, CreativeTabs var2, List var3) {
      for(ItemFishFood$FishType var7 : ItemFishFood$FishType.values()) {
         if(!this.cooked || var7.canCook()) {
            var3.add(new ItemStack(this, 1, var7.getMetadata()));
         }
      }

   }

   public String getUnlocalizedName(ItemStack var1) {
      ItemFishFood$FishType var2 = ItemFishFood$FishType.byItemStack(var1);
      return this.getUnlocalizedName() + "." + var2.getUnlocalizedName() + "." + (this.cooked && var2.canCook()?"cooked":"raw");
   }
}
