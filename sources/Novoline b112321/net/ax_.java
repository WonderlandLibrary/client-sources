package net;

import java.time.Instant;

public class ax_ {
   private static int b;

   public static Instant d() {
      return Instant.now();
   }

   public static Instant a(Instant var0, long var1) {
      return var0.plusMillis(var1);
   }

   public static void b(int var0) {
      b = var0;
   }

   public static int b() {
      return b;
   }

   public static int c() {
      int var0 = b();
      return 72;
   }

   static {
      if(b() == 0) {
         b(2);
      }

   }
}
