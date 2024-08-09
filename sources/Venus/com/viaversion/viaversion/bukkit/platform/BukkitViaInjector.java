/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.plugin.PluginDescriptionFile
 */
package com.viaversion.viaversion.bukkit.platform;

import com.google.common.base.Preconditions;
import com.viaversion.viaversion.bukkit.handlers.BukkitChannelInitializer;
import com.viaversion.viaversion.bukkit.platform.PaperViaInjector;
import com.viaversion.viaversion.bukkit.util.NMSUtil;
import com.viaversion.viaversion.platform.LegacyViaInjector;
import com.viaversion.viaversion.platform.WrappedChannelInitializer;
import com.viaversion.viaversion.util.ReflectionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.checkerframework.checker.nullness.qual.Nullable;

public class BukkitViaInjector
extends LegacyViaInjector {
    private static final boolean HAS_WORLD_VERSION_PROTOCOL_VERSION = PaperViaInjector.hasClass("net.minecraft.SharedConstants") && PaperViaInjector.hasClass("net.minecraft.WorldVersion") && !PaperViaInjector.hasClass("com.mojang.bridge.game.GameVersion");

    @Override
    public void inject() throws ReflectiveOperationException {
        if (PaperViaInjector.PAPER_INJECTION_METHOD) {
            PaperViaInjector.setPaperChannelInitializeListener();
            return;
        }
        super.inject();
    }

    @Override
    public void uninject() throws ReflectiveOperationException {
        if (PaperViaInjector.PAPER_INJECTION_METHOD) {
            PaperViaInjector.removePaperChannelInitializeListener();
            return;
        }
        super.uninject();
    }

    @Override
    public int getServerProtocolVersion() throws ReflectiveOperationException {
        if (PaperViaInjector.PAPER_PROTOCOL_METHOD) {
            return Bukkit.getUnsafe().getProtocolVersion();
        }
        return HAS_WORLD_VERSION_PROTOCOL_VERSION ? this.cursedProtocolDetection() : this.veryCursedProtocolDetection();
    }

    private int cursedProtocolDetection() throws ReflectiveOperationException {
        Class<?> clazz = Class.forName("net.minecraft.SharedConstants");
        Class<?> clazz2 = Class.forName("net.minecraft.WorldVersion");
        Method method = null;
        for (Method method2 : clazz.getDeclaredMethods()) {
            if (method2.getReturnType() != clazz2 || method2.getParameterTypes().length != 0) continue;
            method = method2;
            break;
        }
        Preconditions.checkNotNull(method, "Failed to get world version method");
        Object object = method.invoke(null, new Object[0]);
        for (Method method3 : clazz2.getDeclaredMethods()) {
            if (method3.getReturnType() != Integer.TYPE || method3.getParameterTypes().length != 0) continue;
            return (Integer)method3.invoke(object, new Object[0]);
        }
        throw new IllegalAccessException("Failed to find protocol version method in WorldVersion");
    }

    private int veryCursedProtocolDetection() throws ReflectiveOperationException {
        Class<?> clazz = NMSUtil.nms("MinecraftServer", "net.minecraft.server.MinecraftServer");
        Object object = ReflectionUtil.invokeStatic(clazz, "getServer");
        Preconditions.checkNotNull(object, "Failed to get server instance");
        Class<?> clazz2 = NMSUtil.nms("ServerPing", "net.minecraft.network.protocol.status.ServerPing");
        Object object2 = null;
        for (Field field : clazz.getDeclaredFields()) {
            if (field.getType() != clazz2) continue;
            field.setAccessible(false);
            object2 = field.get(object);
            break;
        }
        Preconditions.checkNotNull(object2, "Failed to get server ping");
        Class<?> clazz3 = NMSUtil.nms("ServerPing$ServerData", "net.minecraft.network.protocol.status.ServerPing$ServerData");
        Object object3 = null;
        for (Field field : clazz2.getDeclaredFields()) {
            if (field.getType() != clazz3) continue;
            field.setAccessible(false);
            object3 = field.get(object2);
            break;
        }
        Preconditions.checkNotNull(object3, "Failed to get server data");
        for (Field field : clazz3.getDeclaredFields()) {
            if (field.getType() != Integer.TYPE) continue;
            field.setAccessible(false);
            int n = (Integer)field.get(object3);
            if (n == -1) continue;
            return n;
        }
        throw new RuntimeException("Failed to get server");
    }

    @Override
    protected @Nullable Object getServerConnection() throws ReflectiveOperationException {
        Class<?> clazz = NMSUtil.nms("MinecraftServer", "net.minecraft.server.MinecraftServer");
        Class<?> clazz2 = NMSUtil.nms("ServerConnection", "net.minecraft.server.network.ServerConnection");
        Object object = ReflectionUtil.invokeStatic(clazz, "getServer");
        for (Method method : clazz.getDeclaredMethods()) {
            Object object2;
            if (method.getReturnType() != clazz2 || method.getParameterTypes().length != 0 || (object2 = method.invoke(object, new Object[0])) == null) continue;
            return object2;
        }
        return null;
    }

    @Override
    protected WrappedChannelInitializer createChannelInitializer(ChannelInitializer<Channel> channelInitializer) {
        return new BukkitChannelInitializer(channelInitializer);
    }

    @Override
    protected void blame(ChannelHandler channelHandler) throws ReflectiveOperationException {
        ClassLoader classLoader = channelHandler.getClass().getClassLoader();
        if (classLoader.getClass().getName().equals("org.bukkit.plugin.java.PluginClassLoader")) {
            PluginDescriptionFile pluginDescriptionFile = ReflectionUtil.get(classLoader, "description", PluginDescriptionFile.class);
            throw new RuntimeException("Unable to inject, due to " + channelHandler.getClass().getName() + ", try without the plugin " + pluginDescriptionFile.getName() + "?");
        }
        throw new RuntimeException("Unable to find core component 'childHandler', please check your plugins. issue: " + channelHandler.getClass().getName());
    }

    @Override
    public boolean lateProtocolVersionSetting() {
        return !PaperViaInjector.PAPER_PROTOCOL_METHOD && !HAS_WORLD_VERSION_PROTOCOL_VERSION;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean isBinded() {
        if (PaperViaInjector.PAPER_INJECTION_METHOD) {
            return false;
        }
        try {
            Object object = this.getServerConnection();
            if (object == null) {
                return false;
            }
            for (Field field : object.getClass().getDeclaredFields()) {
                List list;
                if (!List.class.isAssignableFrom(field.getType())) continue;
                field.setAccessible(false);
                List list2 = list = (List)field.get(object);
                synchronized (list2) {
                    if (!list.isEmpty() && list.get(0) instanceof ChannelFuture) {
                        return true;
                    }
                }
            }
        } catch (ReflectiveOperationException reflectiveOperationException) {
            reflectiveOperationException.printStackTrace();
        }
        return true;
    }
}

