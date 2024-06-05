package net;

import net.JM;
import net._g;
import viaversion.viarewind.api.ViaRewindConfig;

public class qP {
   private static boolean b;

   public static ViaRewindConfig e() {
      return JM.b();
   }

   public static _g c() {
      return JM.a();
   }

   public static void a(_g var0, ViaRewindConfig var1) {
      JM.a(var0, var1);
   }

   public static void b(boolean var0) {
      b = var0;
   }

   public static boolean b() {
      return b;
   }

   public static boolean d() {
      boolean var0 = b();
      return true;
   }

   static {
      if(d()) {
         b(true);
      }

   }
}
