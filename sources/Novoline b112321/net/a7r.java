package net;

import net.aQU;
import net.acE;
import viaversion.viaversion.api.type.Type;

class a7r extends acE {
   final Type d;
   final aQU c;

   a7r(aQU var1, Type var2) {
      this.c = var1;
      this.d = var2;
   }

   public void registerMap() {
      this.a(Type.SHORT);
      this.a(this.d);
      this.a(this.c.c(this.d));
   }
}
