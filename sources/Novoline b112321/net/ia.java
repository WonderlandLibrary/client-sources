package net;

import io.netty.buffer.ByteBuf;
import net.aDC;
import net.aRi;
import net.afz;
import net.axZ;
import viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.EntityTracker;
import viaversion.viarewind.utils.PacketUtil;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

class ia implements PacketHandler {
   final aDC a;

   ia(aDC var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      afz.b();
      int[] var3 = (int[])var1.read(Type.VAR_INT_ARRAY_PRIMITIVE);
      EntityTracker var4 = (EntityTracker)var1.user().b(EntityTracker.class);
      int var6 = var3.length;
      int var7 = 0;
      if(var7 < var6) {
         int var8 = var3[var7];
         var4.removeEntity(var8);
         ++var7;
      }

      if(var3.length > 127) {
         int[] var5 = new int[127];
         System.arraycopy(var3, 0, var5, 0, 127);
         int[] var9 = new int[var3.length - 127];
         System.arraycopy(var3, 127, var9, 0, var9.length);
         var3 = var9;
         PacketWrapper var11 = new PacketWrapper(19, (ByteBuf)null, var1.user());
         var11.write(axZ.b, var5);
         PacketUtil.sendPacket(var11, aRi.class);
      }

      var1.write(axZ.b, var3);
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
