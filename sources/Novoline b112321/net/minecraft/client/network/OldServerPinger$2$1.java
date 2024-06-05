package net.minecraft.client.network;

import com.google.common.base.Charsets;
import com.google.common.collect.Iterables;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.aGr;
import net.minecraft.client.network.OldServerPinger;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;

class OldServerPinger$2$1 extends SimpleChannelInboundHandler {
   final aGr a;

   OldServerPinger$2$1(aGr var1) {
      this.a = var1;
   }

   public void channelActive(ChannelHandlerContext var1) throws Exception {
      super.channelActive(var1);
      ByteBuf var2 = Unpooled.buffer();
      ByteBuf var10000 = var2;
      short var10001 = 254;

      try {
         var10000.writeByte(var10001);
         var2.writeByte(1);
         var2.writeByte(250);
         char[] var3 = "MC|PingHost".toCharArray();
         var2.writeShort(var3.length);

         for(char var7 : var3) {
            var2.writeChar(var7);
         }

         var2.writeShort(7 + 2 * this.a.b.getIP().length());
         var2.writeByte(127);
         var3 = this.a.b.getIP().toCharArray();
         var2.writeShort(var3.length);

         for(char var15 : var3) {
            var2.writeChar(var15);
         }

         var2.writeInt(this.a.b.getPort());
         var1.channel().writeAndFlush(var2).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
      } finally {
         var2.release();
      }

   }

   protected void channelRead0(ChannelHandlerContext var1, ByteBuf var2) throws Exception {
      short var3 = var2.readUnsignedByte();
      if(var3 == 255) {
         String var4 = new String(var2.readBytes(var2.readShort() * 2).array(), Charsets.UTF_16BE);
         String[] var5 = (String[])Iterables.toArray(OldServerPinger.access$200().split(var4), String.class);
         if("§1".equals(var5[0])) {
            int var6 = MathHelper.parseIntWithDefault(var5[1], 0);
            String var7 = var5[2];
            String var8 = var5[3];
            int var9 = MathHelper.parseIntWithDefault(var5[4], -1);
            int var10 = MathHelper.parseIntWithDefault(var5[5], -1);
            this.a.c.version = -1;
            this.a.c.gameVersion = var7;
            this.a.c.serverMOTD = var8;
            this.a.c.h = EnumChatFormatting.GRAY + "" + var9 + "" + EnumChatFormatting.DARK_GRAY + "/" + EnumChatFormatting.GRAY + var10;
         }
      }

      var1.close();
   }

   public void exceptionCaught(ChannelHandlerContext var1, Throwable var2) throws Exception {
      var1.close();
   }
}
