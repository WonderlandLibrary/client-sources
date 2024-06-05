package net.minecraft.client.gui;

import net.minecraft.util.EnumFacing;

final class GuiOverlayDebug$GuiOverlayDebug$1 {
   static final int[] field_178907_a = new int[EnumFacing.values().length];
   private static final String b = "CL_00001955";

   static {
      try {
         field_178907_a[EnumFacing.NORTH.ordinal()] = 1;
      } catch (NoSuchFieldError var5) {
         ;
      }

      try {
         field_178907_a[EnumFacing.SOUTH.ordinal()] = 2;
      } catch (NoSuchFieldError var4) {
         ;
      }

      try {
         field_178907_a[EnumFacing.WEST.ordinal()] = 3;
      } catch (NoSuchFieldError var3) {
         ;
      }

      try {
         field_178907_a[EnumFacing.EAST.ordinal()] = 4;
      } catch (NoSuchFieldError var2) {
         ;
      }

   }
}
