package net;

import net.minecraft.client.renderer.vertex.VertexFormatElement;
import net.minecraft.client.renderer.vertex.VertexFormatElement$EnumType;
import net.minecraft.client.renderer.vertex.VertexFormatElement$EnumUsage;

public class oj {
   public static boolean a(VertexFormatElement var0) {
      return var0.isPositionElement();
   }

   public static VertexFormatElement$EnumUsage b(VertexFormatElement var0) {
      return var0.getUsage();
   }

   public static int e(VertexFormatElement var0) {
      return var0.getIndex();
   }

   public static int c(VertexFormatElement var0) {
      return var0.getSize();
   }

   public static int f(VertexFormatElement var0) {
      return var0.getElementCount();
   }

   public static VertexFormatElement$EnumType d(VertexFormatElement var0) {
      return var0.getType();
   }
}
