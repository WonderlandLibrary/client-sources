package net;

import net.aMR;
import net.aXe;
import net.cq;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.minecraft.item.Item;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

class Ey implements PacketHandler {
   final aMR a;

   Ey(aMR var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      aXe.b();
      short var3 = ((Short)var1.get(Type.UNSIGNED_BYTE, 0)).shortValue();
      if(var3 == 5 || var3 == 4 || var3 == 3) {
         cq var4 = (cq)var1.user().b(cq.class);
         if(var4.j()) {
            var4.c(false);
            var4.a((Item)null);
         }
      }

   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
