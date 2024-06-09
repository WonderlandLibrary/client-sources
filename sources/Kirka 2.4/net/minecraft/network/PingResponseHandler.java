/*
 * Decompiled with CFR 0.143.
 */
package net.minecraft.network;

import com.google.common.base.Charsets;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import io.netty.util.concurrent.GenericFutureListener;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.charset.Charset;
import net.minecraft.network.NetworkSystem;
import net.minecraft.server.MinecraftServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PingResponseHandler
extends ChannelInboundHandlerAdapter {
    private static final Logger logger = LogManager.getLogger();
    private NetworkSystem networkSystem;
    private static final String __OBFID = "CL_00001444";

    public PingResponseHandler(NetworkSystem networkSystemIn) {
        this.networkSystem = networkSystemIn;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public void channelRead(ChannelHandlerContext p_channelRead_1_, Object p_channelRead_2_) {
        var3 = (ByteBuf)p_channelRead_2_;
        var3.markReaderIndex();
        var4 = true;
        try {
            if (var3.readUnsignedByte() != 254) {
                return;
            }
            var5 = (InetSocketAddress)p_channelRead_1_.channel().remoteAddress();
            var6 = this.networkSystem.getServer();
            var7 = var3.readableBytes();
            switch (var7) {
                case 0: {
                    PingResponseHandler.logger.debug("Ping: (<1.3.x) from {}:{}", new Object[]{var5.getAddress(), var5.getPort()});
                    var8 = String.format("%s\u00a7%d\u00a7%d", new Object[]{var6.getMOTD(), var6.getCurrentPlayerCount(), var6.getMaxPlayers()});
                    this.writeAndFlush(p_channelRead_1_, this.getStringBuffer(var8));
                    ** break;
                }
                case 1: {
                    if (var3.readUnsignedByte() != 1) {
                        return;
                    }
                    PingResponseHandler.logger.debug("Ping: (1.4-1.5.x) from {}:{}", new Object[]{var5.getAddress(), var5.getPort()});
                    var8 = String.format("\u00a71\u0000%d\u0000%s\u0000%s\u0000%d\u0000%d", new Object[]{127, var6.getMinecraftVersion(), var6.getMOTD(), var6.getCurrentPlayerCount(), var6.getMaxPlayers()});
                    this.writeAndFlush(p_channelRead_1_, this.getStringBuffer(var8));
                    ** break;
                }
            }
            var23 = var3.readUnsignedByte() == 1;
            var23 &= var3.readUnsignedByte() == 250;
            var23 &= "MC|PingHost".equals(new String(var3.readBytes(var3.readShort() * 2).array(), Charsets.UTF_16BE));
            var9 = var3.readUnsignedShort();
            var23 &= var3.readUnsignedByte() >= 73;
            var23 &= 3 + var3.readBytes(var3.readShort() * 2).array().length + 4 == var9;
            var23 &= var3.readInt() <= 65535;
            if (!(var23 &= var3.readableBytes() == 0)) {
                return;
            }
            PingResponseHandler.logger.debug("Ping: (1.6) from {}:{}", new Object[]{var5.getAddress(), var5.getPort()});
            var10 = String.format("\u00a71\u0000%d\u0000%s\u0000%s\u0000%d\u0000%d", new Object[]{127, var6.getMinecraftVersion(), var6.getMOTD(), var6.getCurrentPlayerCount(), var6.getMaxPlayers()});
            var11 = this.getStringBuffer(var10);
            try {
                this.writeAndFlush(p_channelRead_1_, var11);
            }
            finally {
                var11.release();
            }
lbl43: // 2 sources:
            var3.release();
            var4 = false;
            return;
        }
        catch (RuntimeException var5) {
            return;
        }
        finally {
            if (var4) {
                var3.resetReaderIndex();
                p_channelRead_1_.channel().pipeline().remove("legacy_query");
                p_channelRead_1_.fireChannelRead(p_channelRead_2_);
            }
        }
    }

    private void writeAndFlush(ChannelHandlerContext ctx, ByteBuf data) {
        ctx.pipeline().firstContext().writeAndFlush((Object)data).addListener((GenericFutureListener)ChannelFutureListener.CLOSE);
    }

    private ByteBuf getStringBuffer(String string) {
        ByteBuf var2 = Unpooled.buffer();
        var2.writeByte(255);
        char[] var3 = string.toCharArray();
        var2.writeShort(var3.length);
        char[] var4 = var3;
        int var5 = var3.length;
        for (int var6 = 0; var6 < var5; ++var6) {
            char var7 = var4[var6];
            var2.writeChar((int)var7);
        }
        return var2;
    }
}

