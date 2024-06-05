package net;

import net.BS;
import net.aKX;
import net.q1;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

class b0 implements PacketHandler {
   final aKX a;

   b0(aKX var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      q1.b();
      int var3 = ((Integer)var1.get(Type.INT, 0)).intValue();
      int var4 = ((Integer)var1.get(Type.INT, 1)).intValue();
      if(var3 == 1010) {
         var1.set(Type.INT, 1, Integer.valueOf(this.a.c.getMappingData().getItemMappings().get(var4 << 4)));
      }

      if(var3 == 2001) {
         int var5 = var4 & 4095;
         int var6 = var4 >> 12;
         var1.set(Type.INT, 1, Integer.valueOf(BS.a(var5 << 4 | var6)));
      }

   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
