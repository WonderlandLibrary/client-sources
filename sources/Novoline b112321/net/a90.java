package net;

import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocol.packet.PacketWrapperImpl;
import net.Zv;

final class a90 extends PacketRemapper {
   public void registerMap() {
      this.a(Type.STRING);
      this.a(a90::lambda$registerMap$0);
      this.a(Type.v);
   }

   private static void lambda$registerMap$0(PacketWrapperImpl var0) throws Exception {
      var0.a(Type.c, Boolean.valueOf(false));
   }
}
