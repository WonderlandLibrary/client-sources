package net;

import net.aSG;
import net.aTZ;
import net.aVD;
import net.aVn;
import net.awj;
import net.g4;
import viaversion.viaversion.protocols.protocol1_15to1_14_4.Protocol1_15To1_14_4;

public class awA {
   private static String b;

   public static void a(Protocol1_15To1_14_4 var0) {
      aTZ var1 = (aTZ)var0.get(aTZ.class);
      var1.registerSpawnTrackerWithData(awj.SPAWN_ENTITY, g4.FALLING_BLOCK);
      var0.a(awj.SPAWN_MOB, new aVn(var1));
      var0.a(awj.SPAWN_PLAYER, new aVD(var1));
      var1.registerMetadataRewriter(awj.ENTITY_METADATA, aSG.c);
      var1.registerEntityDestroy(awj.DESTROY_ENTITIES);
   }

   public static int a(int var0) {
      String var1 = b();
      return var0 >= 4?var0 + 1:var0;
   }

   public static void b(String var0) {
      b = var0;
   }

   public static String b() {
      return b;
   }

   static {
      if(b() != null) {
         b("E6gwUc");
      }

   }
}
