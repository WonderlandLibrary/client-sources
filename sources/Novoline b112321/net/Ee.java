package net;

import net.aM2;
import net.aR2;
import net.acE;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

class Ee implements PacketHandler {
   final aM2 a;

   Ee(aM2 var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      aR2.a();
      int var3 = ((Integer)var1.get(Type.VAR_INT, 0)).intValue();
      if(var3 == 415) {
         var1.cancel();
      }

      if(var3 >= 416) {
         var1.set(Type.VAR_INT, 0, Integer.valueOf(var3 - 1));
      }

      if(acE.b() == null) {
         aR2.a(new acE[1]);
      }

   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
