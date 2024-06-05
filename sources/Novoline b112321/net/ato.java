package net;

import net.afz;
import net.anx;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

class ato implements PacketHandler {
   final anx a;

   ato(anx var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      int var2 = afz.b();
      if(((Short)var1.get(Type.SHORT, 0)).shortValue() > 4) {
         var1.cancel();
      }

   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
