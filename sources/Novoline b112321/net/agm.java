package net;

import java.util.List;
import net.Dk;
import net.aDt;
import net.axZ;
import net.t4;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;

class agm implements PacketHandler {
   final aDt a;

   agm(aDt var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      List var2 = (List)var1.get(axZ.j, 0);
      Dk.a(t4.PLAYER, var2);
   }
}
