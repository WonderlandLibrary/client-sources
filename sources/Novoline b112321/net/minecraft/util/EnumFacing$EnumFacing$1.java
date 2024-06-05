package net.minecraft.util;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing$Axis;
import net.minecraft.util.EnumFacing$Plane;

final class EnumFacing$EnumFacing$1 {
   static final int[] field_179515_a;
   static final int[] field_179513_b;
   static final int[] field_179514_c = new int[EnumFacing$Plane.values().length];
   private static final String d = "CL_00002322";

   static {
      try {
         field_179514_c[EnumFacing$Plane.HORIZONTAL.ordinal()] = 1;
      } catch (NoSuchFieldError var12) {
         ;
      }

      try {
         field_179514_c[EnumFacing$Plane.VERTICAL.ordinal()] = 2;
      } catch (NoSuchFieldError var11) {
         ;
      }

      field_179513_b = new int[EnumFacing.values().length];

      try {
         field_179513_b[EnumFacing.NORTH.ordinal()] = 1;
      } catch (NoSuchFieldError var10) {
         ;
      }

      try {
         field_179513_b[EnumFacing.EAST.ordinal()] = 2;
      } catch (NoSuchFieldError var9) {
         ;
      }

      try {
         field_179513_b[EnumFacing.SOUTH.ordinal()] = 3;
      } catch (NoSuchFieldError var8) {
         ;
      }

      try {
         field_179513_b[EnumFacing.WEST.ordinal()] = 4;
      } catch (NoSuchFieldError var7) {
         ;
      }

      try {
         field_179513_b[EnumFacing.UP.ordinal()] = 5;
      } catch (NoSuchFieldError var6) {
         ;
      }

      try {
         field_179513_b[EnumFacing.DOWN.ordinal()] = 6;
      } catch (NoSuchFieldError var5) {
         ;
      }

      field_179515_a = new int[EnumFacing$Axis.values().length];

      try {
         field_179515_a[EnumFacing$Axis.X.ordinal()] = 1;
      } catch (NoSuchFieldError var4) {
         ;
      }

      try {
         field_179515_a[EnumFacing$Axis.Y.ordinal()] = 2;
      } catch (NoSuchFieldError var3) {
         ;
      }

      try {
         field_179515_a[EnumFacing$Axis.Z.ordinal()] = 3;
      } catch (NoSuchFieldError var2) {
         ;
      }

   }
}
