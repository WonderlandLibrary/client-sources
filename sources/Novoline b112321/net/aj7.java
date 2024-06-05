package net;

import java.io.IOException;

public class aj7 {
   private static boolean b;

   public static void a(IOException var0) {
      var0.printStackTrace();
   }

   public static String b(IOException var0) {
      return var0.getMessage();
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
