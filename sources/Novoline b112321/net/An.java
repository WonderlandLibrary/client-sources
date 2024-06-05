package net;

import net.aKl;
import net.aRw;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

class An implements PacketHandler {
   final aKl a;

   An(aKl var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      aRw.a();
      int var3 = ((Integer)var1.get(Type.VAR_INT, 0)).intValue();
      if(var3 == 0) {
         var1.passthrough(Type.COMPONENT);
         var1.passthrough(Type.FLOAT);
         var1.passthrough(Type.VAR_INT);
         var1.passthrough(Type.VAR_INT);
         short var4 = (short)((Byte)var1.read(Type.BYTE)).byteValue();
         if((var4 & 2) != 0) {
            var4 = (short)(var4 | 4);
         }

         var1.write(Type.UNSIGNED_BYTE, Short.valueOf(var4));
      }

   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
