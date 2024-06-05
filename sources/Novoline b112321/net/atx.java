package net;

import net.ack;
import net.aqw;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

class atx implements PacketHandler {
   final ack a;

   atx(ack var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      String var2 = aqw.a();
      if(((Short)var1.get(Type.UNSIGNED_BYTE, 0)).shortValue() == 11) {
         var1.cancel();
      }

   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
