package com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets;

import com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets.PlayerPackets1_14;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;

class PlayerPackets1_14$1 extends PacketRemapper {
   final PlayerPackets1_14 this$0;

   PlayerPackets1_14$1(PlayerPackets1_14 var1) {
      this.this$0 = var1;
   }

   public void registerMap() {
      this.a(Type.M);
      this.map(Type.c, Type.af);
   }
}
