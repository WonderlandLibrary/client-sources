package net;

import net.minecraft.item.ItemFishFood$FishType;
import net.minecraft.item.ItemStack;

public class aaw {
   public static int e(ItemFishFood$FishType var0) {
      return var0.getMetadata();
   }

   public static boolean f(ItemFishFood$FishType var0) {
      return var0.canCook();
   }

   public static ItemFishFood$FishType a(ItemStack var0) {
      return ItemFishFood$FishType.byItemStack(var0);
   }

   public static int b(ItemFishFood$FishType var0) {
      return var0.getCookedHealAmount();
   }

   public static int c(ItemFishFood$FishType var0) {
      return var0.getUncookedHealAmount();
   }

   public static float a(ItemFishFood$FishType var0) {
      return var0.getCookedSaturationModifier();
   }

   public static float d(ItemFishFood$FishType var0) {
      return var0.getUncookedSaturationModifier();
   }
}
