package net;

import net.aRY;
import net.aV5;
import net.aXe;
import net.cq;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.minecraft.item.Item;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

class amk implements PacketHandler {
   final aV5 a;

   amk(aV5 var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      aXe.b();
      cq var3 = (cq)var1.user().b(cq.class);
      int var4 = ((Integer)var1.get(Type.VAR_INT, 0)).intValue();
      Item var5 = (Item)var1.get(Type.ITEM, 0);
      if(aRY.a(var5.getIdentifier())) {
         var3.m().add(Integer.valueOf(var4));
      } else {
         var3.m().remove(Integer.valueOf(var4));
      }
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
