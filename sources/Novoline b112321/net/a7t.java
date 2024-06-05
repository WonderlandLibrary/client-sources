package net;

import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocol.packet.PacketWrapperImpl;
import net.EN;
import net.aRs;
import net.cT;

class a7t extends PacketRemapper {
   final aRs c;

   a7t(aRs var1) {
      this.c = var1;
   }

   public void registerMap() {
      this.a(Type.I);
      this.a(Type.M);
      this.a(Type.I);
      this.a(a7t::lambda$registerMap$0);
   }

   private static void lambda$registerMap$0(PacketWrapperImpl var0) throws Exception {
      cT var1 = (cT)var0.e().b(cT.class);
      int var2 = ((Integer)var0.b(Type.I, 1)).intValue();
      var1.c(var2);
   }
}
