package net.minecraft.client.renderer;

import net.minecraft.util.EnumFacing;

final class BlockModelRenderer$BlockModelRenderer$1 {
   static final int[] field_178290_a = new int[EnumFacing.values().length];
   private static final String b = "CL_00002517";

   static {
      try {
         field_178290_a[EnumFacing.DOWN.ordinal()] = 1;
      } catch (NoSuchFieldError var7) {
         ;
      }

      try {
         field_178290_a[EnumFacing.UP.ordinal()] = 2;
      } catch (NoSuchFieldError var6) {
         ;
      }

      try {
         field_178290_a[EnumFacing.NORTH.ordinal()] = 3;
      } catch (NoSuchFieldError var5) {
         ;
      }

      try {
         field_178290_a[EnumFacing.SOUTH.ordinal()] = 4;
      } catch (NoSuchFieldError var4) {
         ;
      }

      try {
         field_178290_a[EnumFacing.WEST.ordinal()] = 5;
      } catch (NoSuchFieldError var3) {
         ;
      }

      try {
         field_178290_a[EnumFacing.EAST.ordinal()] = 6;
      } catch (NoSuchFieldError var2) {
         ;
      }

   }
}
