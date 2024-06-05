package net;

import io.netty.util.Attribute;

public class s8 {
   private static int b;

   public static Object a(Attribute var0) {
      return var0.get();
   }

   public static void a(Attribute var0, Object var1) {
      var0.set(var1);
   }

   public static void b(int var0) {
      b = var0;
   }

   public static int b() {
      return b;
   }

   public static int c() {
      int var0 = b();
      return 63;
   }

   static {
      if(c() != 0) {
         b(41);
      }

   }
}
