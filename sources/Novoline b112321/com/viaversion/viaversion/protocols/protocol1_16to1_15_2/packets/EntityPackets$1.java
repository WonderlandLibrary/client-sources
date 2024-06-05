package com.viaversion.viaversion.protocols.protocol1_16to1_15_2.packets;

import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocol.packet.PacketWrapperImpl;
import java.util.UUID;
import net.EN;
import net.axs;
import net.c4;

final class EntityPackets$1 extends PacketRemapper {
   public void registerMap() {
      this.a(EntityPackets$1::lambda$registerMap$0);
   }

   private static void lambda$registerMap$0(PacketWrapperImpl var0) throws Exception {
      int var1 = ((Integer)var0.a((Type)Type.VAR_INT)).intValue();
      ((c4)var0.e().b(c4.class)).a(var1, axs.LIGHTNING_BOLT);
      var0.a(Type.UUID, UUID.randomUUID());
      var0.a(Type.VAR_INT, Integer.valueOf(axs.LIGHTNING_BOLT.a()));
      var0.b(Type.k);
      var0.a(Type.m);
      var0.a(Type.m);
      var0.a(Type.m);
      var0.a(Type.k, Byte.valueOf((byte)0));
      var0.a(Type.k, Byte.valueOf((byte)0));
      var0.a(Type.I, Integer.valueOf(0));
      var0.a(Type.SHORT, Short.valueOf((short)0));
      var0.a(Type.SHORT, Short.valueOf((short)0));
      var0.a(Type.SHORT, Short.valueOf((short)0));
   }
}
