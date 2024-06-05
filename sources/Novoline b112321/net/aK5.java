package net;

import net.Em;
import net.acE;
import viaversion.viaversion.api.type.Type;
import viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2;

class aK5 extends acE {
   final Protocol1_13To1_12_2 c;

   aK5(Protocol1_13To1_12_2 var1) {
      this.c = var1;
   }

   public void registerMap() {
      this.a(Type.VAR_INT);
      this.a(new Em(this));
   }
}
