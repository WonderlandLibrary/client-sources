package net;

import io.netty.buffer.ByteBuf;
import net.S3;
import net.aRE;
import net.acE;
import viaversion.viarewind.utils.PacketUtil;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.minecraft.Position;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

final class a9x extends acE {
   public void registerMap() {
      this.a(Type.STRING);
      this.a(a9x::lambda$registerMap$0);
   }

   private static void lambda$registerMap$0(PacketWrapper var0) throws Exception {
      S3.b();
      String var2 = (String)var0.get(Type.STRING, 0);
      if(var2.toLowerCase().startsWith("/offhand")) {
         var0.cancel();
         PacketWrapper var3 = new PacketWrapper(19, (ByteBuf)null, var0.user());
         var3.write(Type.VAR_INT, Integer.valueOf(6));
         var3.write(Type.POSITION, new Position(0, (short)0, 0));
         var3.write(Type.BYTE, Byte.valueOf((byte)-1));
         PacketUtil.sendToServer(var3, aRE.class, true, true);
      }

   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
