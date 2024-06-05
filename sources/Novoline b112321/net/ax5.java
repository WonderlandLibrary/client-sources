package net;

import java.util.List;
import net.aMN;
import net.aTi;
import net.aXe;
import net.cq;
import net.rX;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.Via;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

class ax5 implements PacketHandler {
   final aMN a;

   ax5(aMN var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      List var3 = (List)var1.get(rX.a, 0);
      int var4 = ((Integer)var1.get(Type.VAR_INT, 0)).intValue();
      aXe.b();
      cq var5 = (cq)var1.user().b(cq.class);
      if(var5.hasEntity(var4)) {
         ((aTi)this.a.c.get(aTi.class)).handleMetadata(var4, var3, var1.user());
      }

      Via.getPlatform().getLogger().warning("Unable to find entity for metadata, entity ID: " + var4);
      var3.clear();
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
