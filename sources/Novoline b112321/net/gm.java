package net;

import net.acE;
import net.skidunion.y;

public class gm {
   private static acE[] b;

   public static int a(y var0) {
      return var0.a();
   }

   public static void b(acE[] var0) {
      b = var0;
   }

   public static acE[] b() {
      return b;
   }

   static {
      if(b() == null) {
         b(new acE[1]);
      }

   }
}
