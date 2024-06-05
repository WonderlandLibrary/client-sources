package com.thunderware.events.listeners;

import com.thunderware.events.Event;
import com.thunderware.events.EventDirection;
import com.thunderware.events.EventPacketHandler;

import net.minecraft.network.Packet;

public class EventPacket extends Event<EventPacket> { 
	private Packet packet;
	private EventDirection direction;
	private boolean cancelled;
	
	public EventPacket(EventDirection direction, Packet packet) {
		this.packet = packet;
		this.direction = direction;
	}
	
	public Packet getPacket() {
		return packet;
	}

	@Override
	public EventDirection getDirection() {
		return direction;
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}
}
