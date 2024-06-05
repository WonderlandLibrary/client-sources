package net;

import viaversion.viabackwards.protocol.protocol1_12_2to1_13.data.PaintingMapping;

public class Ep {
   public static String a(int var0) {
      return PaintingMapping.getStringId(var0);
   }

   public static void a() {
      PaintingMapping.init();
   }
}
