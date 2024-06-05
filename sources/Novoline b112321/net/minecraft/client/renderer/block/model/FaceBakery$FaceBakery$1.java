package net.minecraft.client.renderer.block.model;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing$Axis;

final class FaceBakery$FaceBakery$1 {
   static final int[] field_178400_a = new int[EnumFacing.values().length];
   static final int[] field_178399_b = new int[EnumFacing$Axis.values().length];

   static {
      try {
         field_178399_b[EnumFacing$Axis.X.ordinal()] = 1;
         field_178399_b[EnumFacing$Axis.Y.ordinal()] = 2;
         field_178399_b[EnumFacing$Axis.Z.ordinal()] = 3;
         field_178400_a[EnumFacing.DOWN.ordinal()] = 1;
         field_178400_a[EnumFacing.UP.ordinal()] = 2;
         field_178400_a[EnumFacing.NORTH.ordinal()] = 3;
         field_178400_a[EnumFacing.SOUTH.ordinal()] = 4;
         field_178400_a[EnumFacing.WEST.ordinal()] = 5;
         field_178400_a[EnumFacing.EAST.ordinal()] = 6;
      } catch (NoSuchFieldError var1) {
         ;
      }

   }
}
