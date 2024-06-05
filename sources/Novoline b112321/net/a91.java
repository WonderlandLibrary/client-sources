package net;

import net.S3;
import net.acE;
import viaversion.viarewind.protocol.protocol1_8to1_9.storage.BossBarStorage;
import viaversion.viarewind.protocol.protocol1_8to1_9.storage.PlayerPosition;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

final class a91 extends acE {
   public void registerMap() {
      this.a(Type.FLOAT);
      this.a(Type.FLOAT);
      this.a(Type.BOOLEAN);
      this.a(a91::lambda$registerMap$0);
      this.a(a91::lambda$registerMap$1);
   }

   private static void lambda$registerMap$1(PacketWrapper var0) throws Exception {
      ((BossBarStorage)var0.user().b(BossBarStorage.class)).updateLocation();
   }

   private static void lambda$registerMap$0(PacketWrapper var0) throws Exception {
      S3.b();
      PlayerPosition var2 = (PlayerPosition)var0.user().b(PlayerPosition.class);
      if(var2.getConfirmId() == -1) {
         var2.setYaw(((Float)var0.get(Type.FLOAT, 0)).floatValue());
         var2.setPitch(((Float)var0.get(Type.FLOAT, 1)).floatValue());
         var2.setOnGround(((Boolean)var0.get(Type.BOOLEAN, 0)).booleanValue());
      }
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
