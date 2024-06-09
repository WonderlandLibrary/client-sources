/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.event.events;

import me.thekirkayt.event.Event;
import net.minecraft.network.Packet;

public class PacketReceiveEvent
extends Event {
    private Packet packet;

    public Packet getPacket() {
        return this.packet;
    }

    public void setPacket(Packet packet) {
        this.packet = packet;
    }

    public PacketReceiveEvent(Packet packet) {
        this.packet = packet;
    }
}

