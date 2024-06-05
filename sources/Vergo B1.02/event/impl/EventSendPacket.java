package xyz.vergoclient.event.impl;

import xyz.vergoclient.event.Event;
import net.minecraft.network.Packet;

public class EventSendPacket extends Event {
	
	public EventSendPacket(Packet packet) {
		this.packet = packet;
	}
	
	public Packet packet = null;
	
}
