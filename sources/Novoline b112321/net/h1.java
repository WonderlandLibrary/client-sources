package net;

public class h1 {
   private static int[] b;

   public static boolean a(net.skidunion.u var0) {
      return var0.d();
   }

   public static String b(net.skidunion.u var0) {
      return var0.a();
   }

   public static void a(net.skidunion.u var0, boolean var1) {
      var0.c(var1);
   }

   public static void b(int[] var0) {
      b = var0;
   }

   public static int[] b() {
      return b;
   }

   static {
      if(b() == null) {
         b(new int[3]);
      }

   }
}
