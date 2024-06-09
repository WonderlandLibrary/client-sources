/*
 * Decompiled with CFR 0.145.
 */
package us.amerikan.events;

import com.darkmagician6.eventapi.events.Event;
import com.darkmagician6.eventapi.types.EventType;
import net.minecraft.network.Packet;

public class PacketEvent
implements Event {
    private final EventType eventType;
    private Packet packet;

    public PacketEvent(EventType eventType, Packet packet) {
        this.eventType = eventType;
        this.packet = packet;
    }

    public EventType getEventType() {
        return this.eventType;
    }

    public Packet getPacket() {
        return this.packet;
    }

    public void setPacket(Packet packet) {
        this.packet = packet;
    }
}

