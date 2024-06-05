package net;

import net.Q3;
import net.aVW;
import net.awA;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.minecraft.item.Item;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

class ap_ implements PacketHandler {
   final aVW a;

   ap_(aVW var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      awA.b();
      int var3 = ((Integer)var1.get(Type.INT, 0)).intValue();
      if(var3 == 3 || var3 == 23) {
         int var4 = ((Integer)var1.passthrough(Type.VAR_INT)).intValue();
         var1.set(Type.VAR_INT, 0, Integer.valueOf(this.a.c.a().getNewBlockStateId(var4)));
      }

      if(var3 == 32) {
         Q3.a((Item)var1.passthrough(Type.FLAT_VAR_INT_ITEM));
      }

   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
