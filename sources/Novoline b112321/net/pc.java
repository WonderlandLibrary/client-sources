package net;

import net.ajH;
import net.an0;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

class pc implements PacketHandler {
   final an0 a;

   pc(an0 var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      ajH.b();
      int var3 = ((Integer)var1.read(Type.VAR_INT)).intValue();
      int var4 = 0;
      if(var4 < var3 * 3) {
         var1.read(Type.STRING);
         ++var4;
      }

   }
}
