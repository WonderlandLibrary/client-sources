package net;

import net.g4;
import viaversion.viaversion.api.data.UserConnection;
import viaversion.viaversion.api.storage.EntityTracker;

public class cU extends EntityTracker {
   private static String g;

   public cU(UserConnection var1) {
      super(var1, g4.PLAYER);
   }

   public static void b(String var0) {
      g = var0;
   }

   public static String a() {
      return g;
   }

   static {
      if(a() == null) {
         b("nKlnX");
      }

   }
}
