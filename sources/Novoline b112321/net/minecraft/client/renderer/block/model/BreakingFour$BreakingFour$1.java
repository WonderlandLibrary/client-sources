package net.minecraft.client.renderer.block.model;

import net.minecraft.util.EnumFacing;

final class BreakingFour$BreakingFour$1 {
   static final int[] field_178419_a = new int[EnumFacing.values().length];
   private static final String a = "CL_00002491";

   static {
      try {
         field_178419_a[EnumFacing.DOWN.ordinal()] = 1;
      } catch (NoSuchFieldError var7) {
         ;
      }

      try {
         field_178419_a[EnumFacing.UP.ordinal()] = 2;
      } catch (NoSuchFieldError var6) {
         ;
      }

      try {
         field_178419_a[EnumFacing.NORTH.ordinal()] = 3;
      } catch (NoSuchFieldError var5) {
         ;
      }

      try {
         field_178419_a[EnumFacing.SOUTH.ordinal()] = 4;
      } catch (NoSuchFieldError var4) {
         ;
      }

      try {
         field_178419_a[EnumFacing.WEST.ordinal()] = 5;
      } catch (NoSuchFieldError var3) {
         ;
      }

      try {
         field_178419_a[EnumFacing.EAST.ordinal()] = 6;
      } catch (NoSuchFieldError var2) {
         ;
      }

   }
}
