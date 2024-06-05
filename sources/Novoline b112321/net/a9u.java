package net;

import net.S3;
import net.acE;
import viaversion.viarewind.protocol.protocol1_8to1_9.storage.EntityTracker;
import viaversion.viarewind.protocol.protocol1_8to1_9.storage.Levitation;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

final class a9u extends acE {
   public void registerMap() {
      this.a(Type.VAR_INT);
      this.a(Type.BYTE);
      this.a(a9u::lambda$registerMap$0);
   }

   private static void lambda$registerMap$0(PacketWrapper var0) throws Exception {
      S3.b();
      byte var2 = ((Byte)var0.get(Type.BYTE, 0)).byteValue();
      if(var2 > 23) {
         var0.cancel();
      }

      if(var2 == 25) {
         if(((Integer)var0.get(Type.VAR_INT, 0)).intValue() != ((EntityTracker)var0.user().b(EntityTracker.class)).getPlayerId()) {
            return;
         }

         Levitation var3 = (Levitation)var0.user().b(Levitation.class);
         var3.setActive(false);
      }

   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
