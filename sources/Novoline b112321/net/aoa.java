package net;

import net.RP;
import net.acE;
import viaversion.viabackwards.protocol.protocol1_14_4to1_15.packets.BlockItemPackets1_15;
import viaversion.viaversion.api.type.Type;

class aoa extends acE {
   final BlockItemPackets1_15 c;

   aoa(BlockItemPackets1_15 var1) {
      this.c = var1;
   }

   public void registerMap() {
      this.a(Type.INT);
      this.a(Type.BOOLEAN);
      this.a(Type.DOUBLE, Type.FLOAT);
      this.a(Type.DOUBLE, Type.FLOAT);
      this.a(Type.DOUBLE, Type.FLOAT);
      this.a(Type.FLOAT);
      this.a(Type.FLOAT);
      this.a(Type.FLOAT);
      this.a(Type.FLOAT);
      this.a(Type.INT);
      this.a(new RP(this));
   }
}
