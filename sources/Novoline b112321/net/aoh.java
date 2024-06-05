package net;

import net.DY;
import net.acE;
import net.aqN;
import viaversion.viaversion.api.type.Type;

class aoh extends acE {
   final aqN c;

   aoh(aqN var1) {
      this.c = var1;
   }

   public void registerMap() {
      this.a(Type.VAR_INT);
      this.a(Type.UUID);
      this.a(Type.BYTE);
      this.a(Type.DOUBLE);
      this.a(Type.DOUBLE);
      this.a(Type.DOUBLE);
      this.a(Type.BYTE);
      this.a(Type.BYTE);
      this.a(Type.INT);
      this.a(new DY(this));
   }
}
