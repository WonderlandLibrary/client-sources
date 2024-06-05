package net;

import io.netty.buffer.ByteBuf;
import net.aRi;
import net.acE;
import net.afz;
import viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.EntityTracker;
import viaversion.viarewind.utils.PacketUtil;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

final class aD1 extends acE {
   public void registerMap() {
      this.a(Type.FLOAT);
      this.a(Type.FLOAT);
      this.a(aD1::lambda$registerMap$0);
   }

   private static void lambda$registerMap$0(PacketWrapper var0) throws Exception {
      afz.a();
      boolean var2 = ((Boolean)var0.read(Type.BOOLEAN)).booleanValue();
      boolean var3 = ((Boolean)var0.read(Type.BOOLEAN)).booleanValue();
      short var4 = 0;
      if(var2) {
         ++var4;
      }

      if(var3) {
         var4 = (short)(var4 + 2);
      }

      var0.write(Type.UNSIGNED_BYTE, Short.valueOf(var4));
      if(var3) {
         EntityTracker var5 = (EntityTracker)var0.user().b(EntityTracker.class);
         if(var5.getSpectating() != var5.getPlayerId()) {
            PacketWrapper var6 = new PacketWrapper(11, (ByteBuf)null, var0.user());
            var6.write(Type.VAR_INT, Integer.valueOf(var5.getPlayerId()));
            var6.write(Type.VAR_INT, Integer.valueOf(0));
            var6.write(Type.VAR_INT, Integer.valueOf(0));
            PacketWrapper var7 = new PacketWrapper(11, (ByteBuf)null, var0.user());
            var7.write(Type.VAR_INT, Integer.valueOf(var5.getPlayerId()));
            var7.write(Type.VAR_INT, Integer.valueOf(1));
            var7.write(Type.VAR_INT, Integer.valueOf(0));
            PacketUtil.sendToServer(var6, aRi.class);
         }
      }

   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
