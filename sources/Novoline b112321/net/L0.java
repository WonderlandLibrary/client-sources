package net;

import net.aMP;
import net.cq;
import net.t4;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

class L0 implements PacketHandler {
   final aMP a;

   L0(aMP var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      int var2 = ((Integer)var1.get(Type.VAR_INT, 0)).intValue();
      cq var3 = (cq)var1.user().b(cq.class);
      var3.addEntity(var2, t4.EXPERIENCE_ORB);
      var3.c(var2);
   }
}
