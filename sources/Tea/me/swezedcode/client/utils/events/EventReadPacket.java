package me.swezedcode.client.utils.events;

import com.darkmagician6.eventapi.events.Event;

import net.minecraft.network.Packet;

public class EventReadPacket implements Event {
	
	private Packet packet;
	
	private boolean isCancelled;
	
	private packetState state;
	
	public EventReadPacket(Packet packet, packetState state) {
		this.packet = packet;
		this.state = state;
	}
	
	public packetState getState() {
		return this.state;
	}
	
	public void setState(packetState state) {
		this.state = state;
	}
	
	public Packet getPacket() {
		return this.packet;
	}
	
	public boolean isCancelled() {
		return this.isCancelled;
	}
	
	public void setCancelled(boolean cancelled) {
		this.isCancelled = cancelled;
	}
	
	public enum packetState {
		PRE, POST
	}
}