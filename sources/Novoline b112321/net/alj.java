package net;

import cc.novoline.utils.Timer;

public class alj {
   private static int b;

   public static void a(Timer var0) {
      var0.reset();
   }

   public static double b(Timer var0) {
      return var0.getLastDelay();
   }

   public static boolean a(Timer var0, double var1) {
      return var0.delay(var1);
   }

   public static boolean a(Timer var0, float var1) {
      return var0.check(var1);
   }

   public static long c(Timer var0) {
      return var0.getCurrentMS();
   }

   public static long d(Timer var0) {
      return var0.getLastMS();
   }

   public static void b(int var0) {
      b = var0;
   }

   public static int b() {
      return b;
   }

   public static int c() {
      int var0 = b();
      return 115;
   }

   static {
      if(b() != 0) {
         b(46);
      }

   }
}
