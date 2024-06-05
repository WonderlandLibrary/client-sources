package net;

import net.aHK;
import net.akb;

public class a4o {
   private static int b;

   public static aHK a(akb var0) {
      return var0.a();
   }

   public static void b(int var0) {
      b = var0;
   }

   public static int b() {
      return b;
   }

   public static int c() {
      int var0 = b();
      return 38;
   }

   static {
      if(c() != 0) {
         b(105);
      }

   }
}
