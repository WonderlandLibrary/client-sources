package net;

import net.Ch;

// $FF: synthetic class
class a0l {
   static final int[] a = new int[Ch.values().length];

   static {
      try {
         a[Ch.BLOCK.ordinal()] = 1;
      } catch (NoSuchFieldError var4) {
         ;
      }

      try {
         a[Ch.ITEM.ordinal()] = 2;
      } catch (NoSuchFieldError var3) {
         ;
      }

      try {
         a[Ch.ENTITY.ordinal()] = 3;
      } catch (NoSuchFieldError var2) {
         ;
      }

      try {
         a[Ch.FLUID.ordinal()] = 4;
      } catch (NoSuchFieldError var1) {
         ;
      }

   }
}
