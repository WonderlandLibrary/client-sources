package net;

import net.acE;
import net.skidunion.P;

public class ayF {
   private static acE[] b;

   public static String a(String var0, String var1, String var2) {
      return P.a(var0, var1, var2);
   }

   public static void b(acE[] var0) {
      b = var0;
   }

   public static acE[] b() {
      return b;
   }

   static {
      if(b() == null) {
         b(new acE[5]);
      }

   }
}
