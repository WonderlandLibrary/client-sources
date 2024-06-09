package me.teus.eclipse.events.packet;

import net.minecraft.network.Packet;
import xyz.lemon.event.bus.Event;

public class EventSendPacket extends Event {
    private Packet packet;

    public EventSendPacket(Packet packet) {
        this.packet = packet;
    }

    public Packet getPacket() {
        return packet;
    }

    public void setPacket(Packet packet) {
        this.packet = packet;
    }
}
