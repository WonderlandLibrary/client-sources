package com.viaversion.viaversion.protocols.protocol1_16to1_15_2;

import com.google.gson.JsonElement;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocol.packet.PacketWrapperImpl;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.Protocol1_16To1_15_2;
import net.EN;
import net.km;

class Protocol1_16To1_15_2$3 extends PacketRemapper {
   final km c;
   final Protocol1_16To1_15_2 this$0;

   Protocol1_16To1_15_2$3(Protocol1_16To1_15_2 var1, km var2) {
      this.this$0 = var1;
      this.c = var2;
   }

   public void registerMap() {
      this.a(Type.p);
      this.a(Type.k);
      this.a(Protocol1_16To1_15_2$3::lambda$registerMap$0);
   }

   private static void lambda$registerMap$0(km var0, PacketWrapperImpl var1) throws Exception {
      var0.a((JsonElement)((JsonElement)var1.b(Type.p, 0)));
      var1.a(Type.UUID, Protocol1_16To1_15_2.access$000());
   }
}
