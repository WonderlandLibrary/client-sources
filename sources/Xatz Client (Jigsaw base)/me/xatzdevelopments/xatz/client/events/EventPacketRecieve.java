package me.xatzdevelopments.xatz.client.events;

import net.minecraft.network.Packet;
import me.xatzdevelopments.xatz.client.darkmagician6.EventCancellable;

public class EventPacketRecieve extends EventCancellable
{
    private Packet packet;
    
    public EventPacketRecieve(final Packet packet) {
        this.packet = packet;
    }
    
    public Packet getPacket() {
        return this.packet;
    }
}