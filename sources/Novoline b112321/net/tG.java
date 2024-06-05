package net;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocol.packet.PacketWrapperImpl;
import net.EN;
import net.aKi;
import net.aMz;
import net.xN;

class tG implements EN {
   final aKi a;

   tG(aKi var1) {
      this.a = var1;
   }

   public void a(PacketWrapperImpl var1) throws Exception {
      aMz var2 = (aMz)var1.b(Type.FLAT_ITEM, 0);
      xN.b(var2);
   }
}
