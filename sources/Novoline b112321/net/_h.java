package net;

import net.aVt;
import net.aXe;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

class _h implements PacketHandler {
   final aVt a;

   _h(aVt var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      aXe.b();
      int var3 = ((Integer)var1.get(Type.VAR_INT, 1)).intValue();
      if(var3 == 6 || var3 == 8) {
         var1.cancel();
      }

      if(var3 == 7) {
         var1.set(Type.VAR_INT, 1, Integer.valueOf(6));
      }

   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
