package net;

import net.aRf;
import net.acE;
import net.ajH;
import net.axZ;
import net.pc;
import viaversion.viaversion.api.type.Type;

class an0 extends acE {
   final aRf c;

   an0(aRf var1) {
      this.c = var1;
   }

   public void registerMap() {
      this.a(Type.VAR_INT);
      ajH.b();
      this.a(Type.STRING, aRf.i);
      this.a(Type.STRING);
      this.a(new pc(this));
      this.a(Type.INT);
      this.a(Type.INT);
      this.a(Type.INT);
      this.a(Type.BYTE);
      this.a(Type.BYTE);
      this.a(Type.SHORT);
      this.a(axZ.j);
   }
}
