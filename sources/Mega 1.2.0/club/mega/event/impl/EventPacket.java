package club.mega.event.impl;

import club.mega.event.Event;
import net.minecraft.network.Packet;

public class EventPacket extends Event {

    private Packet packet;

    public EventPacket(final Packet packet) {
        this.packet = packet;
    }

    public final Packet getPacket() {
        return packet;
    }

    public final void setPacket(final Packet packet) {
        this.packet = packet;
    }

}
