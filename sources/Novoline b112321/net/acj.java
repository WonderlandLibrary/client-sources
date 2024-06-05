package net;

import net.acE;
import net.aq0;
import net.os;
import viaversion.viaversion.api.type.Type;

class acj extends acE {
   final aq0 c;

   acj(aq0 var1) {
      this.c = var1;
   }

   public void registerMap() {
      this.a(Type.POSITION);
      this.a(Type.VAR_INT);
      this.a(new os(this));
   }
}
