package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets;

import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets.EntityPackets1_13;
import com.viaversion.viaversion.api.connection.ConnectionManager;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import net.YD;
import net.aEY;
import net.a_4;

class EntityPackets1_13$4 extends PacketRemapper {
   final EntityPackets1_13 this$0;

   EntityPackets1_13$4(EntityPackets1_13 var1) {
      this.this$0 = var1;
   }

   public void registerMap() {
      this.a(Type.VAR_INT);
      this.a(Type.UUID);
      this.a(Type.m);
      this.a(Type.m);
      this.a(Type.m);
      this.a(Type.k);
      this.a(Type.k);
      this.map(aEY.a, YD.b);
      this.a(EntityPackets1_13.a(this.this$0, (Type)YD.b, (ConnectionManager)a_4.PLAYER));
   }
}
