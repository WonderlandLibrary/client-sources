package net;

import net.acE;
import viaversion.viarewind.protocol.protocol1_8to1_9.storage.EntityTracker;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

final class a92 extends acE {
   public void registerMap() {
      this.a(Type.UNSIGNED_BYTE);
      this.a(Type.FLOAT);
      this.a(a92::lambda$registerMap$0);
   }

   private static void lambda$registerMap$0(PacketWrapper var0) throws Exception {
      short var1 = ((Short)var0.get(Type.UNSIGNED_BYTE, 0)).shortValue();
      if(var1 == 3) {
         ((EntityTracker)var0.user().b(EntityTracker.class)).setPlayerGamemode(((Float)var0.get(Type.FLOAT, 0)).intValue());
      }

   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
