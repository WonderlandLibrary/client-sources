package net;

import net.aML;
import net.cq;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.ValueCreator;
import viaversion.viaversion.api.type.Type;

class a2r implements ValueCreator {
   final aML a;

   a2r(aML var1) {
      this.a = var1;
   }

   public void write(PacketWrapper var1) throws Exception {
      int var2 = ((Integer)var1.get(Type.VAR_INT, 0)).intValue();
      cq var3 = (cq)var1.user().b(cq.class);
      var1.write(Type.UUID, var3.a(var2));
   }
}
