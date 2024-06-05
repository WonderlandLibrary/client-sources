package net;

import net.cT;
import viaversion.viaversion.api.data.UserConnection;
import viaversion.viaversion.api.minecraft.Environment;

public class lt {
   private static int[] b;

   public static void a(cT var0, int var1) {
      var0.c(var1);
   }

   public static Environment b(cT var0) {
      return var0.a();
   }

   public static UserConnection a(cT var0) {
      return var0.d();
   }

   public static void b(int[] var0) {
      b = var0;
   }

   public static int[] b() {
      return b;
   }

   static {
      if(b() == null) {
         b(new int[2]);
      }

   }
}
