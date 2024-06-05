package net;

import net.ahY;
import viaversion.viaversion.api.data.UserConnection;
import viaversion.viaversion.api.storage.EntityTracker;

public class cL extends EntityTracker {
   private static String g;

   public cL(UserConnection var1) {
      super(var1, ahY.PLAYER);
   }

   public static void b(String var0) {
      g = var0;
   }

   public static String a() {
      return g;
   }

   static {
      if(a() == null) {
         b("ymUR4");
      }

   }
}
