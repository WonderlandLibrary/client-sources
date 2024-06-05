package net;

import net.acE;
import net.aq0;
import net.lI;
import viaversion.viabackwards.protocol.protocol1_11_1to1_12.packets.BlockItemPackets1_12$1$1;
import viaversion.viaversion.api.type.Type;

class acx extends acE {
   final aq0 c;

   acx(aq0 var1) {
      this.c = var1;
   }

   public void registerMap() {
      this.a(Type.VAR_INT);
      this.a(Type.BYTE);
      this.a(Type.BOOLEAN);
      this.a(new BlockItemPackets1_12$1$1(this));
      this.a(new lI(this));
   }
}
