package net;

import net.acE;
import net.k4;
import viaversion.viaversion.api.type.Type;
import viaversion.viaversion.protocols.protocol1_11to1_10.Protocol1_11To1_10;

class a7k extends acE {
   final Protocol1_11To1_10 c;

   a7k(Protocol1_11To1_10 var1) {
      this.c = var1;
   }

   public void registerMap() {
      this.a(Type.VAR_INT);
      this.a(Type.VAR_INT);
      this.a(new k4(this));
   }
}
