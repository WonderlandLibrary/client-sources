package net;

import org.apache.http.client.methods.HttpGet;

public class mi {
   private static int b;

   public static void a(HttpGet var0, String var1, String var2) {
      var0.setHeader(var1, var2);
   }

   public static void b(int var0) {
      b = var0;
   }

   public static int b() {
      return b;
   }

   public static int c() {
      int var0 = b();
      return 110;
   }

   static {
      if(b() == 0) {
         b(82);
      }

   }
}
