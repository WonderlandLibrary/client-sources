package us.dev.direkt.event.internal.events.game.network;

import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;

/**
 * @author Foundry
 */
public class EventEncodePacket implements PacketEvent {
    private Packet<?> packet;
    private PacketBuffer buffer;
    private EnumConnectionState state;
    private int packetID;

    public EventEncodePacket(Packet<?> packet, EnumConnectionState state, int packetID, PacketBuffer buffer) {
        this.packet = packet;
        this.buffer = buffer;
        this.state = state;
        this.packetID = packetID;
    }

    public void setPacket(Packet<?> packet) {
        this.packet = packet;
    }

    public Packet<?> getPacket() {
        return this.packet;
    }

    public void setPacketBuffer(PacketBuffer packetBuffer) {
        this.buffer = packetBuffer;
    }

    public PacketBuffer getPacketBuffer() {
        return this.buffer;
    }

    public void setPacketID(int packetID) {
        this.packetID = packetID;
    }

    public int getPacketID() {
        return this.packetID;
    }

    public EnumConnectionState getState() {
        return this.state;
    }
}
