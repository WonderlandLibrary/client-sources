package net.minecraft.client.network;

import net.minecraft.client.multiplayer.*;
import io.netty.channel.*;
import io.netty.util.concurrent.*;
import io.netty.buffer.*;
import com.google.common.base.*;
import com.google.common.collect.*;
import net.minecraft.util.*;

class OldServerPinger$2 extends ChannelInitializer {
    final /* synthetic */ ServerAddress val$var2;
    final /* synthetic */ ServerData val$server;
    
    protected void initChannel(final Channel p_initChannel_1_) {
        try {
            p_initChannel_1_.config().setOption(ChannelOption.IP_TOS, (Object)24);
        }
        catch (ChannelException ex) {}
        try {
            p_initChannel_1_.config().setOption(ChannelOption.TCP_NODELAY, (Object)false);
        }
        catch (ChannelException ex2) {}
        p_initChannel_1_.pipeline().addLast(new ChannelHandler[] { (ChannelHandler)new SimpleChannelInboundHandler() {
                public void channelActive(final ChannelHandlerContext p_channelActive_1_) throws Exception {
                    super.channelActive(p_channelActive_1_);
                    final ByteBuf var2x = Unpooled.buffer();
                    try {
                        var2x.writeByte(254);
                        var2x.writeByte(1);
                        var2x.writeByte(250);
                        char[] var3 = "MC|PingHost".toCharArray();
                        var2x.writeShort(var3.length);
                        char[] var4 = var3;
                        for (int var5 = var3.length, var6 = 0; var6 < var5; ++var6) {
                            final char var7 = var4[var6];
                            var2x.writeChar((int)var7);
                        }
                        var2x.writeShort(7 + 2 * ChannelInitializer.this.val$var2.getIP().length());
                        var2x.writeByte(127);
                        var3 = ChannelInitializer.this.val$var2.getIP().toCharArray();
                        var2x.writeShort(var3.length);
                        var4 = var3;
                        for (int var5 = var3.length, var6 = 0; var6 < var5; ++var6) {
                            final char var7 = var4[var6];
                            var2x.writeChar((int)var7);
                        }
                        var2x.writeInt(ChannelInitializer.this.val$var2.getPort());
                        p_channelActive_1_.channel().writeAndFlush((Object)var2x).addListener((GenericFutureListener)ChannelFutureListener.CLOSE_ON_FAILURE);
                    }
                    finally {
                        var2x.release();
                    }
                }
                
                protected void channelRead0(final ChannelHandlerContext p_channelRead0_1_, final ByteBuf p_channelRead0_2_) {
                    final short var3 = p_channelRead0_2_.readUnsignedByte();
                    if (var3 == 255) {
                        final String var4 = new String(p_channelRead0_2_.readBytes(p_channelRead0_2_.readShort() * 2).array(), Charsets.UTF_16BE);
                        final String[] var5 = (String[])Iterables.toArray(OldServerPinger.access$200().split((CharSequence)var4), (Class)String.class);
                        if ("§1".equals(var5[0])) {
                            final int var6 = MathHelper.parseIntWithDefault(var5[1], 0);
                            final String var7 = var5[2];
                            final String var8 = var5[3];
                            final int var9 = MathHelper.parseIntWithDefault(var5[4], -1);
                            final int var10 = MathHelper.parseIntWithDefault(var5[5], -1);
                            ChannelInitializer.this.val$server.version = -1;
                            ChannelInitializer.this.val$server.gameVersion = var7;
                            ChannelInitializer.this.val$server.serverMOTD = var8;
                            ChannelInitializer.this.val$server.populationInfo = EnumChatFormatting.GRAY + "" + var9 + "" + EnumChatFormatting.DARK_GRAY + "/" + EnumChatFormatting.GRAY + var10;
                        }
                    }
                    p_channelRead0_1_.close();
                }
                
                public void exceptionCaught(final ChannelHandlerContext p_exceptionCaught_1_, final Throwable p_exceptionCaught_2_) {
                    p_exceptionCaught_1_.close();
                }
                
                protected void channelRead0(final ChannelHandlerContext p_channelRead0_1_, final Object p_channelRead0_2_) {
                    this.channelRead0(p_channelRead0_1_, (ByteBuf)p_channelRead0_2_);
                }
            } });
    }
}