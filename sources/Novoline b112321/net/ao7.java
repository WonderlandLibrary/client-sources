package net;

import net.acE;
import net.aq6;
import viaversion.viaversion.api.type.Type;

class ao7 extends acE {
   final aq6 c;

   ao7(aq6 var1) {
      this.c = var1;
   }

   public void registerMap() {
      this.a(Type.VAR_INT);
      this.a(Type.BYTE);
      this.a(Type.BOOLEAN);
      this.a(Type.BOOLEAN, Type.NOTHING);
   }
}
