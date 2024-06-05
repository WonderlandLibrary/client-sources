package net;

import net.aMl;
import net.aXe;
import net.cq;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.minecraft.item.Item;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

class at0 implements PacketHandler {
   final aMl a;

   at0(aMl var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      aXe.b();
      int var3 = ((Integer)var1.get(Type.VAR_INT, 0)).intValue();
      if(var3 == 2) {
         cq var4 = (cq)var1.user().b(cq.class);
         if(var4.j()) {
            var4.a((Item)null);
            var4.c(false);
         }
      }

   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
