package net.minecraft.client.renderer;

import net.minecraft.item.EnumAction;

final class ItemRenderer$ItemRenderer$1 {
   static final int[] field_178094_a = new int[EnumAction.values().length];
   private static final String b = "CL_00002537";

   static {
      try {
         field_178094_a[EnumAction.NONE.ordinal()] = 1;
      } catch (NoSuchFieldError var6) {
         ;
      }

      try {
         field_178094_a[EnumAction.EAT.ordinal()] = 2;
      } catch (NoSuchFieldError var5) {
         ;
      }

      try {
         field_178094_a[EnumAction.DRINK.ordinal()] = 3;
      } catch (NoSuchFieldError var4) {
         ;
      }

      try {
         field_178094_a[EnumAction.BLOCK.ordinal()] = 4;
      } catch (NoSuchFieldError var3) {
         ;
      }

      try {
         field_178094_a[EnumAction.BOW.ordinal()] = 5;
      } catch (NoSuchFieldError var2) {
         ;
      }

   }
}
