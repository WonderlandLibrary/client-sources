package net;

import net.acE;
import viaversion.viaversion.api.type.Type;
import viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2;
import viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2$16$1;

class aK0 extends acE {
   final Protocol1_13To1_12_2 c;

   aK0(Protocol1_13To1_12_2 var1) {
      this.c = var1;
   }

   public void registerMap() {
      this.a(Type.VAR_INT);
      this.a(Type.BYTE);
      this.a(Type.BOOLEAN);
      this.a(new Protocol1_13To1_12_2$16$1(this));
   }
}
