package net.minecraft.client.renderer;

import net.minecraft.client.renderer.vertex.VertexFormatElement$EnumType;

final class WorldRenderer$WorldRenderer$2 {
   static final int[] field_181661_a = new int[VertexFormatElement$EnumType.values().length];
   private static final String b = "CL_00002569";

   static {
      try {
         field_181661_a[VertexFormatElement$EnumType.FLOAT.ordinal()] = 1;
      } catch (NoSuchFieldError var8) {
         ;
      }

      try {
         field_181661_a[VertexFormatElement$EnumType.UINT.ordinal()] = 2;
      } catch (NoSuchFieldError var7) {
         ;
      }

      try {
         field_181661_a[VertexFormatElement$EnumType.INT.ordinal()] = 3;
      } catch (NoSuchFieldError var6) {
         ;
      }

      try {
         field_181661_a[VertexFormatElement$EnumType.USHORT.ordinal()] = 4;
      } catch (NoSuchFieldError var5) {
         ;
      }

      try {
         field_181661_a[VertexFormatElement$EnumType.SHORT.ordinal()] = 5;
      } catch (NoSuchFieldError var4) {
         ;
      }

      try {
         field_181661_a[VertexFormatElement$EnumType.UBYTE.ordinal()] = 6;
      } catch (NoSuchFieldError var3) {
         ;
      }

      try {
         field_181661_a[VertexFormatElement$EnumType.BYTE.ordinal()] = 7;
      } catch (NoSuchFieldError var2) {
         ;
      }

   }
}
