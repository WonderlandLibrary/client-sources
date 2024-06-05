package net;

import net.acE;
import net.axA;
import viaversion.viabackwards.protocol.protocol1_13to1_13_1.Protocol1_13To1_13_1;
import viaversion.viaversion.api.type.Type;

class aoe extends acE {
   final Protocol1_13To1_13_1 c;

   aoe(Protocol1_13To1_13_1 var1) {
      this.c = var1;
   }

   public void registerMap() {
      this.a(Type.VAR_INT);
      this.a(Type.VAR_INT);
      this.a(Type.VAR_INT);
      this.a(Type.VAR_INT);
      this.a(new axA(this));
   }
}
