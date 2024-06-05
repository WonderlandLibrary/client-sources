package net;

import net.a7z;
import net.c6;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

class aQo implements PacketHandler {
   final a7z a;

   aQo(a7z var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      c6 var2 = (c6)var1.user().b(c6.class);
      var2.a((String)var1.get(Type.STRING, 1));
   }
}
