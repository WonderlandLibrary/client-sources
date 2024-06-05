package com.viaversion.viaversion.protocols.protocol1_9to1_8.packets;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocol.packet.PacketWrapperImpl;
import net.EN;
import net.aM4;
import net.aXe;
import net.cq;

class SpawnPackets$8$1 implements EN {
   final aM4 a;

   SpawnPackets$8$1(aM4 var1) {
      this.a = var1;
   }

   public void a(PacketWrapperImpl var1) throws Exception {
      aXe.b();
      int[] var3 = (int[])var1.b(Type.VAR_INT_ARRAY_PRIMITIVE, 0);
      int var5 = var3.length;
      int var6 = 0;
      if(var6 < var5) {
         int var7 = var3[var6];
         ((cq)var1.e().b(cq.class)).b(var7);
         ++var6;
      }

   }
}
