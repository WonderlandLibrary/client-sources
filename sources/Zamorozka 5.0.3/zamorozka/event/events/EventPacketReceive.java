package zamorozka.event.events;

import zamorozka.event.Event;
import net.minecraft.network.Packet;

public class EventPacketReceive extends Event {
    private Packet<?> packet;
    private boolean cancel;

    public EventPacketReceive(final Packet<?> packet) {
        this.packet = packet;
    }

    public Packet<?> getPacket() {
        return this.packet;
    }

    public void setPacket(final Packet<?> packet) {
        this.packet = packet;
    }
}