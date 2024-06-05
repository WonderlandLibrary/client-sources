package net.minecraft.client.renderer.entity.layers;

import net.minecraft.item.ItemArmor$ArmorMaterial;

final class LayerArmorBase$LayerArmorBase$1 {
   static final int[] field_178747_a = new int[ItemArmor$ArmorMaterial.values().length];
   private static final String b = "CL_00002427";

   static {
      try {
         field_178747_a[ItemArmor$ArmorMaterial.LEATHER.ordinal()] = 1;
      } catch (NoSuchFieldError var6) {
         ;
      }

      try {
         field_178747_a[ItemArmor$ArmorMaterial.CHAIN.ordinal()] = 2;
      } catch (NoSuchFieldError var5) {
         ;
      }

      try {
         field_178747_a[ItemArmor$ArmorMaterial.IRON.ordinal()] = 3;
      } catch (NoSuchFieldError var4) {
         ;
      }

      try {
         field_178747_a[ItemArmor$ArmorMaterial.GOLD.ordinal()] = 4;
      } catch (NoSuchFieldError var3) {
         ;
      }

      try {
         field_178747_a[ItemArmor$ArmorMaterial.DIAMOND.ordinal()] = 5;
      } catch (NoSuchFieldError var2) {
         ;
      }

   }
}
