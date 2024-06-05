package net;

public class B6 {
   private static int b;

   public static double f(Number var0) {
      return var0.doubleValue();
   }

   public static float b(Number var0) {
      return var0.floatValue();
   }

   public static long a(Number var0) {
      return var0.longValue();
   }

   public static int d(Number var0) {
      return var0.intValue();
   }

   public static short e(Number var0) {
      return var0.shortValue();
   }

   public static byte c(Number var0) {
      return var0.byteValue();
   }

   public static void b(int var0) {
      b = var0;
   }

   public static int b() {
      return b;
   }

   public static int a() {
      int var0 = b();
      return 115;
   }

   static {
      if(a() != 0) {
         b(96);
      }

   }
}
