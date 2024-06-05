package net;

import net.aW2;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;
import viaversion.viaversion.protocols.protocol1_14to1_13_2.packets.WorldPackets;

class gs implements PacketHandler {
   final aW2 a;

   gs(aW2 var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      WorldPackets.b();
      int var3 = ((Integer)var1.get(Type.VAR_INT, 0)).intValue();
      if(var3 == 0) {
         var1.passthrough(Type.STRING);
      }

      if(var3 == 1) {
         var1.passthrough(Type.BOOLEAN);
         var1.passthrough(Type.BOOLEAN);
         var1.passthrough(Type.BOOLEAN);
         var1.passthrough(Type.BOOLEAN);
         var1.read(Type.BOOLEAN);
         var1.read(Type.BOOLEAN);
         var1.read(Type.BOOLEAN);
         var1.read(Type.BOOLEAN);
      }

   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
