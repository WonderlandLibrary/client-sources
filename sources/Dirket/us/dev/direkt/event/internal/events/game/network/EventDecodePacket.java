package us.dev.direkt.event.internal.events.game.network;

import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import us.dev.direkt.event.CancellableEvent;

/**
 * @author Foundry
 */
public class EventDecodePacket extends CancellableEvent implements PacketEvent {
    private Packet<?> packet;
    private EnumConnectionState state;
    private PacketBuffer buffer;

    public EventDecodePacket(Packet<?> packet, EnumConnectionState state, PacketBuffer buffer) {
        this.packet = packet;
        this.state = state;
        this.buffer = buffer;
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

    public EnumConnectionState getState() {
        return this.state;
    }
}
