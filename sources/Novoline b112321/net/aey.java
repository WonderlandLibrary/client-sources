package net;

import net.aMJ;
import net.aRY;
import net.aXe;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

class aey implements PacketHandler {
   final aMJ a;

   aey(aMJ var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      int var2 = aXe.b();
      if(((Integer)var1.get(Type.VAR_INT, 0)).intValue() == 2) {
         var1.passthrough(Type.VAR_INT);
         var1.passthrough(Type.INT);
         aRY.i.write(var1, var1.read(Type.STRING));
      }

   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
