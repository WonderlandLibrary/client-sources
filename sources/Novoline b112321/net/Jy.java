package net;

import net.a7_;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

class Jy implements PacketHandler {
   final a7_ a;

   Jy(a7_ var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      String var2 = (String)var1.get(Type.STRING, 0);
      if(var2.length() > 100) {
         var1.set(Type.STRING, 0, var2.substring(0, 100));
      }

   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
