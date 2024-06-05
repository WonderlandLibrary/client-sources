package net;

import cc.novoline.modules.configurations.property.AbstractNumberProperty;
import cc.novoline.modules.configurations.property.object.DoubleProperty;

public class ax4 {
   public static AbstractNumberProperty c(DoubleProperty var0, Number var1) {
      return var0.minimum(var1);
   }

   public static AbstractNumberProperty b(DoubleProperty var0, Number var1) {
      return var0.maximum(var1);
   }

   public static Object a(DoubleProperty var0) {
      return var0.get();
   }

   public static DoubleProperty a(Double var0) {
      return DoubleProperty.of(var0);
   }

   public static DoubleProperty a() {
      return DoubleProperty.create();
   }

   public static void a(DoubleProperty var0, Number var1) {
      var0.set(var1);
   }
}
