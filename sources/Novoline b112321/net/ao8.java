package net;

import net.N0;
import net.acE;
import viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets.EntityPackets1_14;
import viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets.EntityPackets1_14$10$1;
import viaversion.viaversion.api.type.Type;

class ao8 extends acE {
   final EntityPackets1_14 c;

   ao8(EntityPackets1_14 var1) {
      this.c = var1;
   }

   public void registerMap() {
      this.a(Type.INT);
      this.a(Type.UNSIGNED_BYTE);
      this.a(Type.INT);
      this.a(EntityPackets1_14.access$700(this.c, N0.PLAYER, Type.INT));
      this.a(EntityPackets1_14.access$800(this.c, 1));
      this.a(new EntityPackets1_14$10$1(this));
   }
}
