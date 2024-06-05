package net;

public class so {
   private static String[] b;

   public static Float f(float var0) {
      return Float.valueOf(var0);
   }

   public static float b(Float var0) {
      return var0.floatValue();
   }

   public static int a(Float var0) {
      return var0.intValue();
   }

   public static float a(String var0) {
      return Float.parseFloat(var0);
   }

   public static float a(int var0) {
      return Float.intBitsToFloat(var0);
   }

   public static int a(float var0, float var1) {
      return Float.compare(var0, var1);
   }

   public static int e(float var0) {
      return Float.floatToIntBits(var0);
   }

   public static String b(float var0) {
      return Float.toString(var0);
   }

   public static int d(float var0) {
      return Float.floatToRawIntBits(var0);
   }

   public static boolean a(float var0) {
      return Float.isNaN(var0);
   }

   public static boolean c(float var0) {
      return Float.isInfinite(var0);
   }

   public static void b(String[] var0) {
      b = var0;
   }

   public static String[] b() {
      return b;
   }

   static {
      if(b() == null) {
         b(new String[1]);
      }

   }
}
