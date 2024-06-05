package zamorozka.event.events;



import zamorozka.event.*;

import net.minecraft.network.Packet;

public class EventReceivePacket extends Event {
    public Packet packet;

    public EventReceivePacket(Packet packet) {
        this.packet = packet;
    }

    public Packet getPacket() {
        return packet;
    }
    public void setPacket(Packet packet) {
        this.packet = packet;
    }
}
