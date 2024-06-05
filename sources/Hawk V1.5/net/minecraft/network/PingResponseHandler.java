package net.minecraft.network;

import com.google.common.base.Charsets;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import java.net.InetSocketAddress;
import net.minecraft.server.MinecraftServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PingResponseHandler extends ChannelInboundHandlerAdapter {
   private static final String __OBFID = "CL_00001444";
   private NetworkSystem networkSystem;
   private static final Logger logger = LogManager.getLogger();

   public PingResponseHandler(NetworkSystem var1) {
      this.networkSystem = var1;
   }

   private void writeAndFlush(ChannelHandlerContext var1, ByteBuf var2) {
      var1.pipeline().firstContext().writeAndFlush(var2).addListener(ChannelFutureListener.CLOSE);
   }

   public void channelRead(ChannelHandlerContext var1, Object var2) {
      ByteBuf var3 = (ByteBuf)var2;
      var3.markReaderIndex();
      boolean var4 = true;

      try {
         if (var3.readUnsignedByte() == 254) {
            InetSocketAddress var5 = (InetSocketAddress)var1.channel().remoteAddress();
            MinecraftServer var6 = this.networkSystem.getServer();
            int var7 = var3.readableBytes();
            String var8;
            switch(var7) {
            case 0:
               logger.debug("Ping: (<1.3.x) from {}:{}", new Object[]{var5.getAddress(), var5.getPort()});
               var8 = String.format("%s§%d§%d", var6.getMOTD(), var6.getCurrentPlayerCount(), var6.getMaxPlayers());
               this.writeAndFlush(var1, this.getStringBuffer(var8));
               break;
            case 1:
               if (var3.readUnsignedByte() != 1) {
                  return;
               }

               logger.debug("Ping: (1.4-1.5.x) from {}:{}", new Object[]{var5.getAddress(), var5.getPort()});
               var8 = String.format("§1\u0000%d\u0000%s\u0000%s\u0000%d\u0000%d", 127, var6.getMinecraftVersion(), var6.getMOTD(), var6.getCurrentPlayerCount(), var6.getMaxPlayers());
               this.writeAndFlush(var1, this.getStringBuffer(var8));
               break;
            default:
               boolean var9 = var3.readUnsignedByte() == 1;
               var9 &= var3.readUnsignedByte() == 250;
               var9 &= "MC|PingHost".equals(new String(var3.readBytes(var3.readShort() * 2).array(), Charsets.UTF_16BE));
               int var10 = var3.readUnsignedShort();
               var9 &= var3.readUnsignedByte() >= 73;
               var9 &= 3 + var3.readBytes(var3.readShort() * 2).array().length + 4 == var10;
               var9 &= var3.readInt() <= 65535;
               var9 &= var3.readableBytes() == 0;
               if (!var9) {
                  return;
               }

               logger.debug("Ping: (1.6) from {}:{}", new Object[]{var5.getAddress(), var5.getPort()});
               String var11 = String.format("§1\u0000%d\u0000%s\u0000%s\u0000%d\u0000%d", 127, var6.getMinecraftVersion(), var6.getMOTD(), var6.getCurrentPlayerCount(), var6.getMaxPlayers());
               ByteBuf var12 = this.getStringBuffer(var11);

               try {
                  this.writeAndFlush(var1, var12);
               } finally {
                  var12.release();
               }
            }

            var3.release();
            var4 = false;
            return;
         }
      } catch (RuntimeException var22) {
         return;
      } finally {
         if (var4) {
            var3.resetReaderIndex();
            var1.channel().pipeline().remove("legacy_query");
            var1.fireChannelRead(var2);
         }

      }

   }

   private ByteBuf getStringBuffer(String var1) {
      ByteBuf var2 = Unpooled.buffer();
      var2.writeByte(255);
      char[] var3 = var1.toCharArray();
      var2.writeShort(var3.length);
      char[] var4 = var3;
      int var5 = var3.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         char var7 = var4[var6];
         var2.writeChar(var7);
      }

      return var2;
   }
}
