package net;

import net.aEY;
import net.aTK;
import net.a_4;
import net.acE;
import viaversion.viaversion.api.type.Type;

final class aKm extends acE {
   final aTK c;

   aKm(aTK var1) {
      this.c = var1;
   }

   public void registerMap() {
      this.a(Type.VAR_INT);
      this.a(Type.UUID);
      this.a(Type.DOUBLE);
      this.a(Type.DOUBLE);
      this.a(Type.DOUBLE);
      this.a(Type.BYTE);
      this.a(Type.BYTE);
      this.a(aEY.a);
      this.a(this.c.getTrackerAndRewriter(aEY.a, a_4.PLAYER));
   }
}
