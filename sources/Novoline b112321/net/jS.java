package net;

import cc.novoline.modules.configurations.property.AbstractNumberProperty;
import cc.novoline.modules.configurations.property.object.IntProperty;

public class jS {
   private static int[] b;

   public static AbstractNumberProperty a(IntProperty var0, Number var1) {
      return var0.minimum(var1);
   }

   public static AbstractNumberProperty b(IntProperty var0, Number var1) {
      return var0.maximum(var1);
   }

   public static Object a(IntProperty var0) {
      return var0.get();
   }

   public static void c(IntProperty var0, Number var1) {
      var0.set(var1);
   }

   public static IntProperty a(Integer var0) {
      return IntProperty.of(var0);
   }

   public static IntProperty c() {
      return IntProperty.create();
   }

   public static Number b(IntProperty var0) {
      return var0.getMaximum();
   }

   public static void b(int[] var0) {
      b = var0;
   }

   public static int[] b() {
      return b;
   }

   static {
      if(b() != null) {
         b(new int[2]);
      }

   }
}
