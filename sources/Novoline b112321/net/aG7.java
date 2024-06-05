package net;

import net.aMU;
import net.cq;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.ValueCreator;
import viaversion.viaversion.api.type.Type;

class aG7 implements ValueCreator {
   final aMU a;

   aG7(aMU var1) {
      this.a = var1;
   }

   public void write(PacketWrapper var1) throws Exception {
      int var2 = ((Integer)var1.get(Type.VAR_INT, 0)).intValue();
      cq var3 = (cq)var1.user().b(cq.class);
      var1.write(Type.UUID, var3.a(var2));
   }
}
