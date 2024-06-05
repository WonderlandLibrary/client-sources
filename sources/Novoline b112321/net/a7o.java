package net;

import net.S3;
import net.acE;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

final class a7o extends acE {
   public void registerMap() {
      this.a(Type.POSITION);
      this.a(Type.UNSIGNED_BYTE);
      this.a(Type.UNSIGNED_BYTE);
      this.a(Type.VAR_INT);
      this.a(a7o::lambda$registerMap$0);
   }

   private static void lambda$registerMap$0(PacketWrapper var0) throws Exception {
      S3.b();
      int var2 = ((Integer)var0.get(Type.VAR_INT, 0)).intValue();
      if(var2 >= 219 && var2 <= 234) {
         var2 = 130;
         var0.set(Type.VAR_INT, 0, Integer.valueOf(130));
      }

   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
