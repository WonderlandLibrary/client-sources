package net;

import net.aSG;
import net.acE;
import net.aqU;
import net.zI;
import viaversion.viaversion.api.type.Type;

class anc extends acE {
   final aqU c;

   anc(aqU var1) {
      this.c = var1;
   }

   public void registerMap() {
      aqU.d();
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
      this.a(aSG.c);
      this.a(new zI(this));
   }
}
