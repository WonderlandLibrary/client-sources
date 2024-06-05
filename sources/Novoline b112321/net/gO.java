package net;

import net.aM1;
import net.cq;
import net.t4;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

class gO implements PacketHandler {
   final aM1 a;

   gO(aM1 var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      int var2 = ((Integer)var1.get(Type.VAR_INT, 0)).intValue();
      cq var3 = (cq)var1.user().b(cq.class);
      var3.addEntity(var2, t4.PLAYER);
      var3.c(var2);
   }
}
