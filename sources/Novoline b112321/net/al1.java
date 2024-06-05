package net;

import net.aR1;
import net.aWN;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

class al1 implements PacketHandler {
   final aWN a;

   al1(aWN var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      aR1.a();
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
         var1.write(Type.INT, Integer.valueOf(0));
         ++var4;
      }

   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
