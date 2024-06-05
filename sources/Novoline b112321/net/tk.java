package net;

import java.util.List;
import net.aVk;
import net.cq;
import net.rX;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

class tk implements PacketHandler {
   final aVk a;

   tk(aVk var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      List var2 = (List)var1.get(rX.a, 0);
      int var3 = ((Integer)var1.get(Type.VAR_INT, 0)).intValue();
      cq var4 = (cq)var1.user().b(cq.class);
      var4.a(var3, var2);
   }
}
