package com.viaversion.viaversion.protocols.protocol1_9to1_8.packets;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocol.packet.PacketWrapperImpl;
import net.ME;
import net.Zv;

class InventoryPackets$1$1$1 implements Zv {
   final short b;
   final short a;
   final short d;
   final ME c;

   InventoryPackets$1$1$1(ME var1, short var2, short var3, short var4) {
      this.c = var1;
      this.b = var2;
      this.a = var3;
      this.d = var4;
   }

   public void b(PacketWrapperImpl var1) throws Exception {
      var1.a(Type.M, Short.valueOf(this.b));
      var1.a(Type.SHORT, Short.valueOf(this.a));
      var1.a(Type.SHORT, Short.valueOf(this.d));
   }
}
