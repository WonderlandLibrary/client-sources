package net;

import net.acE;
import net.aq0;
import net.iF;
import viaversion.viaversion.api.type.Type;

class aca extends acE {
   final aq0 c;

   aca(aq0 var1) {
      this.c = var1;
   }

   public void registerMap() {
      this.a(Type.UNSIGNED_BYTE);
      this.a(Type.SHORT);
      this.a(Type.BYTE);
      this.a(Type.SHORT);
      this.a(Type.VAR_INT);
      this.a(Type.ITEM);
      this.a(new iF(this));
   }
}
