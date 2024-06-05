package xyz.vergoclient.event.impl;

import xyz.vergoclient.event.Event;
import net.minecraft.network.Packet;

public class EventReceivePacket extends Event {
	
	public Packet packet;
	
	public EventReceivePacket(EventType type, Packet packet) {
		
		setType(type);
		this.packet = packet;
		
	}
	
}
