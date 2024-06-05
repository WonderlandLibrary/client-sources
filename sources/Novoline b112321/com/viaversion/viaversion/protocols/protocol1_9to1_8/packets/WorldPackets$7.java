package com.viaversion.viaversion.protocols.protocol1_9to1_8.packets;

import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;

final class WorldPackets$7 extends PacketRemapper {
   public void registerMap() {
      this.a(Type.POSITION);
      this.a(Type.VAR_INT);
   }
}
