/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.bukkit.handlers;

import com.viaversion.viaversion.bukkit.handlers.BukkitDecodeHandler;
import com.viaversion.viaversion.bukkit.handlers.BukkitEncodeHandler;
import com.viaversion.viaversion.bukkit.platform.PaperViaInjector;
import com.viaversion.viaversion.connection.UserConnectionImpl;
import com.viaversion.viaversion.platform.WrappedChannelInitializer;
import com.viaversion.viaversion.protocol.ProtocolPipelineImpl;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import java.lang.reflect.Method;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class BukkitChannelInitializer
extends ChannelInitializer<Channel>
implements WrappedChannelInitializer {
    public static final String VIA_ENCODER = "via-encoder";
    public static final String VIA_DECODER = "via-decoder";
    public static final String MINECRAFT_ENCODER = "encoder";
    public static final String MINECRAFT_DECODER = "decoder";
    public static final String MINECRAFT_COMPRESSOR = "compress";
    public static final String MINECRAFT_DECOMPRESSOR = "decompress";
    public static final Object COMPRESSION_ENABLED_EVENT = BukkitChannelInitializer.paperCompressionEnabledEvent();
    private static final Method INIT_CHANNEL_METHOD;
    private final ChannelInitializer<Channel> original;

    private static @Nullable Object paperCompressionEnabledEvent() {
        try {
            Class<?> clazz = Class.forName("io.papermc.paper.network.ConnectionEvent");
            return clazz.getDeclaredField("COMPRESSION_THRESHOLD_SET").get(null);
        } catch (ReflectiveOperationException reflectiveOperationException) {
            return null;
        }
    }

    public BukkitChannelInitializer(ChannelInitializer<Channel> channelInitializer) {
        this.original = channelInitializer;
    }

    @Deprecated
    public ChannelInitializer<Channel> getOriginal() {
        return this.original;
    }

    @Override
    protected void initChannel(Channel channel) throws Exception {
        INIT_CHANNEL_METHOD.invoke(this.original, channel);
        BukkitChannelInitializer.afterChannelInitialize(channel);
    }

    public static void afterChannelInitialize(Channel channel) {
        UserConnectionImpl userConnectionImpl = new UserConnectionImpl(channel);
        new ProtocolPipelineImpl(userConnectionImpl);
        if (PaperViaInjector.PAPER_PACKET_LIMITER) {
            userConnectionImpl.setPacketLimiterEnabled(false);
        }
        ChannelPipeline channelPipeline = channel.pipeline();
        channelPipeline.addBefore(MINECRAFT_ENCODER, VIA_ENCODER, new BukkitEncodeHandler(userConnectionImpl));
        channelPipeline.addBefore(MINECRAFT_DECODER, VIA_DECODER, new BukkitDecodeHandler(userConnectionImpl));
    }

    @Override
    public ChannelInitializer<Channel> original() {
        return this.original;
    }

    static {
        try {
            INIT_CHANNEL_METHOD = ChannelInitializer.class.getDeclaredMethod("initChannel", Channel.class);
            INIT_CHANNEL_METHOD.setAccessible(false);
        } catch (ReflectiveOperationException reflectiveOperationException) {
            throw new RuntimeException(reflectiveOperationException);
        }
    }
}

