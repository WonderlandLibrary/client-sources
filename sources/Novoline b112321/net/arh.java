package net;

import net.aDd;
import net.aZ9;
import viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.EntityTracker;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

class arh implements PacketHandler {
   final aDd a;

   arh(aDd var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      int var2 = ((Integer)var1.get(Type.VAR_INT, 0)).intValue();
      short var3 = ((Short)var1.get(Type.UNSIGNED_BYTE, 0)).shortValue();
      EntityTracker var4 = (EntityTracker)var1.user().b(EntityTracker.class);
      var4.getClientEntityTypes().put(Integer.valueOf(var2), aZ9.a(var3, false));
      var4.sendMetadataBuffer(var2);
   }
}
