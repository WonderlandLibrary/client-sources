package net;

import net.awD;
import viaversion.viaversion.api.data.UserConnection;
import viaversion.viaversion.api.storage.EntityTracker;

public class c1 extends EntityTracker {
   private static String[] g;

   public c1(UserConnection var1) {
      super(var1, awD.PLAYER);
   }

   public static void a(String[] var0) {
      g = var0;
   }

   public static String[] b() {
      return g;
   }

   static {
      if(b() != null) {
         a(new String[1]);
      }

   }
}
