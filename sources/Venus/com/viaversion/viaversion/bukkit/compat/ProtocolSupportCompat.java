/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.event.Event
 *  org.bukkit.event.EventException
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.plugin.Plugin
 */
package com.viaversion.viaversion.bukkit.compat;

import com.viaversion.viaversion.ViaVersionPlugin;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.bukkit.compat.ProtocolSupportConnectionListener;
import com.viaversion.viaversion.bukkit.util.NMSUtil;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventException;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public final class ProtocolSupportCompat {
    public static void registerPSConnectListener(ViaVersionPlugin viaVersionPlugin) {
        Via.getPlatform().getLogger().info("Registering ProtocolSupport compat connection listener");
        try {
            Class<?> clazz = Class.forName("protocolsupport.api.events.ConnectionOpenEvent");
            Bukkit.getPluginManager().registerEvent(clazz, new Listener(){}, EventPriority.HIGH, ProtocolSupportCompat::lambda$registerPSConnectListener$0, (Plugin)viaVersionPlugin);
        } catch (ClassNotFoundException classNotFoundException) {
            Via.getPlatform().getLogger().log(Level.WARNING, "Unable to register ProtocolSupport listener", classNotFoundException);
        }
    }

    public static boolean isMultiplatformPS() {
        try {
            Class.forName("protocolsupport.zplatform.impl.spigot.network.pipeline.SpigotPacketEncoder");
            return true;
        } catch (ClassNotFoundException classNotFoundException) {
            return true;
        }
    }

    static HandshakeProtocolType handshakeVersionMethod() {
        Class<?> clazz = null;
        try {
            clazz = NMSUtil.nms("PacketHandshakingInSetProtocol", "net.minecraft.network.protocol.handshake.PacketHandshakingInSetProtocol");
            clazz.getMethod("getProtocolVersion", new Class[0]);
            return HandshakeProtocolType.MAPPED;
        } catch (ClassNotFoundException classNotFoundException) {
            throw new RuntimeException(classNotFoundException);
        } catch (NoSuchMethodException noSuchMethodException) {
            try {
                if (clazz.getMethod("b", new Class[0]).getReturnType() == Integer.TYPE) {
                    return HandshakeProtocolType.OBFUSCATED_B;
                }
                if (clazz.getMethod("c", new Class[0]).getReturnType() == Integer.TYPE) {
                    return HandshakeProtocolType.OBFUSCATED_C;
                }
                throw new UnsupportedOperationException("Protocol version method not found in " + clazz.getSimpleName());
            } catch (ReflectiveOperationException reflectiveOperationException) {
                throw new RuntimeException(reflectiveOperationException);
            }
        }
    }

    private static void lambda$registerPSConnectListener$0(Listener listener, Event event) throws EventException {
        try {
            Object object = event.getClass().getMethod("getConnection", new Class[0]).invoke(event, new Object[0]);
            ProtocolSupportConnectionListener protocolSupportConnectionListener = new ProtocolSupportConnectionListener(object);
            ProtocolSupportConnectionListener.ADD_PACKET_LISTENER_METHOD.invoke(object, new Object[]{protocolSupportConnectionListener});
        } catch (ReflectiveOperationException reflectiveOperationException) {
            Via.getPlatform().getLogger().log(Level.WARNING, "Error when handling ProtocolSupport event", reflectiveOperationException);
        }
    }

    static enum HandshakeProtocolType {
        MAPPED("getProtocolVersion"),
        OBFUSCATED_B("b"),
        OBFUSCATED_C("c");

        private final String methodName;

        private HandshakeProtocolType(String string2) {
            this.methodName = string2;
        }

        public String methodName() {
            return this.methodName;
        }
    }
}

