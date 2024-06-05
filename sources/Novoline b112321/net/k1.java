package net;

import net.aMn;
import net.cR;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;

class k1 implements PacketHandler {
   final aMn a;

   k1(aMn var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      cR var2 = (cR)var1.user().b(cR.class);
      var2.a((String)null);
   }
}
