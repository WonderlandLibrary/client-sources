/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  protocolsupport.api.Connection$PacketListener
 *  protocolsupport.api.Connection$PacketListener$PacketEvent
 */
package com.viaversion.viaversion.bukkit.compat;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.bukkit.compat.ProtocolSupportCompat;
import com.viaversion.viaversion.bukkit.util.NMSUtil;
import java.lang.reflect.Method;
import protocolsupport.api.Connection;

final class ProtocolSupportConnectionListener
extends Connection.PacketListener {
    static final Method ADD_PACKET_LISTENER_METHOD;
    private static final Class<?> HANDSHAKE_PACKET_CLASS;
    private static final Method GET_VERSION_METHOD;
    private static final Method SET_VERSION_METHOD;
    private static final Method REMOVE_PACKET_LISTENER_METHOD;
    private static final Method GET_LATEST_METHOD;
    private static final Object PROTOCOL_VERSION_MINECRAFT_FUTURE;
    private static final Object PROTOCOL_TYPE_PC;
    private final Object connection;

    ProtocolSupportConnectionListener(Object object) {
        this.connection = object;
    }

    public void onPacketReceiving(Connection.PacketListener.PacketEvent packetEvent) {
        try {
            if (HANDSHAKE_PACKET_CLASS.isInstance(packetEvent.getPacket()) && GET_VERSION_METHOD.invoke(this.connection, new Object[0]) == PROTOCOL_VERSION_MINECRAFT_FUTURE) {
                Object object = packetEvent.getPacket();
                int n = (Integer)HANDSHAKE_PACKET_CLASS.getDeclaredMethod(ProtocolSupportCompat.handshakeVersionMethod().methodName(), new Class[0]).invoke(object, new Object[0]);
                if (n == Via.getAPI().getServerVersion().lowestSupportedVersion()) {
                    SET_VERSION_METHOD.invoke(this.connection, GET_LATEST_METHOD.invoke(null, PROTOCOL_TYPE_PC));
                }
            }
            REMOVE_PACKET_LISTENER_METHOD.invoke(this.connection, new Object[]{this});
        } catch (ReflectiveOperationException reflectiveOperationException) {
            throw new RuntimeException(reflectiveOperationException);
        }
    }

    static {
        try {
            HANDSHAKE_PACKET_CLASS = NMSUtil.nms("PacketHandshakingInSetProtocol", "net.minecraft.network.protocol.handshake.PacketHandshakingInSetProtocol");
            Class<?> clazz = Class.forName("protocolsupport.protocol.ConnectionImpl");
            Class<?> clazz2 = Class.forName("protocolsupport.api.Connection");
            Class<?> clazz3 = Class.forName("protocolsupport.api.Connection$PacketListener");
            Class<?> clazz4 = Class.forName("protocolsupport.api.ProtocolVersion");
            Class<?> clazz5 = Class.forName("protocolsupport.api.ProtocolType");
            GET_VERSION_METHOD = clazz2.getDeclaredMethod("getVersion", new Class[0]);
            SET_VERSION_METHOD = clazz.getDeclaredMethod("setVersion", clazz4);
            PROTOCOL_VERSION_MINECRAFT_FUTURE = clazz4.getDeclaredField("MINECRAFT_FUTURE").get(null);
            GET_LATEST_METHOD = clazz4.getDeclaredMethod("getLatest", clazz5);
            PROTOCOL_TYPE_PC = clazz5.getDeclaredField("PC").get(null);
            ADD_PACKET_LISTENER_METHOD = clazz2.getDeclaredMethod("addPacketListener", clazz3);
            REMOVE_PACKET_LISTENER_METHOD = clazz2.getDeclaredMethod("removePacketListener", clazz3);
        } catch (ReflectiveOperationException reflectiveOperationException) {
            throw new RuntimeException(reflectiveOperationException);
        }
    }
}

