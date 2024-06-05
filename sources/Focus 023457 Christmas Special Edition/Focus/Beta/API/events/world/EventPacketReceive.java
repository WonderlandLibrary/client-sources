package Focus.Beta.API.events.world;

import Focus.Beta.API.Event;
import net.minecraft.network.Packet;

public class EventPacketReceive extends Event {
	private Packet packet;

    private boolean outgoing;

	public EventPacketReceive(Packet packet, boolean outgoing) {
		this.packet = packet;

        this.outgoing = outgoing;
	}

	

	public Packet getPacket() {
		return this.packet;
	}

	public void setPacket(Packet packet) {
		this.packet = packet;
	}
	
	   public boolean isOutgoing() {
	        return outgoing;
	    }

	    public boolean isIncoming() {
	        return !outgoing;
	    }
}
