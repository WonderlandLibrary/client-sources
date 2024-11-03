package net.silentclient.client.event.impl;

import net.minecraft.network.Packet;
import net.silentclient.client.event.EventCancelable;

public class EventSendPacket extends EventCancelable {
	private Packet<?> packet;
	
	public EventSendPacket(Packet<?> packet) {
		this.packet = packet;
	}

	public Packet<?> getPacket() {
		return packet;
	}

	public void setPacket(Packet<?> packet) {
		this.packet = packet;
	}
}
