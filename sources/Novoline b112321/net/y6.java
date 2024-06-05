package net;

import net.QX;
import net.aRJ;

public class y6 {
   private static boolean b;

   public static void a(aRJ var0) {
      QX.a(var0);
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
      if(a()) {
         b(true);
      }

   }
}
