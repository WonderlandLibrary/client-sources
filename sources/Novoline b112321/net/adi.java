package net;

import net.aDj;
import net.t4;
import viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.EntityTracker;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

class adi implements PacketHandler {
   final aDj a;

   adi(aDj var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      int var2 = ((Integer)var1.get(Type.VAR_INT, 0)).intValue();
      EntityTracker var3 = (EntityTracker)var1.user().b(EntityTracker.class);
      var3.getClientEntityTypes().put(Integer.valueOf(var2), t4.LIGHTNING);
      var3.sendMetadataBuffer(var2);
   }
}
