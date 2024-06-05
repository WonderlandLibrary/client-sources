package com.viaversion.viabackwards.protocol.protocol1_10to1_11.packets;

import com.viaversion.viabackwards.protocol.protocol1_10to1_11.packets.PlayerPackets1_11;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocol.packet.PacketWrapperImpl;
import net.EN;

class PlayerPackets1_11$3 extends PacketRemapper {
   final PlayerPackets1_11 this$0;

   PlayerPackets1_11$3(PlayerPackets1_11 var1) {
      this.this$0 = var1;
   }

   public void registerMap() {
      this.a(Type.VAR_INT);
      this.a(Type.VAR_INT);
      this.a(PlayerPackets1_11$3::lambda$registerMap$0);
   }

   private static void lambda$registerMap$0(PacketWrapperImpl var0) throws Exception {
      Integer var10000 = (Integer)var0.b((Type)Type.VAR_INT);
   }
}
