package net;

import com.typesafe.config.ConfigValueType;

// $FF: synthetic class
class a25 {
   static final int[] a = new int[ConfigValueType.values().length];

   static {
      try {
         a[ConfigValueType.OBJECT.ordinal()] = 1;
      } catch (NoSuchFieldError var3) {
         ;
      }

      try {
         a[ConfigValueType.LIST.ordinal()] = 2;
      } catch (NoSuchFieldError var2) {
         ;
      }

      try {
         a[ConfigValueType.NULL.ordinal()] = 3;
      } catch (NoSuchFieldError var1) {
         ;
      }

   }
}
