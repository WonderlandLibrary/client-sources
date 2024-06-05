package net.minecraft.item;

import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import net.minecraft.world.World;

public class ItemFishFood extends ItemFood {
   private static final String __OBFID = "CL_00000032";
   private final boolean cooked;

   public float getSaturationModifier(ItemStack var1) {
      ItemFishFood.FishType var2 = ItemFishFood.FishType.getFishTypeForItemStack(var1);
      return this.cooked && var2.getCookable() ? var2.getCookedSaturationModifier() : var2.getUncookedSaturationModifier();
   }

   public void getSubItems(Item var1, CreativeTabs var2, List var3) {
      ItemFishFood.FishType[] var4 = ItemFishFood.FishType.values();
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         ItemFishFood.FishType var7 = var4[var6];
         if (!this.cooked || var7.getCookable()) {
            var3.add(new ItemStack(this, 1, var7.getItemDamage()));
         }
      }

   }

   public String getUnlocalizedName(ItemStack var1) {
      ItemFishFood.FishType var2 = ItemFishFood.FishType.getFishTypeForItemStack(var1);
      return String.valueOf((new StringBuilder(String.valueOf(this.getUnlocalizedName()))).append(".").append(var2.getUnlocalizedNamePart()).append(".").append(this.cooked && var2.getCookable() ? "cooked" : "raw"));
   }

   protected void onFoodEaten(ItemStack var1, World var2, EntityPlayer var3) {
      ItemFishFood.FishType var4 = ItemFishFood.FishType.getFishTypeForItemStack(var1);
      if (var4 == ItemFishFood.FishType.PUFFERFISH) {
         var3.addPotionEffect(new PotionEffect(Potion.poison.id, 1200, 3));
         var3.addPotionEffect(new PotionEffect(Potion.hunger.id, 300, 2));
         var3.addPotionEffect(new PotionEffect(Potion.confusion.id, 300, 1));
      }

      super.onFoodEaten(var1, var2, var3);
   }

   public int getHealAmount(ItemStack var1) {
      ItemFishFood.FishType var2 = ItemFishFood.FishType.getFishTypeForItemStack(var1);
      return this.cooked && var2.getCookable() ? var2.getCookedHealAmount() : var2.getUncookedHealAmount();
   }

   public ItemFishFood(boolean var1) {
      super(0, 0.0F, false);
      this.cooked = var1;
   }

   public String getPotionEffect(ItemStack var1) {
      return ItemFishFood.FishType.getFishTypeForItemStack(var1) == ItemFishFood.FishType.PUFFERFISH ? PotionHelper.field_151423_m : null;
   }

   public static enum FishType {
      private static final String __OBFID = "CL_00000033";
      CLOWNFISH("CLOWNFISH", 2, 2, "clownfish", 1, 0.1F),
      PUFFERFISH("PUFFERFISH", 3, 3, "pufferfish", 1, 0.1F),
      SALMON("SALMON", 1, 1, "salmon", 2, 0.1F, 6, 0.8F);

      private final int itemDamage;
      private static final ItemFishFood.FishType[] $VALUES = new ItemFishFood.FishType[]{COD, SALMON, CLOWNFISH, PUFFERFISH};
      private final String unlocalizedNamePart;
      private static final ItemFishFood.FishType[] ENUM$VALUES = new ItemFishFood.FishType[]{COD, SALMON, CLOWNFISH, PUFFERFISH};
      private final int cookedHealAmount;
      private final float cookedSaturationModifier;
      COD("COD", 0, 0, "cod", 2, 0.1F, 5, 0.6F);

      private static final Map itemDamageToFishTypeMap = Maps.newHashMap();
      private boolean cookable = false;
      private final float uncookedSaturationModifier;
      private final int uncookedHealAmount;

      public int getItemDamage() {
         return this.itemDamage;
      }

      public static ItemFishFood.FishType getFishTypeForItemDamage(int var0) {
         ItemFishFood.FishType var1 = (ItemFishFood.FishType)itemDamageToFishTypeMap.get(var0);
         return var1 == null ? COD : var1;
      }

      public static ItemFishFood.FishType getFishTypeForItemStack(ItemStack var0) {
         return var0.getItem() instanceof ItemFishFood ? getFishTypeForItemDamage(var0.getMetadata()) : COD;
      }

      public float getCookedSaturationModifier() {
         return this.cookedSaturationModifier;
      }

      public float getUncookedSaturationModifier() {
         return this.uncookedSaturationModifier;
      }

      public int getUncookedHealAmount() {
         return this.uncookedHealAmount;
      }

      static {
         ItemFishFood.FishType[] var0 = values();
         int var1 = var0.length;

         for(int var2 = 0; var2 < var1; ++var2) {
            ItemFishFood.FishType var3 = var0[var2];
            itemDamageToFishTypeMap.put(var3.getItemDamage(), var3);
         }

      }

      public boolean getCookable() {
         return this.cookable;
      }

      private FishType(String var3, int var4, int var5, String var6, int var7, float var8, int var9, float var10) {
         this.itemDamage = var5;
         this.unlocalizedNamePart = var6;
         this.uncookedHealAmount = var7;
         this.uncookedSaturationModifier = var8;
         this.cookedHealAmount = var9;
         this.cookedSaturationModifier = var10;
         this.cookable = true;
      }

      public int getCookedHealAmount() {
         return this.cookedHealAmount;
      }

      public String getUnlocalizedNamePart() {
         return this.unlocalizedNamePart;
      }

      private FishType(String var3, int var4, int var5, String var6, int var7, float var8) {
         this.itemDamage = var5;
         this.unlocalizedNamePart = var6;
         this.uncookedHealAmount = var7;
         this.uncookedSaturationModifier = var8;
         this.cookedHealAmount = 0;
         this.cookedSaturationModifier = 0.0F;
         this.cookable = false;
      }
   }
}
