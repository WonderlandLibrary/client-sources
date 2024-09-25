/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  io.netty.channel.Channel
 *  io.netty.channel.ChannelHandler
 *  io.netty.channel.ChannelInitializer
 */
package us.myles.ViaVersion.bungee.handlers;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import java.lang.reflect.Method;
import us.myles.ViaVersion.api.data.UserConnection;
import us.myles.ViaVersion.api.protocol.ProtocolPipeline;
import us.myles.ViaVersion.bungee.handlers.BungeeDecodeHandler;
import us.myles.ViaVersion.bungee.handlers.BungeeEncodeHandler;

public class BungeeChannelInitializer
extends ChannelInitializer<Channel> {
    private final ChannelInitializer<Channel> original;
    private Method method;

    public BungeeChannelInitializer(ChannelInitializer<Channel> oldInit) {
        this.original = oldInit;
        try {
            this.method = ChannelInitializer.class.getDeclaredMethod("initChannel", Channel.class);
            this.method.setAccessible(true);
        }
        catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    protected void initChannel(Channel socketChannel) throws Exception {
        UserConnection info = new UserConnection(socketChannel);
        new ProtocolPipeline(info);
        this.method.invoke(this.original, new Object[]{socketChannel});
        if (socketChannel.pipeline().get("packet-encoder") == null) {
            return;
        }
        if (socketChannel.pipeline().get("packet-decoder") == null) {
            return;
        }
        BungeeEncodeHandler encoder = new BungeeEncodeHandler(info);
        BungeeDecodeHandler decoder = new BungeeDecodeHandler(info);
        socketChannel.pipeline().addBefore("packet-encoder", "via-encoder", (ChannelHandler)encoder);
        socketChannel.pipeline().addBefore("packet-decoder", "via-decoder", (ChannelHandler)decoder);
    }

    public ChannelInitializer<Channel> getOriginal() {
        return this.original;
    }
}

