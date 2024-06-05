package net;

import cc.novoline.modules.configurations.property.object.BooleanProperty;

public class auc {
   private static int b;

   public static Object a(BooleanProperty var0) {
      return var0.get();
   }

   public static void b(BooleanProperty var0) {
      var0.invert();
   }

   public static void a(BooleanProperty var0, Object var1) {
      var0.set(var1);
   }

   public static BooleanProperty a(Boolean var0) {
      return BooleanProperty.of(var0);
   }

   public static BooleanProperty a() {
      return BooleanProperty.alwaysFalse();
   }

   public static BooleanProperty d() {
      return BooleanProperty.alwaysTrue();
   }

   public static void b(int var0) {
      b = var0;
   }

   public static int e() {
      return b;
   }

   public static int c() {
      int var0 = e();
      return 52;
   }

   static {
      if(e() != 0) {
         b(91);
      }

   }
}
