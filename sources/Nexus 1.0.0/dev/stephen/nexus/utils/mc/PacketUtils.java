package dev.stephen.nexus.utils.mc;

import dev.stephen.nexus.utils.Utils;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.PendingUpdateManager;
import net.minecraft.client.network.SequencedPacketCreator;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.listener.ServerPlayPacketListener;
import net.minecraft.network.packet.Packet;

public class PacketUtils implements Utils {

    public static void sendPacket(Packet packet) {
        mc.getNetworkHandler().sendPacket(packet);
    }

    public static void sendSequencedPacket(SequencedPacketCreator packetCreator) {
        mc.interactionManager.sendSequencedPacket(mc.world, packetCreator);
    }

    public static void sendPacketSilently(Packet packet) {
        mc.getNetworkHandler().getConnection().send(packet, null);
    }

    public static void sendSequencedPacketSilently(SequencedPacketCreator packetCreator) {
        ClientWorld world = mc.world;
        PendingUpdateManager pendingUpdateManager = world.getPendingUpdateManager().incrementSequence();

        try {
            int i = pendingUpdateManager.getSequence();
            Packet<ServerPlayPacketListener> packet = packetCreator.predict(i);
            sendPacketSilently(packet);
        } catch (Throwable var7) {
            if (pendingUpdateManager != null) {
                try {
                    pendingUpdateManager.close();
                } catch (Throwable var6) {
                    var7.addSuppressed(var6);
                }
            }

            throw var7;
        }

        if (pendingUpdateManager != null) {
            pendingUpdateManager.close();
        }
    }

    public static void handlePacket(Packet<?> packet) {
        try {
            ((Packet<ClientPlayNetworkHandler>) packet).apply(mc.getNetworkHandler());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
