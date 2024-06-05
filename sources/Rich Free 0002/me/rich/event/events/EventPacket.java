package me.rich.event.events;

import me.rich.event.Event;
import net.minecraft.network.Packet;

public class EventPacket extends Event {
    public Packet packet;

    public EventPacket(Packet packet) {
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