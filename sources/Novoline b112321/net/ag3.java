package net;

import net.acE;
import net.hz;

public class ag3 {
   private static acE[] b;

   public static int a(hz var0, hz var1) {
      return var0.a(var1);
   }

   public static String a(hz var0) {
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
         b(new acE[3]);
      }

   }
}
