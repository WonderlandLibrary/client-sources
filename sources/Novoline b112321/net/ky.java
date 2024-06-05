package net;

import net.am5;

// $FF: synthetic class
class ky {
   static final int[] a = new int[am5.values().length];

   static {
      try {
         a[am5.LIST.ordinal()] = 1;
      } catch (NoSuchFieldError var2) {
         ;
      }

      try {
         a[am5.MAP.ordinal()] = 2;
      } catch (NoSuchFieldError var1) {
         ;
      }

   }
}
