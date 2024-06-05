package net;

import net.aQo;
import net.acE;
import viaversion.viaversion.api.type.Type;
import viaversion.viaversion.protocols.protocol1_10to1_9_3.Protocol1_10To1_9_3_4;

class a7z extends acE {
   final Protocol1_10To1_9_3_4 c;

   a7z(Protocol1_10To1_9_3_4 var1) {
      this.c = var1;
   }

   public void registerMap() {
      this.a(Type.STRING);
      this.a(Type.STRING);
      this.a(new aQo(this));
   }
}
