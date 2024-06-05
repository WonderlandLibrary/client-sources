package net;

import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import net.aRs;
import net.aTD;

class a7j extends PacketRemapper {
   final aTD c;
   final aRs d;

   a7j(aRs var1, aTD var2) {
      this.d = var1;
      this.c = var2;
   }

   public void registerMap() {
      this.a(Type.VAR_INT);
      this.a(Type.UUID);
      this.a(Type.k);
      this.a(this.c.d());
   }
}
