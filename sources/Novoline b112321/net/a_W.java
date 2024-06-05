package net;

import net.aVg;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

class a_W implements PacketHandler {
   final aVg a;

   a_W(aVg var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      int var2 = ((Integer)var1.get(Type.VAR_INT, 0)).intValue();
      var1.set(Type.VAR_INT, 0, Integer.valueOf(this.a.c.getMappingData().getNewBlockStateId(var2)));
   }
}
