package net;

import java.io.File;
import java.util.logging.Logger;
import viaversion.viabackwards.api.ViaBackwardsPlatform;

public class pm {
   private static boolean b;

   public static Logger b(ViaBackwardsPlatform var0) {
      return var0.getLogger();
   }

   public static File a(ViaBackwardsPlatform var0) {
      return var0.getDataFolder();
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
