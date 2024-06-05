package net;

import viaversion.viaversion.api.minecraft.metadata.types.MetaType1_8;
import viaversion.viaversion.api.type.Type;

public class akg {
   private static int[] b;

   public static MetaType1_8 a(int var0) {
      return MetaType1_8.byId(var0);
   }

   public static Type a(MetaType1_8 var0) {
      return var0.getType();
   }

   public static void b(int[] var0) {
      b = var0;
   }

   public static int[] b() {
      return b;
   }

   static {
      if(b() != null) {
         b(new int[5]);
      }

   }
}
