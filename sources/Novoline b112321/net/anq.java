package net;

import net.acE;
import net.aqR;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

class anq extends acE {
   final aqR c;

   anq(aqR var1) {
      this.c = var1;
   }

   public void registerMap() {
      this.a(Type.POSITION);
      this.a(Type.VAR_INT);
      this.a(this::lambda$registerMap$0);
   }

   private void lambda$registerMap$0(PacketWrapper var1) throws Exception {
      int var2 = ((Integer)var1.get(Type.VAR_INT, 0)).intValue();
      var1.set(Type.VAR_INT, 0, Integer.valueOf(this.c.a(var2)));
   }
}
