package net;

import java.util.List;
import net.S3;
import net.a9k;
import net.arP;
import net.t4;
import viaversion.viarewind.protocol.protocol1_8to1_9.storage.EntityTracker;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;
import viaversion.viaversion.api.type.types.version.Types1_8;

class adH implements PacketHandler {
   final a9k a;

   adH(a9k var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      S3.b();
      List var3 = (List)var1.get(Types1_8.METADATA_LIST, 0);
      int var4 = ((Integer)var1.get(Type.VAR_INT, 0)).intValue();
      EntityTracker var5 = (EntityTracker)var1.user().b(EntityTracker.class);
      if(var5.getEntityReplacement(var4) != null) {
         var5.getEntityReplacement(var4).updateMetadata(var3);
      }

      if(var5.getClientEntityTypes().containsKey(Integer.valueOf(var4))) {
         arP.a((t4)var5.getClientEntityTypes().get(Integer.valueOf(var4)), var3);
      }

      var1.cancel();
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
