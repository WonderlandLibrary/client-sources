package net;

import java.lang.invoke.MethodType;

public class aa0 {
   private static String[] b;

   public static MethodType a(MethodType var0) {
      return var0.generic();
   }

   public static MethodType a(MethodType var0, Class var1) {
      return var0.changeReturnType(var1);
   }

   public static void b(String[] var0) {
      b = var0;
   }

   public static String[] b() {
      return b;
   }

   static {
      if(b() != null) {
         b(new String[3]);
      }

   }
}
