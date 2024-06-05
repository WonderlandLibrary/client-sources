package zamorozka.event.events;

import zamorozka.event.*;

import net.minecraft.network.Packet;

public class EventSendPacket extends Event {
    public Packet packet;

    public EventSendPacket(Packet packet) {
        setPacket(packet);
    }

	public Packet getPacket()
	{
		return packet;
	}
    public void setPacket(Packet packet) {
        this.packet = packet;
    }
}