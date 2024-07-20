/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
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
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public final class ProtocolSupportCompat {
    public static void registerPSConnectListener(ViaVersionPlugin plugin) {
        Via.getPlatform().getLogger().info("Registering ProtocolSupport compat connection listener");
        try {
            Class<?> connectionOpenEvent = Class.forName("protocolsupport.api.events.ConnectionOpenEvent");
            Bukkit.getPluginManager().registerEvent(connectionOpenEvent, new Listener(){}, EventPriority.HIGH, (listener, event) -> {
                try {
                    Object connection = event.getClass().getMethod("getConnection", new Class[0]).invoke(event, new Object[0]);
                    ProtocolSupportConnectionListener connectListener = new ProtocolSupportConnectionListener(connection);
                    ProtocolSupportConnectionListener.ADD_PACKET_LISTENER_METHOD.invoke(connection, new Object[]{connectListener});
                } catch (ReflectiveOperationException e) {
                    Via.getPlatform().getLogger().log(Level.WARNING, "Error when handling ProtocolSupport event", e);
                }
            }, (Plugin)plugin);
        } catch (ClassNotFoundException e) {
            Via.getPlatform().getLogger().log(Level.WARNING, "Unable to register ProtocolSupport listener", e);
        }
    }

    public static boolean isMultiplatformPS() {
        try {
            Class.forName("protocolsupport.zplatform.impl.spigot.network.pipeline.SpigotPacketEncoder");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    static HandshakeProtocolType handshakeVersionMethod() {
        Class<?> clazz = null;
        try {
            clazz = NMSUtil.nms("PacketHandshakingInSetProtocol", "net.minecraft.network.protocol.handshake.PacketHandshakingInSetProtocol");
            clazz.getMethod("getProtocolVersion", new Class[0]);
            return HandshakeProtocolType.MAPPED;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            try {
                if (clazz.getMethod("b", new Class[0]).getReturnType() == Integer.TYPE) {
                    return HandshakeProtocolType.OBFUSCATED_B;
                }
                if (clazz.getMethod("c", new Class[0]).getReturnType() == Integer.TYPE) {
                    return HandshakeProtocolType.OBFUSCATED_C;
                }
                throw new UnsupportedOperationException("Protocol version method not found in " + clazz.getSimpleName());
            } catch (ReflectiveOperationException e2) {
                throw new RuntimeException(e2);
            }
        }
    }

    static enum HandshakeProtocolType {
        MAPPED("getProtocolVersion"),
        OBFUSCATED_B("b"),
        OBFUSCATED_C("c");

        private final String methodName;

        private HandshakeProtocolType(String methodName) {
            this.methodName = methodName;
        }

        public String methodName() {
            return this.methodName;
        }
    }
}

