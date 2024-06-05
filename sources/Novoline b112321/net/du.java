package net;

import net.minecraft.item.EnumAction;

// $FF: synthetic class
class du {
   static final int[] a = new int[EnumAction.values().length];

   static {
      try {
         a[EnumAction.NONE.ordinal()] = 1;
      } catch (NoSuchFieldError var5) {
         ;
      }

      try {
         a[EnumAction.EAT.ordinal()] = 2;
      } catch (NoSuchFieldError var4) {
         ;
      }

      try {
         a[EnumAction.DRINK.ordinal()] = 3;
      } catch (NoSuchFieldError var3) {
         ;
      }

      try {
         a[EnumAction.BLOCK.ordinal()] = 4;
      } catch (NoSuchFieldError var2) {
         ;
      }

      try {
         a[EnumAction.BOW.ordinal()] = 5;
      } catch (NoSuchFieldError var1) {
         ;
      }

   }
}
