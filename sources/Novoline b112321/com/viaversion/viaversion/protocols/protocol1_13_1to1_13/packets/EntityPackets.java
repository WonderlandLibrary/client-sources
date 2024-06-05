package com.viaversion.viaversion.protocols.protocol1_13_1to1_13.packets;

import com.viaversion.viaversion.protocols.protocol1_13_1to1_13.Protocol1_13_1To1_13;
import com.viaversion.viaversion.protocols.protocol1_13_1to1_13.packets.EntityPackets$2;
import com.viaversion.viaversion.protocols.protocol1_13_1to1_13.packets.EntityPackets$3;
import net.aEY;
import net.aK8;
import net.aTK;
import net.q1;

public class EntityPackets {
   public static void register(Protocol1_13_1To1_13 var0) {
      aTK var1 = (aTK)var0.b(aTK.class);
      var0.a(q1.SPAWN_ENTITY, new aK8(var0));
      var0.a(q1.SPAWN_MOB, new EntityPackets$2(var1));
      var0.a(q1.SPAWN_PLAYER, new EntityPackets$3(var1));
      var1.b(q1.DESTROY_ENTITIES);
      var1.a(q1.ENTITY_METADATA, aEY.a);
   }
}
