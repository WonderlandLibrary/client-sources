package net;

import io.netty.buffer.ByteBuf;
import net.aRi;
import net.acE;
import net.afz;
import viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.EntityTracker;
import viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.GameProfileStorage;
import viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.GameProfileStorage$GameProfile;
import viaversion.viarewind.utils.PacketUtil;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

final class aD3 extends acE {
   public void registerMap() {
      this.a(Type.STRING);
      this.a(aD3::lambda$registerMap$0);
   }

   private static void lambda$registerMap$0(PacketWrapper var0) throws Exception {
      afz.a();
      String var2 = (String)var0.get(Type.STRING, 0);
      int var3 = ((EntityTracker)var0.user().b(EntityTracker.class)).a();
      if(var3 == 3 && var2.toLowerCase().startsWith("/stp ")) {
         String var4 = var2.split(" ")[1];
         GameProfileStorage var5 = (GameProfileStorage)var0.user().b(GameProfileStorage.class);
         GameProfileStorage$GameProfile var6 = var5.get(var4, true);
         if(var6 != null && var6.uuid != null) {
            var0.cancel();
            PacketWrapper var7 = new PacketWrapper(24, (ByteBuf)null, var0.user());
            var7.write(Type.UUID, var6.uuid);
            PacketUtil.sendToServer(var7, aRi.class, true, true);
         }
      }

   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
