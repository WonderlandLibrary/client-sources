package net;

import net.Bc;
import net.a6d;

// $FF: synthetic class
class ayU {
   static final int[] b;
   static final int[] a = new int[a6d.values().length];

   static {
      try {
         a[a6d.SLIDER.ordinal()] = 1;
      } catch (NoSuchFieldError var11) {
         ;
      }

      try {
         a[a6d.GUI.ordinal()] = 2;
      } catch (NoSuchFieldError var10) {
         ;
      }

      try {
         a[a6d.CHECKBOX.ordinal()] = 3;
      } catch (NoSuchFieldError var9) {
         ;
      }

      try {
         a[a6d.COMBOBOX.ordinal()] = 4;
      } catch (NoSuchFieldError var8) {
         ;
      }

      try {
         a[a6d.SELECTBOX.ordinal()] = 5;
      } catch (NoSuchFieldError var7) {
         ;
      }

      try {
         a[a6d.TEXTBOX.ordinal()] = 6;
      } catch (NoSuchFieldError var6) {
         ;
      }

      try {
         a[a6d.COLOR_PICKER.ordinal()] = 7;
      } catch (NoSuchFieldError var5) {
         ;
      }

      try {
         a[a6d.BINDABLE.ordinal()] = 8;
      } catch (NoSuchFieldError var4) {
         ;
      }

      b = new int[Bc.values().length];

      try {
         b[Bc.HUE.ordinal()] = 1;
      } catch (NoSuchFieldError var3) {
         ;
      }

      try {
         b[Bc.SATURATION.ordinal()] = 2;
      } catch (NoSuchFieldError var2) {
         ;
      }

      try {
         b[Bc.BRIGHTNESS.ordinal()] = 3;
      } catch (NoSuchFieldError var1) {
         ;
      }

   }
}
