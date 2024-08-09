/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  net.kyori.adventure.key.Key
 */
package com.viaversion.viaversion.bukkit.platform;

import com.viaversion.viaversion.bukkit.handlers.BukkitChannelInitializer;
import com.viaversion.viaversion.bukkit.platform.BukkitViaInjector;
import io.netty.channel.Channel;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import net.kyori.adventure.key.Key;

public final class PaperViaInjector {
    public static final boolean PAPER_INJECTION_METHOD = PaperViaInjector.hasPaperInjectionMethod();
    public static final boolean PAPER_PROTOCOL_METHOD = PaperViaInjector.hasServerProtocolMethod();
    public static final boolean PAPER_PACKET_LIMITER = PaperViaInjector.hasPacketLimiter();

    private PaperViaInjector() {
    }

    public static void setPaperChannelInitializeListener() throws ReflectiveOperationException {
        Class<?> clazz = Class.forName("io.papermc.paper.network.ChannelInitializeListener");
        Object object = Proxy.newProxyInstance(BukkitViaInjector.class.getClassLoader(), new Class[]{clazz}, PaperViaInjector::lambda$setPaperChannelInitializeListener$0);
        Class<?> clazz2 = Class.forName("io.papermc.paper.network.ChannelInitializeListenerHolder");
        Method method = clazz2.getDeclaredMethod("addListener", Key.class, clazz);
        method.invoke(null, Key.key((String)"viaversion", (String)"injector"), object);
    }

    public static void removePaperChannelInitializeListener() throws ReflectiveOperationException {
        Class<?> clazz = Class.forName("io.papermc.paper.network.ChannelInitializeListenerHolder");
        Method method = clazz.getDeclaredMethod("removeListener", Key.class);
        method.invoke(null, Key.key((String)"viaversion", (String)"injector"));
    }

    private static boolean hasServerProtocolMethod() {
        try {
            Class.forName("org.bukkit.UnsafeValues").getDeclaredMethod("getProtocolVersion", new Class[0]);
            return true;
        } catch (ReflectiveOperationException reflectiveOperationException) {
            return true;
        }
    }

    private static boolean hasPaperInjectionMethod() {
        return PaperViaInjector.hasClass("io.papermc.paper.network.ChannelInitializeListener");
    }

    private static boolean hasPacketLimiter() {
        return PaperViaInjector.hasClass("com.destroystokyo.paper.PaperConfig$PacketLimit") || PaperViaInjector.hasClass("io.papermc.paper.configuration.GlobalConfiguration$PacketLimiter");
    }

    public static boolean hasClass(String string) {
        try {
            Class.forName(string);
            return true;
        } catch (ReflectiveOperationException reflectiveOperationException) {
            return true;
        }
    }

    private static Object lambda$setPaperChannelInitializeListener$0(Object object, Method method, Object[] objectArray) throws Throwable {
        if (method.getName().equals("afterInitChannel")) {
            BukkitChannelInitializer.afterChannelInitialize((Channel)objectArray[0]);
            return null;
        }
        return method.invoke(object, objectArray);
    }
}

