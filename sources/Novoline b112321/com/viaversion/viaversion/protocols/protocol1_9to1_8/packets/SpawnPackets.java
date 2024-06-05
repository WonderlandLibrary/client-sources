package com.viaversion.viaversion.protocols.protocol1_9to1_8.packets;

import com.viaversion.viaversion.api.protocol.remapper.ValueTransformer;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.SpawnPackets$1;
import net.aM1;
import net.aM4;
import net.aML;
import net.aMN;
import net.aMP;
import net.aMU;
import net.aMb;
import net.aRY;
import net.aeU;

public class SpawnPackets {
   public static final ValueTransformer toNewDouble = new SpawnPackets$1(Type.m);

   public static void a(aRY var0) {
      var0.a(aeU.SPAWN_ENTITY, new aML());
      var0.a(aeU.SPAWN_EXPERIENCE_ORB, new aMP());
      var0.a(aeU.SPAWN_GLOBAL_ENTITY, new aMb());
      var0.a(aeU.SPAWN_MOB, new aMN(var0));
      var0.a(aeU.SPAWN_PAINTING, new aMU());
      var0.a(aeU.SPAWN_PLAYER, new aM1(var0));
      var0.a(aeU.DESTROY_ENTITIES, new aM4());
   }
}
