package net;

import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocol.packet.PacketWrapperImpl;
import net.EN;

final class a7c extends PacketRemapper {
   public void registerMap() {
      this.a(Type.VAR_INT);
      this.a(Type.k);
      this.a(a7c::lambda$registerMap$0);
   }

   private static void lambda$registerMap$0(PacketWrapperImpl var0) throws Exception {
      Boolean var10000 = (Boolean)var0.b(Type.c);
   }
}
