package me.valk.event.events.other;

import me.valk.event.Event;
import net.minecraft.network.Packet;

public class EventPacket extends Event {

	private EventPacketType type;
	private Packet packet;
	
	public EventPacket(EventPacketType type, Packet packet) {
		this.type = type;
		this.packet = packet;
	}
	
	public Packet getPacket() {
		return packet;
	}

	public void setPacket(Packet packet) {
		this.packet = packet;
	}
	
	public EventPacketType getType() {
		return type;
	}
	
	public enum EventPacketType {
		
		SEND,
		RECEIVE
		
	}
	
}
