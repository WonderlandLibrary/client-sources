package com.viaversion.viaversion.protocols.protocol1_11to1_10;

import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import net.aRs;

class Protocol1_11To1_10$13 extends PacketRemapper {
   final aRs c;

   Protocol1_11To1_10$13(aRs var1) {
      this.c = var1;
   }

   public void registerMap() {
      this.a(Type.POSITION);
      this.a(Type.VAR_INT);
      this.a(Type.VAR_INT);
      this.map(Type.FLOAT, aRs.a());
      this.map(Type.FLOAT, aRs.a());
      this.map(Type.FLOAT, aRs.a());
   }
}
