package net;

import net.sK;
import viaversion.viaversion.api.data.UserConnection;

public class l5 {
   private static int[] b;

   public static boolean a(sK var0, short var1, short var2, short var3, UserConnection var4) {
      return var0.a(var1, var2, var3, var4);
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
