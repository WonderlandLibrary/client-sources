package net;

public class qg {
   private static int[] b;

   public static Double b(double var0) {
      return Double.valueOf(var0);
   }

   public static double b(Double var0) {
      return var0.doubleValue();
   }

   public static long a(Double var0) {
      return var0.longValue();
   }

   public static double a(String var0) {
      return Double.parseDouble(var0);
   }

   public static boolean d(double var0) {
      return Double.isNaN(var0);
   }

   public static long c(double var0) {
      return Double.doubleToRawLongBits(var0);
   }

   public static double a(long var0) {
      return Double.longBitsToDouble(var0);
   }

   public static long a(double var0) {
      return Double.doubleToLongBits(var0);
   }

   public static String f(double var0) {
      return Double.toString(var0);
   }

   public static int a(double var0, double var2) {
      return Double.compare(var0, var2);
   }

   public static boolean e(double var0) {
      return Double.isInfinite(var0);
   }

   public static void b(int[] var0) {
      b = var0;
   }

   public static int[] b() {
      return b;
   }

   static {
      if(b() != null) {
         b(new int[1]);
      }

   }
}
