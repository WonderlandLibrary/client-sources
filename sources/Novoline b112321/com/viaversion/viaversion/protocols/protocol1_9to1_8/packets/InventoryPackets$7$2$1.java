package com.viaversion.viaversion.protocols.protocol1_9to1_8.packets;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocol.packet.PacketWrapperImpl;
import net.Zv;
import net.aNs;

class InventoryPackets$7$2$1 implements Zv {
   final short val$slot;
   final aNs b;

   InventoryPackets$7$2$1(aNs var1, short var2) {
      this.b = var1;
      this.val$slot = var2;
   }

   public void b(PacketWrapperImpl var1) throws Exception {
      var1.a(Type.k, Byte.valueOf((byte)0));
      var1.a(Type.SHORT, Short.valueOf(this.val$slot));
      var1.a(Type.ITEM, (Object)null);
   }
}
