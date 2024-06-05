package net;

import net.aQU;
import net.acE;
import viaversion.viaversion.api.type.Type;

class a73 extends acE {
   final Type c;
   final aQU d;

   a73(aQU var1, Type var2) {
      this.d = var1;
      this.c = var2;
   }

   public void registerMap() {
      this.a(Type.BYTE);
      this.a(Type.SHORT);
      this.a(this.c);
      this.a(this.d.a(this.c));
   }
}
