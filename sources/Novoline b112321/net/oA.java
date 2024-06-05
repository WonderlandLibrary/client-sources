package net;

import net.aRC;
import net.aoY;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

class oA implements PacketHandler {
   final aoY a;

   oA(aoY var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      aRC.b();
      var1.passthrough(Type.VAR_INT);
      short var3 = ((Short)var1.passthrough(Type.UNSIGNED_BYTE)).shortValue();
      int var4 = 0;
      if(var4 < var3) {
         var1.passthrough(Type.FLAT_VAR_INT_ITEM);
         var1.passthrough(Type.FLAT_VAR_INT_ITEM);
         if(((Boolean)var1.passthrough(Type.BOOLEAN)).booleanValue()) {
            var1.passthrough(Type.FLAT_VAR_INT_ITEM);
         }

         var1.passthrough(Type.BOOLEAN);
         var1.passthrough(Type.INT);
         var1.passthrough(Type.INT);
         var1.passthrough(Type.INT);
         var1.passthrough(Type.INT);
         var1.passthrough(Type.FLOAT);
         ++var4;
      }

      var1.passthrough(Type.VAR_INT);
      var1.passthrough(Type.VAR_INT);
      var1.passthrough(Type.BOOLEAN);
      var1.read(Type.BOOLEAN);
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
