package net;

import net.An;
import net.aRw;
import net.acE;
import viaversion.viaversion.api.type.Type;

class aKl extends acE {
   final aRw c;

   aKl(aRw var1) {
      this.c = var1;
   }

   public void registerMap() {
      this.a(Type.UUID);
      this.a(Type.VAR_INT);
      this.a(new An(this));
   }
}
