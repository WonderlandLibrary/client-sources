package net;

import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocol.packet.PacketWrapperImpl;
import net.EN;
import net.aRs;
import net.cT;

class a7x extends PacketRemapper {
   final aRs c;

   a7x(aRs var1) {
      this.c = var1;
   }

   public void registerMap() {
      this.a(Type.I);
      this.a(a7x::lambda$registerMap$0);
   }

   private static void lambda$registerMap$0(PacketWrapperImpl var0) throws Exception {
      cT var1 = (cT)var0.e().b(cT.class);
      int var2 = ((Integer)var0.b(Type.I, 0)).intValue();
      var1.c(var2);
   }
}
