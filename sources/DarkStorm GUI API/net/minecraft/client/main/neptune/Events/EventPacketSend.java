package net.minecraft.client.main.neptune.Events;

import net.minecraft.client.main.neptune.memes.events.callables.MemeMeable;
import net.minecraft.network.Packet;

public class EventPacketSend extends MemeMeable {

	public Packet packet;

	public EventPacketSend(Packet packet) {
		this.packet = packet;
	}

}
