package net;

import net.S3;
import net.acE;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

final class a9d extends acE {
   public void registerMap() {
      this.a(Type.VAR_INT);
      this.a(Type.VAR_INT);
      this.a(a9d::lambda$registerMap$0);
   }

   private static void lambda$registerMap$0(PacketWrapper var0) throws Exception {
      S3.b();
      int var2 = ((Integer)var0.get(Type.VAR_INT, 1)).intValue();
      if(var2 == 2) {
         var0.passthrough(Type.FLOAT);
         var0.passthrough(Type.FLOAT);
         var0.passthrough(Type.FLOAT);
      }

      if(var2 == 2 || var2 == 0) {
         var0.write(Type.VAR_INT, Integer.valueOf(0));
      }

   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
