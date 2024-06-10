package Snipeware.events.packet;

import Snipeware.events.Cancellable;
import Snipeware.events.Event;
import net.minecraft.network.Packet;

public final class EventPacketSend extends Cancellable implements Event  {
	
    private Packet packet;

    public EventPacketSend(Packet packet) {
        this.packet = packet;
    }

    @SuppressWarnings("unchecked")
    public <T extends Packet> T getPacket() {
        return (T) packet;
    }
    
    public void setPacket(Packet packet) {
    	this.packet = packet;
    }
}
