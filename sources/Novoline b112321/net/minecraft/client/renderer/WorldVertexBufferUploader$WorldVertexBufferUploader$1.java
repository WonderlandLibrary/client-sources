package net.minecraft.client.renderer;

import net.minecraft.client.renderer.vertex.VertexFormatElement$EnumUsage;

final class WorldVertexBufferUploader$WorldVertexBufferUploader$1 {
   static final int[] field_178958_a = new int[VertexFormatElement$EnumUsage.values().length];
   private static final String b = "CL_00002566";

   static {
      try {
         field_178958_a[VertexFormatElement$EnumUsage.POSITION.ordinal()] = 1;
      } catch (NoSuchFieldError var5) {
         ;
      }

      try {
         field_178958_a[VertexFormatElement$EnumUsage.UV.ordinal()] = 2;
      } catch (NoSuchFieldError var4) {
         ;
      }

      try {
         field_178958_a[VertexFormatElement$EnumUsage.COLOR.ordinal()] = 3;
      } catch (NoSuchFieldError var3) {
         ;
      }

      try {
         field_178958_a[VertexFormatElement$EnumUsage.NORMAL.ordinal()] = 4;
      } catch (NoSuchFieldError var2) {
         ;
      }

   }
}
