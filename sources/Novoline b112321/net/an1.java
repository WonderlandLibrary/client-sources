package net;

import net.acE;
import net.aq1;
import net.aqR;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.minecraft.item.Item;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

class an1 extends acE {
   final aqR c;

   an1(aqR var1) {
      this.c = var1;
   }

   public void registerMap() {
      this.a(Type.STRING);
      this.a(this::lambda$registerMap$0);
   }

   private void lambda$registerMap$0(PacketWrapper var1) throws Exception {
      String[] var2 = aq1.a();
      if(((String)var1.get(Type.STRING, 0)).equalsIgnoreCase("MC|TrList")) {
         var1.passthrough(Type.INT);
         short var3 = ((Short)var1.passthrough(Type.UNSIGNED_BYTE)).shortValue();
         int var4 = 0;
         if(var4 < var3) {
            var1.write(Type.ITEM, this.c.a((Item)var1.read(Type.ITEM)));
            var1.write(Type.ITEM, this.c.a((Item)var1.read(Type.ITEM)));
            boolean var5 = ((Boolean)var1.passthrough(Type.BOOLEAN)).booleanValue();
            var1.write(Type.ITEM, this.c.a((Item)var1.read(Type.ITEM)));
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
