package net;

import net.acE;
import viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets.EntityPackets1_14;
import viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets.EntityPackets1_14$3$1;
import viaversion.viaversion.api.type.Type;

class aou extends acE {
   final EntityPackets1_14 c;

   aou(EntityPackets1_14 var1) {
      this.c = var1;
   }

   public void registerMap() {
      this.a(Type.VAR_INT);
      this.a(Type.SHORT);
      this.a(Type.SHORT);
      this.a(Type.SHORT);
      this.a(new EntityPackets1_14$3$1(this));
   }
}
