package net;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;

public class gx {
   public static MethodType a(MethodHandle var0) {
      return var0.type();
   }

   public static MethodHandle a(MethodHandle var0, MethodType var1) {
      return var0.asType(var1);
   }

   public static void a(MethodHandle var0, Object var1) {
      var0.invokeExact(var1);
   }
}
