package net;

import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import net.aAX;

class acv extends PacketRemapper {
   final aAX c;

   acv(aAX var1) {
      this.c = var1;
   }

   public void registerMap() {
      this.a(this.c.b());
   }
}
