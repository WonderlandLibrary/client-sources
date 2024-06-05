package net;

import net.co;

public class aZ7 {
   private static boolean b;

   public static String a(co var0) {
      return var0.c();
   }

   public static int b(co var0) {
      return var0.b();
   }

   public static void a(co var0, String var1) {
      var0.c(var1);
   }

   public static void a(co var0, int var1) {
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
