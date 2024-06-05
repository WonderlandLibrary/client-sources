package net;

import net.acE;
import net.aq0;
import viaversion.viabackwards.protocol.protocol1_11_1to1_12.packets.BlockItemPackets1_12$6$1;
import viaversion.viaversion.api.type.Type;

class ac5 extends acE {
   final aq0 c;

   ac5(aq0 var1) {
      this.c = var1;
   }

   public void registerMap() {
      this.a(Type.INT);
      this.a(Type.INT);
      this.a(Type.BLOCK_CHANGE_RECORD_ARRAY);
      this.a(new BlockItemPackets1_12$6$1(this));
   }
}
