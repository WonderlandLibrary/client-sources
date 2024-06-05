package net;

import net.acE;
import viaversion.viabackwards.protocol.protocol1_13to1_13_1.Protocol1_13To1_13_1;
import viaversion.viabackwards.protocol.protocol1_13to1_13_1.Protocol1_13To1_13_1$2$1;
import viaversion.viaversion.api.type.Type;

class ao6 extends acE {
   final Protocol1_13To1_13_1 c;

   ao6(Protocol1_13To1_13_1 var1) {
      this.c = var1;
   }

   public void registerMap() {
      this.a(Type.FLAT_ITEM);
      this.a(Type.BOOLEAN);
      this.a(new Protocol1_13To1_13_1$2$1(this));
   }
}
