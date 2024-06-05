package net;

import net.aRb;
import net.aSG;
import net.aTV;
import net.aWL;
import net.aWP;
import net.awj;

public class zK {
   private static String[] b;

   public static void a(aRb var0) {
      aTV var1 = (aTV)var0.get(aTV.class);
      var0.a(awj.SPAWN_MOB, new aWL(var1));
      var1.registerEntityDestroy(awj.DESTROY_ENTITIES);
      var0.a(awj.SPAWN_PLAYER, new aWP(var1));
      var1.registerMetadataRewriter(awj.ENTITY_METADATA, aSG.c);
   }

   public static void b(String[] var0) {
      b = var0;
   }

   public static String[] b() {
      return b;
   }

   static {
      if(b() == null) {
         b(new String[5]);
      }

   }
}
