package none.event.events;

import net.minecraft.network.Packet;
import none.event.Event;

public class EventReceivePacket extends Event {
    private Packet packet;

    public void fire(Packet packet) {
        this.packet = packet;
        super.fire();
    }

    public Packet getPacket() {
        return packet;
    }
    public void setPacket(Packet packet) {
        this.packet = packet;
    }
}
