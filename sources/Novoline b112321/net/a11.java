package net;

import net.aD9;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

class a11 implements PacketHandler {
   final aD9 a;

   a11(aD9 var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      var1.read(Type.BOOLEAN);
   }
}
