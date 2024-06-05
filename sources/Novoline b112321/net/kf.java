package net;

import net.aMr;
import net.aRY;
import net.aXe;
import net.cq;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.Via;
import viaversion.viaversion.api.remapper.PacketHandler;

class kf implements PacketHandler {
   final aMr a;

   kf(aMr var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      aXe.c();
      cq var3 = (cq)var1.user().b(cq.class);
      if(Via.getConfig().isAutoTeam()) {
         var3.a(true);
         var1.send(aRY.class, true, true);
         var1.cancel();
         var3.a(true, true);
         var3.a("viaversion");
      }

      var3.a(false);
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
