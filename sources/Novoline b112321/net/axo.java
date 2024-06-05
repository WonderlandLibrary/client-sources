package net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import net.a7T;
import net.aRY;
import net.md_5.bungee.api.ChatColor;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.Via;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.protocols.base.BaseProtocol;
import viaversion.viaversion.protocols.base.ProtocolInfo;

class axo implements PacketHandler {
   final a7T a;

   axo(a7T var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      ProtocolInfo.d();
      int var3 = var1.user().getProtocolInfo().getProtocolVersion();
      if(Via.getConfig().getBlockedProtocols().contains(var3)) {
         if(!var1.user().getChannel().isOpen()) {
            return;
         }

         if(!var1.user().o()) {
            return;
         }

         PacketWrapper var4 = new PacketWrapper(0, (ByteBuf)null, var1.user());
         aRY.i.write(var4, ChatColor.translateAlternateColorCodes('&', Via.getConfig().getBlockedDisconnectMsg()));
         var1.cancel();
         ChannelFuture var5 = var4.sendFuture(BaseProtocol.class);
         var5.addListener(axo::lambda$handle$0);
      }

   }

   private static void lambda$handle$0(PacketWrapper var0, Future var1) throws Exception {
      var0.user().getChannel().close();
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
