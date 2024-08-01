package wtf.diablo.client.event.impl.network;

import net.minecraft.network.Packet;
import wtf.diablo.client.event.api.AbstractEvent;

public final class RecievePacketEvent extends AbstractEvent {
    private Packet<?> packet;

    public RecievePacketEvent(final Packet<?> packet) {
        this.packet = packet;
    }

    public Packet<?> getPacket() {
        return packet;
    }

    public void setPacket(final Packet<?> packet) {
        this.packet = packet;
    }
}
