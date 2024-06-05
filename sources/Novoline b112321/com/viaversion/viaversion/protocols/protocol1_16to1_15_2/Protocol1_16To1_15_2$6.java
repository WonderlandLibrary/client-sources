package com.viaversion.viaversion.protocols.protocol1_16to1_15_2;

import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocol.packet.PacketWrapperImpl;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.Protocol1_16To1_15_2;
import net.EN;

class Protocol1_16To1_15_2$6 extends PacketRemapper {
   final Protocol1_16To1_15_2 this$0;

   Protocol1_16To1_15_2$6(Protocol1_16To1_15_2 var1) {
      this.this$0 = var1;
   }

   public void registerMap() {
      this.a(Protocol1_16To1_15_2$6::lambda$registerMap$0);
   }

   private static void lambda$registerMap$0(PacketWrapperImpl var0) throws Exception {
      var0.a(Type.k);
      var0.a(Type.FLOAT, Float.valueOf(0.05F));
      var0.a(Type.FLOAT, Float.valueOf(0.1F));
   }
}
