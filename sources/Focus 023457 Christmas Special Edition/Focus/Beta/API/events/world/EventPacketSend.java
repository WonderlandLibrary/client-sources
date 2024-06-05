
package Focus.Beta.API.events.world;

import Focus.Beta.API.Event;
import net.minecraft.network.Packet;

public class EventPacketSend extends Event {
	private Packet packet;

	public EventPacketSend(Packet packet) {
		this.packet = packet;
	}

	public Packet getPacket() {
		return this.packet;
	}

	public void setPacket(Packet packet) {
		this.packet = packet;
	}
}
