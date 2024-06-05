package net;

import com.google.gson.JsonElement;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocol.packet.PacketWrapperImpl;
import net.EN;
import net.km;

class a78 extends PacketRemapper {
   final km c;

   a78(km var1) {
      this.c = var1;
   }

   public void registerMap() {
      this.a(this::lambda$registerMap$0);
   }

   private void lambda$registerMap$0(PacketWrapperImpl var1) throws Exception {
      this.c.a((JsonElement)((JsonElement)var1.a(Type.p)));
   }
}
