package net;

import net.aqU;

public class u1 {
   private static int b;

   public static void a(aqU var0) {
      var0.f();
   }

   public static void b(int var0) {
      b = var0;
   }

   public static int b() {
      return b;
   }

   public static int c() {
      int var0 = b();
      return 68;
   }

   static {
      if(b() == 0) {
         b(75);
      }

   }
}
