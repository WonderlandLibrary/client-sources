package com.viaversion.viaversion.protocols.protocol1_9to1_8.packets;

import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.packets.EntityPackets;

final class EntityPackets$4 extends PacketRemapper {
   public void registerMap() {
      this.a(Type.VAR_INT);
      this.map(Type.k, EntityPackets.toNewShort);
      this.map(Type.k, EntityPackets.toNewShort);
      this.map(Type.k, EntityPackets.toNewShort);
      this.a(Type.k);
      this.a(Type.k);
      this.a(Type.c);
   }
}
