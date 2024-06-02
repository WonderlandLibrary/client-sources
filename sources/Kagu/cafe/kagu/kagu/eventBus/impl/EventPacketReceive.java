/**
 * 
 */
package cafe.kagu.kagu.eventBus.impl;

import cafe.kagu.kagu.eventBus.Event;
import net.minecraft.network.Packet;

/**
 * @author lavaflowglow
 *
 */
public class EventPacketReceive extends Event {

	/**
	 * @param eventPosition The position of the event
	 * @param packet        The packet being received
	 */
	public EventPacketReceive(EventPosition eventPosition, Packet<?> packet) {
		super(eventPosition);
		this.packet = packet;
	}

	private Packet<?> packet;

	/**
	 * @return the packet
	 */
	public Packet<?> getPacket() {
		return packet;
	}

	/**
	 * @param packet the packet to set
	 */
	public void setPacket(Packet<?> packet) {
		this.packet = packet;
	}

}
