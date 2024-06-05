package net;

import net.Xw;
import net.aEY;
import net.acE;
import net.aqN;
import viaversion.viaversion.api.type.Type;

class aoT extends acE {
   final aqN c;

   aoT(aqN var1) {
      this.c = var1;
   }

   public void registerMap() {
      this.a(Type.VAR_INT);
      this.a(Type.UUID);
      this.a(Type.VAR_INT);
      this.a(Type.DOUBLE);
      this.a(Type.DOUBLE);
      this.a(Type.DOUBLE);
      this.a(Type.BYTE);
      aqN.a();
      this.a(Type.BYTE);
      this.a(Type.BYTE);
      this.a(Type.SHORT);
      this.a(Type.SHORT);
      this.a(Type.SHORT);
      this.a(aEY.a);
      this.a(aqN.a(this.c));
      this.a(new Xw(this));
   }
}
