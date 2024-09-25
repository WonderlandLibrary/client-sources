/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 *  io.netty.channel.Channel
 *  io.netty.channel.ChannelFuture
 *  io.netty.channel.ChannelHandlerContext
 */
package com.github.creeper123123321.viafabric.platform;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import us.myles.ViaVersion.api.data.UserConnection;

public class VRClientSideUserConnection
extends UserConnection {
    public VRClientSideUserConnection(Channel socketChannel) {
        super(socketChannel);
    }

    @Override
    public void sendRawPacket(ByteBuf packet, boolean currentThread) {
        Runnable act = () -> {
            ChannelHandlerContext channelHandlerContext = this.getChannel().pipeline().context("via-decoder").fireChannelRead((Object)packet);
        };
        if (currentThread) {
            act.run();
        } else {
            this.getChannel().eventLoop().execute(act);
        }
    }

    @Override
    public ChannelFuture sendRawPacketFuture(ByteBuf packet) {
        this.getChannel().pipeline().context("via-decoder").fireChannelRead((Object)packet);
        return this.getChannel().newSucceededFuture();
    }

    @Override
    public void sendRawPacketToServer(ByteBuf packet, boolean currentThread) {
        if (currentThread) {
            this.getChannel().pipeline().context("via-encoder").writeAndFlush((Object)packet);
        } else {
            this.getChannel().eventLoop().submit(() -> this.getChannel().pipeline().context("via-encoder").writeAndFlush((Object)packet));
        }
    }
}

