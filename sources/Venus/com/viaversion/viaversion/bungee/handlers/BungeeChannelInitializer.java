/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.bungee.handlers;

import com.viaversion.viaversion.bungee.handlers.BungeeDecodeHandler;
import com.viaversion.viaversion.bungee.handlers.BungeeEncodeHandler;
import com.viaversion.viaversion.connection.UserConnectionImpl;
import com.viaversion.viaversion.protocol.ProtocolPipelineImpl;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import java.lang.reflect.Method;

public class BungeeChannelInitializer
extends ChannelInitializer<Channel> {
    private final ChannelInitializer<Channel> original;
    private Method method;

    public BungeeChannelInitializer(ChannelInitializer<Channel> channelInitializer) {
        this.original = channelInitializer;
        try {
            this.method = ChannelInitializer.class.getDeclaredMethod("initChannel", Channel.class);
            this.method.setAccessible(false);
        } catch (NoSuchMethodException noSuchMethodException) {
            noSuchMethodException.printStackTrace();
        }
    }

    @Override
    protected void initChannel(Channel channel) throws Exception {
        if (!channel.isActive()) {
            return;
        }
        UserConnectionImpl userConnectionImpl = new UserConnectionImpl(channel);
        new ProtocolPipelineImpl(userConnectionImpl);
        this.method.invoke(this.original, channel);
        if (!channel.isActive()) {
            return;
        }
        if (channel.pipeline().get("packet-encoder") == null) {
            return;
        }
        if (channel.pipeline().get("packet-decoder") == null) {
            return;
        }
        BungeeEncodeHandler bungeeEncodeHandler = new BungeeEncodeHandler(userConnectionImpl);
        BungeeDecodeHandler bungeeDecodeHandler = new BungeeDecodeHandler(userConnectionImpl);
        channel.pipeline().addBefore("packet-encoder", "via-encoder", bungeeEncodeHandler);
        channel.pipeline().addBefore("packet-decoder", "via-decoder", bungeeDecodeHandler);
    }

    public ChannelInitializer<Channel> getOriginal() {
        return this.original;
    }
}

