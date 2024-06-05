package net;

import net.a6d;
import net.z6;

// $FF: synthetic class
class abX {
   static final int[] b;
   static final int[] a = new int[a6d.values().length];

   static {
      try {
         a[a6d.COMBOBOX.ordinal()] = 1;
      } catch (NoSuchFieldError var5) {
         ;
      }

      try {
         a[a6d.SELECTBOX.ordinal()] = 2;
      } catch (NoSuchFieldError var4) {
         ;
      }

      try {
         a[a6d.TEXTBOX.ordinal()] = 3;
      } catch (NoSuchFieldError var3) {
         ;
      }

      b = new int[z6.values().length];

      try {
         b[z6.DOWN.ordinal()] = 1;
      } catch (NoSuchFieldError var2) {
         ;
      }

      try {
         b[z6.UP.ordinal()] = 2;
      } catch (NoSuchFieldError var1) {
         ;
      }

   }
}
