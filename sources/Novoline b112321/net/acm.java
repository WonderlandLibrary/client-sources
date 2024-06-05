package net;

import com.viaversion.viaversion.api.connection.ConnectionManager;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocol.packet.PacketWrapperImpl;
import net.EN;
import net.aqS;

class acm extends PacketRemapper {
   final Type c;
   final ConnectionManager d;
   final aqS e;

   acm(aqS var1, Type var2, ConnectionManager var3) {
      this.e = var1;
      this.c = var2;
      this.d = var3;
   }

   public void registerMap() {
      this.a(this.c);
      this.a(this::lambda$registerMap$0);
   }

   private void lambda$registerMap$0(Type var1, ConnectionManager var2, PacketWrapperImpl var3) throws Exception {
      this.e.a(var3, ((Integer)var3.b(var1, 0)).intValue(), var2);
   }
}
