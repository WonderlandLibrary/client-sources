package net;

import net.ca;

public class qk {
   private static String[] b;

   public static String a(ca var0) {
      return var0.d();
   }

   public static boolean b(ca var0) {
      return var0.c();
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
