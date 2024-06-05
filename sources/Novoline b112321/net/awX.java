package net;

import net.aMr;
import net.cq;
import net.t4;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

class awX implements PacketHandler {
   final aMr a;

   awX(aMr var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      int var2 = ((Integer)var1.get(Type.INT, 0)).intValue();
      cq var3 = (cq)var1.user().b(cq.class);
      var3.addEntity(var2, t4.PLAYER);
      var3.setClientEntityId(var2);
   }
}
