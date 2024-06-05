package net;

import net.aJp;
import net.aR3;

public class aig {
   private static int[] b;

   public static void a(aR3 var0) {
      aJp.a(var0);
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
