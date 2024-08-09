/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import net.minecraft.network.NetworkSystem;
import net.minecraft.server.MinecraftServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LegacyPingHandler
extends ChannelInboundHandlerAdapter {
    private static final Logger LOGGER = LogManager.getLogger();
    private final NetworkSystem networkSystem;

    public LegacyPingHandler(NetworkSystem networkSystem) {
        this.networkSystem = networkSystem;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object object) throws Exception {
        ByteBuf byteBuf = (ByteBuf)object;
        byteBuf.markReaderIndex();
        boolean bl = true;
        try {
            if (byteBuf.readUnsignedByte() == 254) {
                InetSocketAddress inetSocketAddress = (InetSocketAddress)channelHandlerContext.channel().remoteAddress();
                MinecraftServer minecraftServer = this.networkSystem.getServer();
                int n = byteBuf.readableBytes();
                switch (n) {
                    case 0: {
                        LOGGER.debug("Ping: (<1.3.x) from {}:{}", (Object)inetSocketAddress.getAddress(), (Object)inetSocketAddress.getPort());
                        String string = String.format("%s\u00a7%d\u00a7%d", minecraftServer.getMOTD(), minecraftServer.getCurrentPlayerCount(), minecraftServer.getMaxPlayers());
                        this.writeAndFlush(channelHandlerContext, this.getStringBuffer(string));
                        break;
                    }
                    case 1: {
                        if (byteBuf.readUnsignedByte() != 1) {
                            return;
                        }
                        LOGGER.debug("Ping: (1.4-1.5.x) from {}:{}", (Object)inetSocketAddress.getAddress(), (Object)inetSocketAddress.getPort());
                        String string = String.format("\u00a71\u0000%d\u0000%s\u0000%s\u0000%d\u0000%d", 127, minecraftServer.getMinecraftVersion(), minecraftServer.getMOTD(), minecraftServer.getCurrentPlayerCount(), minecraftServer.getMaxPlayers());
                        this.writeAndFlush(channelHandlerContext, this.getStringBuffer(string));
                        break;
                    }
                    default: {
                        boolean bl2 = byteBuf.readUnsignedByte() == 1;
                        bl2 &= byteBuf.readUnsignedByte() == 250;
                        bl2 &= "MC|PingHost".equals(new String(byteBuf.readBytes(byteBuf.readShort() * 2).array(), StandardCharsets.UTF_16BE));
                        int n2 = byteBuf.readUnsignedShort();
                        bl2 &= byteBuf.readUnsignedByte() >= 73;
                        bl2 &= 3 + byteBuf.readBytes(byteBuf.readShort() * 2).array().length + 4 == n2;
                        bl2 &= byteBuf.readInt() <= 65535;
                        if (!(bl2 &= byteBuf.readableBytes() == 0)) {
                            return;
                        }
                        LOGGER.debug("Ping: (1.6) from {}:{}", (Object)inetSocketAddress.getAddress(), (Object)inetSocketAddress.getPort());
                        String string = String.format("\u00a71\u0000%d\u0000%s\u0000%s\u0000%d\u0000%d", 127, minecraftServer.getMinecraftVersion(), minecraftServer.getMOTD(), minecraftServer.getCurrentPlayerCount(), minecraftServer.getMaxPlayers());
                        ByteBuf byteBuf2 = this.getStringBuffer(string);
                        try {
                            this.writeAndFlush(channelHandlerContext, byteBuf2);
                            break;
                        } finally {
                            byteBuf2.release();
                        }
                    }
                }
                byteBuf.release();
                bl = false;
                return;
            }
        } catch (RuntimeException runtimeException) {
            return;
        } finally {
            if (bl) {
                byteBuf.resetReaderIndex();
                channelHandlerContext.channel().pipeline().remove("legacy_query");
                channelHandlerContext.fireChannelRead(object);
            }
        }
    }

    private void writeAndFlush(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) {
        channelHandlerContext.pipeline().firstContext().writeAndFlush(byteBuf).addListener(ChannelFutureListener.CLOSE);
    }

    private ByteBuf getStringBuffer(String string) {
        ByteBuf byteBuf = Unpooled.buffer();
        byteBuf.writeByte(255);
        char[] cArray = string.toCharArray();
        byteBuf.writeShort(cArray.length);
        for (char c : cArray) {
            byteBuf.writeChar(c);
        }
        return byteBuf;
    }
}

