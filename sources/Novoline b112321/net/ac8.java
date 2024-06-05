package net;

import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocol.packet.PacketWrapperImpl;
import net.EN;
import net.aqF;

class ac8 extends PacketRemapper {
   final aqF c;

   ac8(aqF var1) {
      this.c = var1;
   }

   public void registerMap() {
      this.a(Type.VAR_INT);
      this.a(Type.UUID);
      this.a(Type.VAR_INT);
      this.a(this::lambda$registerMap$0);
   }

   private void lambda$registerMap$0(PacketWrapperImpl var1) throws Exception {
      aqF.a((aqF)this.c, (PacketWrapperImpl)var1);
   }
}
