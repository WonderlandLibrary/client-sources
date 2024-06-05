package com.viaversion.viaversion.protocols.protocol1_14to1_13_2.packets;

import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;

final class WorldPackets$1 extends PacketRemapper {
   public void registerMap() {
      this.a(Type.VAR_INT);
      this.map(Type.POSITION, Type.POSITION1_14);
      this.a(Type.k);
   }
}
