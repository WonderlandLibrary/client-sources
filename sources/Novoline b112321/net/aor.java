package net;

import com.viaversion.viaversion.api.connection.ConnectionManager;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocol.packet.PacketWrapperImpl;
import net.CQ;
import net.EN;
import net.N0;
import net.aSG;
import net.aqb;

class aor extends PacketRemapper {
   final aqb c;

   aor(aqb var1) {
      this.c = var1;
   }

   public void registerMap() {
      this.a(Type.VAR_INT);
      this.a(Type.UUID);
      this.a(Type.m);
      this.a(Type.m);
      this.a(Type.m);
      this.a(Type.k);
      this.a(Type.k);
      this.map(aSG.c, CQ.c);
      this.a(aqb.a(this.c, (Type)CQ.c, (ConnectionManager)N0.PLAYER));
      this.a(this::lambda$registerMap$0);
   }

   private void lambda$registerMap$0(PacketWrapperImpl var1) throws Exception {
      aqb.a(this.c).a(var1, true, false);
   }
}
