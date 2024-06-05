package net;

import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocol.packet.PacketWrapperImpl;
import com.viaversion.viaversion.protocols.protocol1_9_1_2to1_9_3_4.types.Chunk1_9_3_4Type;
import net.EN;
import net.aqR;
import net.cT;

class an4 extends PacketRemapper {
   final aqR c;

   an4(aqR var1) {
      this.c = var1;
   }

   public void registerMap() {
      this.a(this::lambda$registerMap$0);
   }

   private void lambda$registerMap$0(PacketWrapperImpl var1) throws Exception {
      cT var2 = (cT)var1.e().b(cT.class);
      Chunk1_9_3_4Type var3 = new Chunk1_9_3_4Type(var2);
      Chunk var4 = (Chunk)var1.a((Type)var3);
      aqR.a(this.c, var4);
   }
}
