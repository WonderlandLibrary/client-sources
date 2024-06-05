package net;

import net.azi;

// $FF: synthetic class
class ahe {
   static final int[] a = new int[azi.values().length];

   static {
      try {
         a[azi.ERROR.ordinal()] = 1;
      } catch (NoSuchFieldError var4) {
         ;
      }

      try {
         a[azi.WARNING.ordinal()] = 2;
      } catch (NoSuchFieldError var3) {
         ;
      }

      try {
         a[azi.SUCCESS.ordinal()] = 3;
      } catch (NoSuchFieldError var2) {
         ;
      }

      try {
         a[azi.INFO.ordinal()] = 4;
      } catch (NoSuchFieldError var1) {
         ;
      }

   }
}
