package net;

import net.skidunion.Y;

public class pz {
   private static boolean b;

   public static void a(Y var0, net.skidunion.q var1) {
      var0.a(var1);
   }

   public static void a(Y var0, net.skidunion.H var1) {
      var0.a(var1);
   }

   public static void b(boolean var0) {
      b = var0;
   }

   public static boolean b() {
      return b;
   }

   public static boolean a() {
      boolean var0 = b();
      return true;
   }

   static {
      if(!a()) {
         b(true);
      }

   }
}
