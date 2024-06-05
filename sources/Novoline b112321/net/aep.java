package net;

import java.util.List;
import net.a7J;
import net.arP;
import net.t4;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.types.version.Types1_8;

class aep implements PacketHandler {
   final a7J a;

   aep(a7J var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      List var2 = (List)var1.get(Types1_8.METADATA_LIST, 0);
      arP.a(t4.PLAYER, var2);
   }
}
