package net;

import net.BS;
import net.aWf;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

class a8e implements PacketHandler {
   final aWf a;

   a8e(aWf var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      BS.b();
      short var3 = ((Short)var1.get(Type.SHORT, 0)).shortValue();
      if(var3 >= 4 && var3 <= 6) {
         var1.set(Type.SHORT, 1, Short.valueOf((short)this.a.c.getMappingData().getEnchantmentMappings().getNewId(((Short)var1.get(Type.SHORT, 1)).shortValue())));
      }

   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
