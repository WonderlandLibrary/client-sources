package net;

import net.aEY;
import net.a_4;
import net.acE;
import net.aqN;
import viaversion.viaversion.api.type.Type;

class ao0 extends acE {
   final aqN c;

   ao0(aqN var1) {
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
      this.a(aqN.a(this.c, aEY.a, a_4.PLAYER));
   }
}
