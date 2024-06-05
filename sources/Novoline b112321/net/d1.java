package net;

import kotlin.random.Random.Default;

public class d1 {
   private static String[] b;

   public static int a(Default var0, int var1, int var2) {
      return var0.nextInt(var1, var2);
   }

   public static void b(String[] var0) {
      b = var0;
   }

   public static String[] b() {
      return b;
   }

   static {
      if(b() != null) {
         b(new String[3]);
      }

   }
}
