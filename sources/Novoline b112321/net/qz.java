package net;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocol.packet.PacketWrapperImpl;
import net.EN;
import net.ao8;

class qz implements EN {
   final ao8 a;

   qz(ao8 var1) {
      this.a = var1;
   }

   public void a(PacketWrapperImpl var1) throws Exception {
      var1.a(Type.M, Short.valueOf((short)0));
      var1.a(Type.M);
      var1.a(Type.STRING);
      var1.b((Type)Type.VAR_INT);
   }
}
