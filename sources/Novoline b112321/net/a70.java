package net;

import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import net.aRF;

class a70 extends PacketRemapper {
   final aRF c;

   a70(aRF var1) {
      this.c = var1;
   }

   public void registerMap() {
      this.a(Type.STRING);
      this.a(Type.VAR_INT);
      this.a(Type.I);
      this.a(Type.I);
      this.a(Type.I);
      this.a(Type.FLOAT);
      this.map(Type.M, aRF.j);
   }
}
