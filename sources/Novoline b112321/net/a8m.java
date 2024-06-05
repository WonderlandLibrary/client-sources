package net;

import net.aKs;
import net.cT;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

class a8m implements PacketHandler {
   final aKs a;

   a8m(aKs var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      cT var2 = (cT)var1.user().b(cT.class);
      int var3 = ((Integer)var1.get(Type.INT, 0)).intValue();
      var2.c(var3);
   }
}
