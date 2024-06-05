package de.dietrichpaul.clientbase.feature.engine.lag;

import net.minecraft.network.ClientConnection;
import net.minecraft.network.listener.PacketListener;
import net.minecraft.network.packet.Packet;

public class DelayedPacket {

    private Packet<?> packet;
    private PacketListener listener;

    public DelayedPacket(Packet<?> packet, PacketListener listener) {
        this.packet = packet;
        this.listener = listener;
    }

    public void handle() {
        ClientConnection.handlePacket(packet, listener);
    }

}
