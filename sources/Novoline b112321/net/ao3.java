package net;

import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocol.packet.PacketWrapperImpl;
import net.EN;
import net.N0;
import net.aqb;

class ao3 extends PacketRemapper {
   final aqb c;

   ao3(aqb var1) {
      this.c = var1;
   }

   public void registerMap() {
      this.a(Type.VAR_INT);
      this.a(Type.m);
      this.a(Type.m);
      this.a(Type.m);
      this.a(this::lambda$registerMap$0);
   }

   private void lambda$registerMap$0(PacketWrapperImpl var1) throws Exception {
      this.c.a(var1, ((Integer)var1.b(Type.VAR_INT, 0)).intValue(), N0.EXPERIENCE_ORB);
   }
}
