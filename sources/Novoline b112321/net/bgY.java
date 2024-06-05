package net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import net.acE;
import viaversion.viaversion.api.data.UserConnection;

public class bgY extends UserConnection {
   private static acE[] r;

   public bgY(Channel var1) {
      super(var1);
   }

   public void sendRawPacket(ByteBuf var1, boolean var2) {
      b();
      Runnable var4 = this::lambda$sendRawPacket$0;
      var4.run();
      this.getChannel().eventLoop().execute(var4);
   }

   public ChannelFuture sendRawPacketFuture(ByteBuf var1) {
      this.getChannel().pipeline().context("via-decoder").fireChannelRead(var1);
      return this.getChannel().newSucceededFuture();
   }

   public void sendRawPacketToServer(ByteBuf var1, boolean var2) {
      acE[] var3 = b();
      this.getChannel().pipeline().context("via-encoder").writeAndFlush(var1);
      this.getChannel().eventLoop().submit(this::lambda$sendRawPacketToServer$1);
   }

   private void lambda$sendRawPacketToServer$1(ByteBuf var1) {
      this.getChannel().pipeline().context("via-encoder").writeAndFlush(var1);
   }

   private void lambda$sendRawPacket$0(ByteBuf var1) {
      this.getChannel().pipeline().context("via-decoder").fireChannelRead(var1);
   }

   public static void b(acE[] var0) {
      r = var0;
   }

   public static acE[] b() {
      return r;
   }

   static {
      b(new acE[1]);
   }
}
