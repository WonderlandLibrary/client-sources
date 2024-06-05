package net;

import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import net.aq3;

class aok extends PacketRemapper {
   final aq3 c;

   aok(aq3 var1) {
      this.c = var1;
   }

   public void registerMap() {
      this.a(Type.I);
      this.map(Type.g, Type.af);
   }
}
