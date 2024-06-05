package net;

import net.minecraft.util.EnumFacing;

// $FF: synthetic class
class D0 {
   static final int[] a = new int[EnumFacing.values().length];

   static {
      try {
         a[EnumFacing.EAST.ordinal()] = 1;
      } catch (NoSuchFieldError var4) {
         ;
      }

      try {
         a[EnumFacing.WEST.ordinal()] = 2;
      } catch (NoSuchFieldError var3) {
         ;
      }

      try {
         a[EnumFacing.SOUTH.ordinal()] = 3;
      } catch (NoSuchFieldError var2) {
         ;
      }

      try {
         a[EnumFacing.NORTH.ordinal()] = 4;
      } catch (NoSuchFieldError var1) {
         ;
      }

   }
}
