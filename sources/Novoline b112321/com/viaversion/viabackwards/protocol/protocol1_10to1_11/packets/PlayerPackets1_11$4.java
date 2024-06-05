package com.viaversion.viabackwards.protocol.protocol1_10to1_11.packets;

import com.viaversion.viabackwards.protocol.protocol1_10to1_11.packets.PlayerPackets1_11;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;

class PlayerPackets1_11$4 extends PacketRemapper {
   final PlayerPackets1_11 this$0;

   PlayerPackets1_11$4(PlayerPackets1_11 var1) {
      this.this$0 = var1;
   }

   public void registerMap() {
      this.a(Type.POSITION);
      this.a(Type.VAR_INT);
      this.a(Type.VAR_INT);
      this.map(Type.M, PlayerPackets1_11.access$000());
      this.map(Type.M, PlayerPackets1_11.access$000());
      this.map(Type.M, PlayerPackets1_11.access$000());
   }
}
