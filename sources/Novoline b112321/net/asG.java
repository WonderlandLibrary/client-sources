package net;

import net.S3;
import net.a9v;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

class asG implements PacketHandler {
   final a9v a;

   asG(a9v var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      S3.b();
      String var3 = (String)var1.get(Type.STRING, 0);
      if(var3.equals("EntityHorse")) {
         var1.passthrough(Type.INT);
      }

   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
