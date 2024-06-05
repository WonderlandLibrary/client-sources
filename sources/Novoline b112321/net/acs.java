package net;

import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import net.aAX;

class acs extends PacketRemapper {
   final aAX c;

   acs(aAX var1) {
      this.c = var1;
   }

   public void registerMap() {
      this.a(Type.STRING);
      this.a(this.c.c());
   }
}
