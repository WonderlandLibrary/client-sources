package net;

import net.aML;
import net.aZ9;
import net.cq;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

class gk implements PacketHandler {
   final aML a;

   gk(aML var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      int var2 = ((Integer)var1.get(Type.VAR_INT, 0)).intValue();
      byte var3 = ((Byte)var1.get(Type.BYTE, 0)).byteValue();
      cq var4 = (cq)var1.user().b(cq.class);
      var4.addEntity(var2, aZ9.a(var3, true));
      var4.c(var2);
   }
}
