package net;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocol.packet.PacketWrapperImpl;
import net.EN;
import net.acx;
import net.aqw;

class Hg implements EN {
   final acx a;

   Hg(acx var1) {
      this.a = var1;
   }

   public void a(PacketWrapperImpl var1) throws Exception {
      aqw.a();
      int var3 = ((Integer)var1.a((Type)Type.VAR_INT)).intValue();
      int var4 = 0;
      if(var4 < var3 * 3) {
         var1.a(Type.k);
         ++var4;
      }

   }
}
