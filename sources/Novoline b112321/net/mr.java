package net;

import net.aMr;
import net.cq;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;
import viaversion.viaversion.protocols.protocol1_9to1_8.chat.GameMode;

class mr implements PacketHandler {
   final aMr a;

   mr(aMr var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      cq var2 = (cq)var1.user().b(cq.class);
      var2.a(GameMode.getById(((Short)var1.get(Type.UNSIGNED_BYTE, 0)).shortValue()));
   }
}
