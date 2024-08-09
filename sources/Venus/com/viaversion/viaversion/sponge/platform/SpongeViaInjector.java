/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.spongepowered.api.MinecraftVersion
 *  org.spongepowered.api.Sponge
 */
package com.viaversion.viaversion.sponge.platform;

import com.viaversion.viaversion.platform.LegacyViaInjector;
import com.viaversion.viaversion.platform.WrappedChannelInitializer;
import com.viaversion.viaversion.sponge.handlers.SpongeChannelInitializer;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.api.MinecraftVersion;
import org.spongepowered.api.Sponge;

public class SpongeViaInjector
extends LegacyViaInjector {
    @Override
    public int getServerProtocolVersion() throws ReflectiveOperationException {
        MinecraftVersion minecraftVersion = Sponge.platform().minecraftVersion();
        try {
            return (Integer)minecraftVersion.getClass().getDeclaredMethod("getProtocol", new Class[0]).invoke(minecraftVersion, new Object[0]);
        } catch (NoSuchMethodException noSuchMethodException) {
            return (Integer)minecraftVersion.getClass().getDeclaredMethod("protocolVersion", new Class[0]).invoke(minecraftVersion, new Object[0]);
        }
    }

    @Override
    protected @Nullable Object getServerConnection() throws ReflectiveOperationException {
        Class<?> clazz = Class.forName("net.minecraft.server.MinecraftServer");
        return clazz.getDeclaredMethod("getConnection", new Class[0]).invoke(Sponge.server(), new Object[0]);
    }

    @Override
    protected WrappedChannelInitializer createChannelInitializer(ChannelInitializer<Channel> channelInitializer) {
        return new SpongeChannelInitializer(channelInitializer);
    }

    @Override
    protected void blame(ChannelHandler channelHandler) {
        throw new RuntimeException("Unable to find core component 'childHandler', please check your plugins. Issue: " + channelHandler.getClass().getName());
    }

    @Override
    public String getEncoderName() {
        return "encoder";
    }

    @Override
    public String getDecoderName() {
        return "decoder";
    }
}

