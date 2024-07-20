/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.event.events;

import net.minecraft.network.Packet;
import ru.govno.client.event.Event;

public class EventSendPacket
extends Event {
    public Packet packet;

    public EventSendPacket(Packet packet) {
        this.setPacket(packet);
    }

    public Packet getPacket() {
        return this.packet;
    }

    public void setPacket(Packet packet) {
        this.packet = packet;
    }
}

