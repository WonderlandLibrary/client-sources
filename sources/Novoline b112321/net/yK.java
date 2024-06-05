package net;

import net.acY;
import net.aqp;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

class yK implements PacketHandler {
   final acY a;

   yK(acY var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      aqp.d();
      byte var3 = ((Byte)var1.get(Type.BYTE, 0)).byteValue();
      if(var3 == 35) {
         var1.clearPacket();
         var1.setId(30);
         var1.write(Type.UNSIGNED_BYTE, Short.valueOf((short)10));
         var1.write(Type.FLOAT, Float.valueOf(0.0F));
      }

   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
