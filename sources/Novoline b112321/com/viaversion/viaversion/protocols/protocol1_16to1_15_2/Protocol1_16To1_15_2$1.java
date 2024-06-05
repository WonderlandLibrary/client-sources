package com.viaversion.viaversion.protocols.protocol1_16to1_15_2;

import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocol.packet.PacketWrapperImpl;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.Protocol1_16To1_15_2;
import java.util.UUID;
import net.EN;

class Protocol1_16To1_15_2$1 extends PacketRemapper {
   final Protocol1_16To1_15_2 this$0;

   Protocol1_16To1_15_2$1(Protocol1_16To1_15_2 var1) {
      this.this$0 = var1;
   }

   public void registerMap() {
      this.a(Protocol1_16To1_15_2$1::lambda$registerMap$0);
   }

   private static void lambda$registerMap$0(PacketWrapperImpl var0) throws Exception {
      UUID var1 = UUID.fromString((String)var0.b(Type.STRING));
      var0.a(Type.UUID_INT_ARRAY, var1);
   }
}
