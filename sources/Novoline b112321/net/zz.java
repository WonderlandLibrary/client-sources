package net;

import net.aCW;
import net.aqL;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.minecraft.item.Item;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

class zz implements PacketHandler {
   final aCW a;

   zz(aCW var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      String var2 = aqL.a();
      if(((String)var1.get(Type.STRING, 0)).equalsIgnoreCase("MC|TrList")) {
         var1.passthrough(Type.INT);
         short var3 = ((Short)var1.passthrough(Type.UNSIGNED_BYTE)).shortValue();
         int var4 = 0;
         if(var4 < var3) {
            var1.write(Type.ITEM, this.a.c.a((Item)var1.read(Type.ITEM)));
            var1.write(Type.ITEM, this.a.c.a((Item)var1.read(Type.ITEM)));
            boolean var5 = ((Boolean)var1.passthrough(Type.BOOLEAN)).booleanValue();
            var1.write(Type.ITEM, this.a.c.a((Item)var1.read(Type.ITEM)));
            var1.passthrough(Type.BOOLEAN);
            var1.passthrough(Type.INT);
            var1.passthrough(Type.INT);
            ++var4;
         }
      }

   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
