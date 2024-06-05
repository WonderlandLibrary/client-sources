package net;

import net.acE;
import net.aqL;
import net.rX;
import viaversion.viaversion.api.type.Type;

class aC7 extends acE {
   final aqL c;

   aC7(aqL var1) {
      this.c = var1;
   }

   public void registerMap() {
      aqL.a();
      this.a(Type.VAR_INT);
      this.a(Type.UUID);
      this.a(Type.VAR_INT);
      this.a(Type.DOUBLE);
      this.a(Type.DOUBLE);
      this.a(Type.DOUBLE);
      this.a(Type.BYTE);
      this.a(Type.BYTE);
      this.a(Type.BYTE);
      this.a(Type.SHORT);
      this.a(Type.SHORT);
      this.a(Type.SHORT);
      this.a(rX.a);
      this.a(aqL.b(this.c));
      this.a(aqL.a(this.c, rX.a));
   }
}
