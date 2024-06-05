package net;

import net.aEY;
import net.aK8;
import net.aKB;
import net.aKm;
import net.aRw;
import net.aTK;
import net.q1;

public class I_ {
   public static void a(aRw var0) {
      aTK var1 = (aTK)var0.get(aTK.class);
      var0.a(q1.SPAWN_ENTITY, new aK8(var0));
      var0.a(q1.SPAWN_MOB, new aKB(var1));
      var0.a(q1.SPAWN_PLAYER, new aKm(var1));
      var1.registerEntityDestroy(q1.DESTROY_ENTITIES);
      var1.registerMetadataRewriter(q1.ENTITY_METADATA, aEY.a);
   }
}
