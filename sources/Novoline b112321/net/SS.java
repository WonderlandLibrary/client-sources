package net;

import net.optifine.ReflectorClass;

public class SS {
   public static Class b(ReflectorClass var0) {
      return var0.getTargetClass();
   }

   public static boolean a(ReflectorClass var0) {
      return var0.exists();
   }

   public static boolean a(ReflectorClass var0, Object var1) {
      return var0.isInstance(var1);
   }
}
