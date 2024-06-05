package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets;

import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets.EntityPackets1_13;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocol.packet.PacketWrapperImpl;
import net.EN;
import net.cC;

class EntityPackets1_13$8 extends PacketRemapper {
   final EntityPackets1_13 this$0;

   EntityPackets1_13$8(EntityPackets1_13 var1) {
      this.this$0 = var1;
   }

   public void registerMap() {
      this.a(Type.m);
      this.a(Type.m);
      this.a(Type.m);
      this.a(EntityPackets1_13$8::lambda$registerMap$0);
   }

   private static void lambda$registerMap$0(PacketWrapperImpl var0) throws Exception {
      ((cC)var0.e().b(cC.class)).a(var0, false);
   }
}
