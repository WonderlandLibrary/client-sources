package net;

import net.Uc;
import net.aRf;
import net.acE;
import viaversion.viaversion.api.type.Type;

class any extends acE {
   final aRf c;

   any(aRf var1) {
      this.c = var1;
   }

   public void registerMap() {
      this.a(Type.STRING);
      this.a(Type.BYTE);
      this.a(new Uc(this));
   }
}
