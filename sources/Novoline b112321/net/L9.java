package net;

import cc.novoline.modules.configurations.property.AbstractNumberProperty;
import cc.novoline.modules.configurations.property.object.FloatProperty;

public class L9 {
   public static AbstractNumberProperty a(FloatProperty var0, Number var1) {
      return var0.minimum(var1);
   }

   public static AbstractNumberProperty c(FloatProperty var0, Number var1) {
      return var0.maximum(var1);
   }

   public static Object a(FloatProperty var0) {
      return var0.get();
   }

   public static FloatProperty a(Float var0) {
      return FloatProperty.of(var0);
   }

   public static FloatProperty a() {
      return FloatProperty.create();
   }

   public static void b(FloatProperty var0, Number var1) {
      var0.set(var1);
   }
}
