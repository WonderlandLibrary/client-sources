/*
 * Decompiled with CFR 0.152.
 */
package de.gerrygames.viarewind.utils;

import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.exception.CancelException;

public class PacketUtil {
    public static void sendToServer(PacketWrapper packet, Class<? extends Protocol> packetProtocol) {
        PacketUtil.sendToServer(packet, packetProtocol, true);
    }

    public static void sendToServer(PacketWrapper packet, Class<? extends Protocol> packetProtocol, boolean skipCurrentPipeline) {
        PacketUtil.sendToServer(packet, packetProtocol, skipCurrentPipeline, false);
    }

    public static void sendToServer(PacketWrapper packet, Class<? extends Protocol> packetProtocol, boolean skipCurrentPipeline, boolean currentThread) {
        try {
            if (currentThread) {
                packet.sendToServer(packetProtocol, skipCurrentPipeline);
                return;
            }
            packet.scheduleSendToServer(packetProtocol, skipCurrentPipeline);
            return;
        }
        catch (CancelException cancelException) {
            return;
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static boolean sendPacket(PacketWrapper packet, Class<? extends Protocol> packetProtocol) {
        return PacketUtil.sendPacket(packet, packetProtocol, true);
    }

    public static boolean sendPacket(PacketWrapper packet, Class<? extends Protocol> packetProtocol, boolean skipCurrentPipeline) {
        return PacketUtil.sendPacket(packet, packetProtocol, true, false);
    }

    public static boolean sendPacket(PacketWrapper packet, Class<? extends Protocol> packetProtocol, boolean skipCurrentPipeline, boolean currentThread) {
        try {
            if (currentThread) {
                packet.send(packetProtocol, skipCurrentPipeline);
                return true;
            }
            packet.scheduleSend(packetProtocol, skipCurrentPipeline);
            return true;
        }
        catch (CancelException cancelException) {
            return false;
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }
}

