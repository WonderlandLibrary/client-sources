package net.minecraft.network;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import net.minecraft.network.NetworkSystem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PingResponseHandler extends ChannelInboundHandlerAdapter {
   private static final Logger LOGGER = LogManager.getLogger();
   private final NetworkSystem networkSystem;

   public PingResponseHandler(NetworkSystem var1) {
      this.networkSystem = var1;
   }

   public void channelRead(ChannelHandlerContext param1, Object param2) throws Exception {
      // $FF: Couldn't be decompiled
   }

   private void writeAndFlush(ChannelHandlerContext var1, ByteBuf var2) {
      var1.pipeline().firstContext().writeAndFlush(var2).addListener(ChannelFutureListener.CLOSE);
   }

   private ByteBuf getStringBuffer(String var1) {
      ByteBuf var2 = Unpooled.buffer();
      var2.writeByte(255);
      char[] var3 = var1.toCharArray();
      var2.writeShort(var3.length);

      for(char var7 : var3) {
         var2.writeChar(var7);
      }

      return var2;
   }

   private static RuntimeException a(RuntimeException var0) {
      return var0;
   }
}
