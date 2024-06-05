package net;

import java.util.List;
import net.aVk;
import net.rX;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;

class a4l implements PacketHandler {
   final aVk a;

   a4l(aVk var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      List var2 = (List)var1.get(rX.a, 0);
      if(var2.isEmpty()) {
         var1.cancel();
      }

   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
