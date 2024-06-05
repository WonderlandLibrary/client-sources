package net;

import net.aif;

public class aB3 {
   private static String[] b;

   public static boolean a(aif var0) {
      return var0.d();
   }

   public static void a(aif var0, float var1) {
      var0.a(var1);
   }

   public static void b(String[] var0) {
      b = var0;
   }

   public static String[] b() {
      return b;
   }

   static {
      if(b() != null) {
         b(new String[2]);
      }

   }
}
