package net;

import net.acE;
import net.aqT;
import net.co;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;

class ac1 extends acE {
   final aqT c;

   ac1(aqT var1) {
      this.c = var1;
   }

   public void registerMap() {
      this.a(ac1::lambda$registerMap$0);
   }

   private static void lambda$registerMap$0(PacketWrapper var0) throws Exception {
      co var1 = (co)var0.user().b(co.class);
      var1.c((String)null);
      var1.a(-1);
   }
}
