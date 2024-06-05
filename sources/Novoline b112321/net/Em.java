package net;

import com.google.common.primitives.Ints;
import net.aK5;
import net.q1;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

class Em implements PacketHandler {
   final aK5 a;

   Em(aK5 var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      q1.b();
      int var3 = ((Integer)var1.get(Type.VAR_INT, 0)).intValue();
      if(var3 == 0) {
         String var4 = (String)var1.read(Type.STRING);
         Integer var5;
         if(var4.length() < 19 || (var5 = Ints.tryParse(var4.substring(18))) == null) {
            var1.cancel();
            return;
         }

         var1.write(Type.INT, var5);
      }

      if(var3 == 1) {
         var1.passthrough(Type.BOOLEAN);
         var1.passthrough(Type.BOOLEAN);
         var1.read(Type.BOOLEAN);
         var1.read(Type.BOOLEAN);
      }

   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
