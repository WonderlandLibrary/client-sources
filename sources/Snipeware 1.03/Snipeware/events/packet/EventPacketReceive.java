package Snipeware.events.packet;

import Snipeware.events.Cancellable;
import Snipeware.events.Event;
import net.minecraft.network.Packet;

public final class EventPacketReceive extends Cancellable implements Event {
	
    private Packet packet;

    public EventPacketReceive(Packet packet) {
        this.packet = packet;
    }

    public Packet getPacket() {
        return this.packet;
    }
    public void setPacket(Packet packet) {
    	this.packet = packet;
    }
}
