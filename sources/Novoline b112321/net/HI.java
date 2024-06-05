package net;

import java.util.List;
import net.Dk;
import net.afz;
import net.anj;
import net.axZ;
import net.t4;
import viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.EntityTracker;
import viaversion.viarewind.replacement.EntityReplacement;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

class HI implements PacketHandler {
   final anj a;

   HI(anj var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      afz.b();
      List var3 = (List)var1.get(axZ.j, 0);
      int var4 = ((Integer)var1.get(Type.INT, 0)).intValue();
      EntityTracker var5 = (EntityTracker)var1.user().b(EntityTracker.class);
      if(var5.getClientEntityTypes().containsKey(Integer.valueOf(var4))) {
         EntityReplacement var6 = var5.getEntityReplacement(var4);
         if(var6 != null) {
            var1.cancel();
            var6.updateMetadata(var3);
         }

         Dk.a((t4)var5.getClientEntityTypes().get(Integer.valueOf(var4)), var3);
         if(var3.isEmpty()) {
            var1.cancel();
         }
      }

      var5.addMetadataToBuffer(var4, var3);
      var1.cancel();
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
