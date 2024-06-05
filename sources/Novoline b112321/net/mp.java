package net;

import net.aKR;
import net.cT;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.Via;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;
import viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.ConnectionData;

class mp implements PacketHandler {
   final aKR a;

   mp(aKR var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      cT var2 = (cT)var1.user().b(cT.class);
      int var3 = ((Integer)var1.get(Type.INT, 0)).intValue();
      var2.c(var3);
      if(Via.getConfig().isServersideBlockConnections()) {
         ConnectionData.clearBlockStorage(var1.user());
      }

   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
