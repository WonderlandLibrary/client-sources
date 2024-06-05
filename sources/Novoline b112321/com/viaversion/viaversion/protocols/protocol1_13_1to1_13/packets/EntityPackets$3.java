package com.viaversion.viaversion.protocols.protocol1_13_1to1_13.packets;

import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import net.aEY;
import net.aTK;
import net.a_4;

final class EntityPackets$3 extends PacketRemapper {
   final aTK c;

   EntityPackets$3(aTK var1) {
      this.c = var1;
   }

   public void registerMap() {
      this.a(Type.VAR_INT);
      this.a(Type.UUID);
      this.a(Type.m);
      this.a(Type.m);
      this.a(Type.m);
      this.a(Type.k);
      this.a(Type.k);
      this.a(aEY.a);
      this.a(this.c.a(aEY.a, a_4.PLAYER));
   }
}
