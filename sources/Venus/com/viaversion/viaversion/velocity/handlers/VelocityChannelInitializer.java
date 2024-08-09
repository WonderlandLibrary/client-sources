/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.velocity.handlers;

import com.viaversion.viaversion.connection.UserConnectionImpl;
import com.viaversion.viaversion.protocol.ProtocolPipelineImpl;
import com.viaversion.viaversion.velocity.handlers.VelocityDecodeHandler;
import com.viaversion.viaversion.velocity.handlers.VelocityEncodeHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import java.lang.reflect.Method;

public class VelocityChannelInitializer
extends ChannelInitializer<Channel> {
    public static final String MINECRAFT_ENCODER = "minecraft-encoder";
    public static final String MINECRAFT_DECODER = "minecraft-decoder";
    public static final String VIA_ENCODER = "via-encoder";
    public static final String VIA_DECODER = "via-decoder";
    public static final Object COMPRESSION_ENABLED_EVENT;
    private static final Method INIT_CHANNEL;
    private final ChannelInitializer<?> original;
    private final boolean clientSide;

    public VelocityChannelInitializer(ChannelInitializer<?> channelInitializer, boolean bl) {
        this.original = channelInitializer;
        this.clientSide = bl;
    }

    @Override
    protected void initChannel(Channel channel) throws Exception {
        INIT_CHANNEL.invoke(this.original, channel);
        UserConnectionImpl userConnectionImpl = new UserConnectionImpl(channel, this.clientSide);
        new ProtocolPipelineImpl(userConnectionImpl);
        channel.pipeline().addBefore(MINECRAFT_ENCODER, VIA_ENCODER, new VelocityEncodeHandler(userConnectionImpl));
        channel.pipeline().addBefore(MINECRAFT_DECODER, VIA_DECODER, new VelocityDecodeHandler(userConnectionImpl));
    }

    static {
        try {
            INIT_CHANNEL = ChannelInitializer.class.getDeclaredMethod("initChannel", Channel.class);
            INIT_CHANNEL.setAccessible(false);
            Class<?> clazz = Class.forName("com.velocitypowered.proxy.protocol.VelocityConnectionEvent");
            COMPRESSION_ENABLED_EVENT = clazz.getDeclaredField("COMPRESSION_ENABLED").get(null);
        } catch (ReflectiveOperationException reflectiveOperationException) {
            throw new RuntimeException(reflectiveOperationException);
        }
    }
}

