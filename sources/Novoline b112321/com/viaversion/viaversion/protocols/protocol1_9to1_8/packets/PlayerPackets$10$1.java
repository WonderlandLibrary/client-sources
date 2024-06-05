package com.viaversion.viaversion.protocols.protocol1_9to1_8.packets;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocol.packet.PacketWrapperImpl;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.storage.ClientChunks;
import net.EN;
import net.aMV;

class PlayerPackets$10$1 implements EN {
   final aMV a;

   PlayerPackets$10$1(aMV var1) {
      this.a = var1;
   }

   public void a(PacketWrapperImpl var1) throws Exception {
      float var2 = ((Float)var1.b(Type.FLOAT, 0)).floatValue();
      if(var2 <= 0.0F) {
         ClientChunks var3 = (ClientChunks)var1.e().b(ClientChunks.class);
         var3.getBulkChunks().clear();
         var3.getLoadedChunks().clear();
      }

   }
}
