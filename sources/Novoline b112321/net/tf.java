package net;

import java.util.UUID;
import net.afz;
import net.anx;
import viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.EntityTracker;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

class tf implements PacketHandler {
   final anx a;

   tf(anx var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      int var2 = afz.b();
      if(!var1.isCancelled()) {
         EntityTracker var3 = (EntityTracker)var1.user().b(EntityTracker.class);
         UUID var4 = var3.getPlayerUUID(((Integer)var1.get(Type.INT, 0)).intValue());
      }
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
