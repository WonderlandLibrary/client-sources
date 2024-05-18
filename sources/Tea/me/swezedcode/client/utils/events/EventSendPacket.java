package me.swezedcode.client.utils.events;

import com.darkmagician6.eventapi.events.Event;
import com.darkmagician6.eventapi.events.callables.EventCancellable;
import net.minecraft.network.Packet;

public class EventSendPacket extends EventCancellable implements Event {
	private Packet packet = null;

	public EventSendPacket(Packet packet) {
		setPacket(packet);
	}

	public Packet getPacket() {
		return this.packet;
	}

	public void setPacket(Packet packet) {
		this.packet = packet;
	}
}
