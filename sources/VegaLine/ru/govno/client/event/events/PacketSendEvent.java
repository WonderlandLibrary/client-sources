/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.event.events;

import net.minecraft.network.Packet;
import ru.govno.client.event.Event;

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

