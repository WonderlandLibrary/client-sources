package net;

import net.K6;
import net.Ul;
import net.aNb;

public class a6e {
   private static String[] b;

   public static double c(aNb var0) {
      return var0.d();
   }

   public static Ul b(aNb var0) {
      return var0.a();
   }

   public static K6 a(aNb var0) {
      return var0.c();
   }

   public static void b(String[] var0) {
      b = var0;
   }

   public static String[] b() {
      return b;
   }

   static {
      if(b() == null) {
         b(new String[2]);
      }

   }
}
