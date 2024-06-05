package net;

import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.realms.RealmsVertexFormat;
import net.minecraft.realms.RealmsVertexFormatElement;

public class jq {
   public static RealmsVertexFormat a(RealmsVertexFormat var0, RealmsVertexFormatElement var1) {
      return var0.addElement(var1);
   }

   public static VertexFormat a(RealmsVertexFormat var0) {
      return var0.getVertexFormat();
   }
}
