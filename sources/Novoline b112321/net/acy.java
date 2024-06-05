package net;

import net.acE;
import net.amW;
import net.aqp;
import net.rX;
import viaversion.viaversion.api.type.Type;

class acy extends acE {
   final aqp c;

   acy(aqp var1) {
      this.c = var1;
   }

   public void registerMap() {
      this.a(Type.VAR_INT);
      this.a(Type.UUID);
      this.a(Type.VAR_INT, Type.UNSIGNED_BYTE);
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
      this.a(aqp.a(this.c, (Type)Type.UNSIGNED_BYTE, 0));
      this.a(new amW(this));
   }
}
