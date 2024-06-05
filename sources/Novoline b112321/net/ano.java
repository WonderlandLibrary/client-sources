package net;

import com.google.gson.JsonElement;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocol.packet.PacketWrapperImpl;
import net.EN;
import net.aR7;

class ano extends PacketRemapper {
   final aR7 c;

   ano(aR7 var1) {
      this.c = var1;
   }

   public void registerMap() {
      this.a(this::lambda$registerMap$0);
      this.a(Type.k);
      this.map(Type.UUID, Type.af);
   }

   private void lambda$registerMap$0(PacketWrapperImpl var1) throws Exception {
      aR7.a(this.c).a((JsonElement)var1.a(Type.p));
   }
}
