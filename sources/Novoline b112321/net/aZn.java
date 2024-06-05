package net;

import net.aRb;
import net.zK;

public class aZn {
   private static int[] b;

   public static void a(aRb var0) {
      zK.a(var0);
   }

   public static void b(int[] var0) {
      b = var0;
   }

   public static int[] b() {
      return b;
   }

   static {
      if(b() != null) {
         b(new int[4]);
      }

   }
}
