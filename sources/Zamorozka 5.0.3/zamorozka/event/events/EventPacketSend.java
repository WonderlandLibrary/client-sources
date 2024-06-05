package zamorozka.event.events;

import zamorozka.event.Event;
import net.minecraft.network.Packet;

public class EventPacketSend extends Event {
    public boolean cancel;
    public Packet packet;

    public EventPacketSend(Packet packet) {
        this.packet = packet;
    }

    public Packet getPacket() {
        return this.packet;
    }

    @Override
    public boolean isCancelled() {
        return this.cancel;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }

    public void setPacket(Packet packet) {
        this.packet = packet;
    }
}
