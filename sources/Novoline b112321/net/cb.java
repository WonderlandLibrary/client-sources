package net;

import net.N0;
import viaversion.viaversion.api.data.UserConnection;
import viaversion.viaversion.api.storage.EntityTracker;

public class cb extends EntityTracker {
   private static String[] g;

   public cb(UserConnection var1) {
      super(var1, N0.PLAYER);
   }

   public static void a(String[] var0) {
      g = var0;
   }

   public static String[] b() {
      return g;
   }

   static {
      if(b() == null) {
         a(new String[5]);
      }

   }
}
