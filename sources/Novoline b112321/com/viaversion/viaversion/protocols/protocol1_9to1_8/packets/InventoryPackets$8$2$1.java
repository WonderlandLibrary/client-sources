package com.viaversion.viaversion.protocols.protocol1_9to1_8.packets;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocol.packet.PacketWrapperImpl;
import net.Zv;
import net.a0W;

class InventoryPackets$8$2$1 implements Zv {
   final short val$windowID;
   final short val$slot;
   final a0W a;

   InventoryPackets$8$2$1(a0W var1, short var2, short var3) {
      this.a = var1;
      this.val$windowID = var2;
      this.val$slot = var3;
   }

   public void b(PacketWrapperImpl var1) throws Exception {
      var1.a(Type.k, Byte.valueOf((byte)this.val$windowID));
      var1.a(Type.SHORT, Short.valueOf(this.val$slot));
      var1.a(Type.ITEM, (Object)null);
   }
}
