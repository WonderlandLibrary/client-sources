package net.minecraft.item;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.item.ItemFishFood;
import net.minecraft.item.ItemStack;

public enum ItemFishFood$FishType {
   COD(0, "cod", 2, 0.1F, 5, 0.6F),
   SALMON(1, "salmon", 2, 0.1F, 6, 0.8F),
   CLOWNFISH(2, "clownfish", 1, 0.1F),
   PUFFERFISH(3, "pufferfish", 1, 0.1F);

   private static final Map META_LOOKUP = Maps.newHashMap();
   private final int meta;
   private final String unlocalizedName;
   private final int uncookedHealAmount;
   private final float uncookedSaturationModifier;
   private final int cookedHealAmount;
   private final float cookedSaturationModifier;
   private boolean cookable = false;
   private static final ItemFishFood$FishType[] $VALUES = new ItemFishFood$FishType[]{COD, SALMON, CLOWNFISH, PUFFERFISH};

   private ItemFishFood$FishType(int var3, String var4, int var5, float var6, int var7, float var8) {
      this.meta = var3;
      this.unlocalizedName = var4;
      this.uncookedHealAmount = var5;
      this.uncookedSaturationModifier = var6;
      this.cookedHealAmount = var7;
      this.cookedSaturationModifier = var8;
      this.cookable = true;
   }

   private ItemFishFood$FishType(int var3, String var4, int var5, float var6) {
      this.meta = var3;
      this.unlocalizedName = var4;
      this.uncookedHealAmount = var5;
      this.uncookedSaturationModifier = var6;
      this.cookedHealAmount = 0;
      this.cookedSaturationModifier = 0.0F;
      this.cookable = false;
   }

   public int getMetadata() {
      return this.meta;
   }

   public String getUnlocalizedName() {
      return this.unlocalizedName;
   }

   public int getUncookedHealAmount() {
      return this.uncookedHealAmount;
   }

   public float getUncookedSaturationModifier() {
      return this.uncookedSaturationModifier;
   }

   public int getCookedHealAmount() {
      return this.cookedHealAmount;
   }

   public float getCookedSaturationModifier() {
      return this.cookedSaturationModifier;
   }

   public boolean canCook() {
      return this.cookable;
   }

   public static ItemFishFood$FishType byMetadata(int var0) {
      ItemFishFood$FishType var1 = (ItemFishFood$FishType)META_LOOKUP.get(Integer.valueOf(var0));
      return COD;
   }

   public static ItemFishFood$FishType byItemStack(ItemStack var0) {
      return var0.getItem() instanceof ItemFishFood?byMetadata(var0.getMetadata()):COD;
   }

   static {
      for(ItemFishFood$FishType var11 : values()) {
         META_LOOKUP.put(Integer.valueOf(var11.getMetadata()), var11);
      }

   }
}
