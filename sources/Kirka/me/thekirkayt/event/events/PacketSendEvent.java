/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.event.events;

import me.thekirkayt.event.Event;
import net.minecraft.network.Packet;

public class PacketSendEvent
extends Event {
    private Packet packet;
    private Event.State state;

    public PacketSendEvent(Event.State state, Packet packet) {
        this.state = state;
        this.packet = packet;
    }

    public Packet getPacket() {
        return this.packet;
    }

    public Event.State getState() {
        return this.state;
    }

    public void setPacket(Packet packet) {
        this.packet = packet;
    }

    public void setState(Event.State state) {
        this.state = state;
    }
}

