/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.velocitypowered.api.network.ProtocolVersion
 */
package com.viaversion.viaversion.velocity.platform;

import com.viaversion.viaversion.VelocityPlugin;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.platform.ViaInjector;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.libs.fastutil.ints.IntLinkedOpenHashSet;
import com.viaversion.viaversion.libs.fastutil.ints.IntSortedSet;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.util.ReflectionUtil;
import com.viaversion.viaversion.velocity.handlers.VelocityChannelInitializer;
import io.netty.channel.ChannelInitializer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.jetbrains.annotations.Nullable;

public class VelocityViaInjector
implements ViaInjector {
    public static final Method GET_PLAYER_INFO_FORWARDING_MODE = VelocityViaInjector.getPlayerInfoForwardingModeMethod();

    @Nullable
    private static Method getPlayerInfoForwardingModeMethod() {
        try {
            return Class.forName("com.velocitypowered.proxy.config.VelocityConfiguration").getMethod("getPlayerInfoForwardingMode", new Class[0]);
        } catch (ClassNotFoundException | NoSuchMethodException reflectiveOperationException) {
            reflectiveOperationException.printStackTrace();
            return null;
        }
    }

    private ChannelInitializer getInitializer() throws Exception {
        Object object = ReflectionUtil.get(VelocityPlugin.PROXY, "cm", Object.class);
        Object object2 = ReflectionUtil.invoke(object, "getServerChannelInitializer");
        return (ChannelInitializer)ReflectionUtil.invoke(object2, "get");
    }

    private ChannelInitializer getBackendInitializer() throws Exception {
        Object object = ReflectionUtil.get(VelocityPlugin.PROXY, "cm", Object.class);
        Object object2 = ReflectionUtil.invoke(object, "getBackendChannelInitializer");
        return (ChannelInitializer)ReflectionUtil.invoke(object2, "get");
    }

    @Override
    public void inject() throws Exception {
        Via.getPlatform().getLogger().info("Replacing channel initializers; you can safely ignore the following two warnings.");
        Object object = ReflectionUtil.get(VelocityPlugin.PROXY, "cm", Object.class);
        Object object2 = ReflectionUtil.invoke(object, "getServerChannelInitializer");
        ChannelInitializer channelInitializer = this.getInitializer();
        object2.getClass().getMethod("set", ChannelInitializer.class).invoke(object2, new VelocityChannelInitializer(channelInitializer, false));
        Object object3 = ReflectionUtil.invoke(object, "getBackendChannelInitializer");
        ChannelInitializer channelInitializer2 = this.getBackendInitializer();
        object3.getClass().getMethod("set", ChannelInitializer.class).invoke(object3, new VelocityChannelInitializer(channelInitializer2, true));
    }

    @Override
    public void uninject() {
        Via.getPlatform().getLogger().severe("ViaVersion cannot remove itself from Velocity without a reboot!");
    }

    @Override
    public int getServerProtocolVersion() throws Exception {
        return VelocityViaInjector.getLowestSupportedProtocolVersion();
    }

    @Override
    public IntSortedSet getServerProtocolVersions() throws Exception {
        int n = VelocityViaInjector.getLowestSupportedProtocolVersion();
        IntLinkedOpenHashSet intLinkedOpenHashSet = new IntLinkedOpenHashSet();
        for (com.velocitypowered.api.network.ProtocolVersion protocolVersion : com.velocitypowered.api.network.ProtocolVersion.SUPPORTED_VERSIONS) {
            if (protocolVersion.getProtocol() < n) continue;
            intLinkedOpenHashSet.add(protocolVersion.getProtocol());
        }
        return intLinkedOpenHashSet;
    }

    public static int getLowestSupportedProtocolVersion() {
        try {
            if (GET_PLAYER_INFO_FORWARDING_MODE != null && ((Enum)GET_PLAYER_INFO_FORWARDING_MODE.invoke(VelocityPlugin.PROXY.getConfiguration(), new Object[0])).name().equals("MODERN")) {
                return ProtocolVersion.v1_13.getVersion();
            }
        } catch (IllegalAccessException | InvocationTargetException reflectiveOperationException) {
            // empty catch block
        }
        return com.velocitypowered.api.network.ProtocolVersion.MINIMUM_VERSION.getProtocol();
    }

    @Override
    public JsonObject getDump() {
        JsonObject jsonObject = new JsonObject();
        try {
            jsonObject.addProperty("currentInitializer", this.getInitializer().getClass().getName());
        } catch (Exception exception) {
            // empty catch block
        }
        return jsonObject;
    }
}

