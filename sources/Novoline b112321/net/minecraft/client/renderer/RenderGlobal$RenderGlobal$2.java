package net.minecraft.client.renderer;

import net.minecraft.client.renderer.vertex.VertexFormatElement$EnumUsage;

final class RenderGlobal$RenderGlobal$2 {
   static final int[] field_178037_a = new int[VertexFormatElement$EnumUsage.values().length];

   static {
      try {
         field_178037_a[VertexFormatElement$EnumUsage.POSITION.ordinal()] = 1;
         field_178037_a[VertexFormatElement$EnumUsage.UV.ordinal()] = 2;
         field_178037_a[VertexFormatElement$EnumUsage.COLOR.ordinal()] = 3;
      } catch (NoSuchFieldError var1) {
         ;
      }

   }
}
