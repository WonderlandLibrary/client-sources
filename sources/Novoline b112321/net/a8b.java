package net;

import net.a7g;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

class a8b implements PacketHandler {
   final a7g a;

   a8b(a7g var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      var1.read(Type.UUID);
   }
}
