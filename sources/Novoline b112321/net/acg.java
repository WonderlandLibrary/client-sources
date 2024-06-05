package net;

import net.aMc;
import net.cR;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

class acg implements PacketHandler {
   final aMc a;

   acg(aMc var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      String var2 = (String)var1.get(Type.STRING, 0);
      cR var3 = (cR)var1.user().b(cR.class);
      var3.a(var2);
   }
}
