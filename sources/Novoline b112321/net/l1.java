package net;

import net.cM;

public class l1 {
   private static boolean b;

   public static boolean a(cM var0) {
      return var0.d();
   }

   public static void a(cM var0, boolean var1) {
      var0.a(var1);
   }

   public static void b(boolean var0) {
      b = var0;
   }

   public static boolean b() {
      return b;
   }

   public static boolean c() {
      boolean var0 = b();
      return true;
   }

   static {
      if(!b()) {
         b(true);
      }

   }
}
