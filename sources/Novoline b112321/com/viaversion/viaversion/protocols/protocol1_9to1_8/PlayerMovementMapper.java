package com.viaversion.viaversion.protocols.protocol1_9to1_8;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocol.packet.PacketWrapperImpl;
import net.Cw;
import net.EN;
import net.cY;

public class PlayerMovementMapper implements EN {
   public void a(PacketWrapperImpl var1) throws Exception {
      Cw.b();
      cY var3 = (cY)var1.e().b(cY.class);
      var3.e();
      if(var1.c(Type.c, 0)) {
         var3.a(((Boolean)var1.b(Type.c, 0)).booleanValue());
      }

   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
