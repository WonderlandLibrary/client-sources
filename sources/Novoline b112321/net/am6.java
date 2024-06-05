package net;

import net.ai4;

public class am6 {
   private static boolean b;

   public static int a(ai4 var0, int var1) {
      return var0.getNewBlockStateId(var1);
   }

   public static int b(ai4 var0, int var1) {
      return var0.getNewItemId(var1);
   }

   public static int c(ai4 var0, int var1) {
      return var0.getOldItemId(var1);
   }

   public static void b(boolean var0) {
      b = var0;
   }

   public static boolean b() {
      return b;
   }

   public static boolean c() {
      boolean var0 = b();
      return true;
   }

   static {
      if(!c()) {
         b(true);
      }

   }
}
