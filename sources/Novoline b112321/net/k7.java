package net;

import net.and;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

class k7 implements PacketHandler {
   final and a;

   k7(and var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      var1.read(Type.BOOLEAN);
   }
}
