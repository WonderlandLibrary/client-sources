package net.minecraft.client.main.neptune.Events;

import net.minecraft.client.main.neptune.memes.events.callables.MemeMeable;
import net.minecraft.network.Packet;

public class EventPacketTake extends MemeMeable {

	public Packet packet;

	public EventPacketTake(Packet packet) {
		this.packet = packet;
	}

}
