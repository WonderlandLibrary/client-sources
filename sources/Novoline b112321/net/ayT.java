package net;

import net.afz;
import net.an_;
import viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.EntityTracker;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

class ayT implements PacketHandler {
   final an_ a;

   ayT(an_ var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      afz.a();
      boolean var3 = ((Boolean)var1.get(Type.BOOLEAN, 0)).booleanValue();
      if(!var3) {
         int var4 = ((Integer)var1.get(Type.INT, 0)).intValue();
         int var5 = ((Integer)var1.get(Type.INT, 1)).intValue();
         EntityTracker var6 = (EntityTracker)var1.user().b(EntityTracker.class);
         var6.setPassenger(var5, var4);
      }
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
