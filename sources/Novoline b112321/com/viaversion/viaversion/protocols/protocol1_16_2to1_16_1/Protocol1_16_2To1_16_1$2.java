package com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1;

import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocol.packet.PacketWrapperImpl;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.Protocol1_16_2To1_16_1;
import net.EN;

class Protocol1_16_2To1_16_1$2 extends PacketRemapper {
   final Protocol1_16_2To1_16_1 this$0;

   Protocol1_16_2To1_16_1$2(Protocol1_16_2To1_16_1 var1) {
      this.this$0 = var1;
   }

   public void registerMap() {
      this.a(Protocol1_16_2To1_16_1$2::lambda$registerMap$0);
   }

   private static void lambda$registerMap$0(PacketWrapperImpl var0) throws Exception {
      String var1 = (String)var0.b(Type.STRING);
      var0.a(Type.VAR_INT, Integer.valueOf(0));
      var0.a(Type.STRING, var1);
   }
}
