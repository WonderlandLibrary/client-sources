package net;

import java.util.function.Supplier;

public class axF {
   private static int[] b;

   public static Object a(Supplier var0) {
      return var0.get();
   }

   public static void b(int[] var0) {
      b = var0;
   }

   public static int[] b() {
      return b;
   }

   static {
      if(b() != null) {
         b(new int[5]);
      }

   }
}
