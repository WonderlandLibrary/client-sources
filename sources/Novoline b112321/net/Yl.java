package net;

import net.aog;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

class Yl implements PacketHandler {
   final aog a;

   Yl(aog var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      int var2 = ((Integer)var1.get(Type.INT, 0)).intValue();
      if(var2 == 27) {
         var1.write(Type.FLAT_ITEM, var1.read(Type.FLAT_VAR_INT_ITEM));
      }

   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
