package com.viaversion.viaversion.protocols.protocol1_13_1to1_13.packets;

import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import net.HR;
import net.aEY;
import net.aTK;

final class EntityPackets$2 extends PacketRemapper {
   final aTK c;

   EntityPackets$2(aTK var1) {
      this.c = var1;
   }

   public void registerMap() {
      this.a(Type.VAR_INT);
      this.a(Type.UUID);
      this.a(Type.VAR_INT);
      this.a(Type.m);
      this.a(Type.m);
      this.a(Type.m);
      this.a(Type.k);
      this.a(Type.k);
      this.a(Type.k);
      this.a(Type.SHORT);
      this.a(Type.SHORT);
      HR.b();
      this.a(Type.SHORT);
      this.a(aEY.a);
      this.a(this.c.a(aEY.a));
      if(PacketRemapper.b() == null) {
         HR.b(new PacketRemapper[5]);
      }

   }
}
