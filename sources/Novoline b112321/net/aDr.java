package net;

import net.acE;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

final class aDr extends acE {
   public void registerMap() {
      this.a(Type.COMPONENT);
      this.a(aDr::lambda$registerMap$0);
   }

   private static void lambda$registerMap$0(PacketWrapper var0) throws Exception {
      byte var1 = ((Byte)var0.read(Type.BYTE)).byteValue();
      if(var1 == 2) {
         var0.cancel();
      }

   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
