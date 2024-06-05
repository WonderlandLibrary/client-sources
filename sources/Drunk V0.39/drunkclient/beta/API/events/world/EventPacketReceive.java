/*
 * Decompiled with CFR 0.152.
 */
package drunkclient.beta.API.events.world;

import drunkclient.beta.API.Event;
import net.minecraft.network.Packet;

public class EventPacketReceive
extends Event {
    private Packet packet;
    private boolean outgoing;

    public EventPacketReceive(Packet packet, boolean outgoing) {
        this.packet = packet;
        this.outgoing = outgoing;
    }

    public Packet getPacket() {
        return this.packet;
    }

    public void setPacket(Packet packet) {
        this.packet = packet;
    }

    public boolean isOutgoing() {
        return this.outgoing;
    }

    public boolean isIncoming() {
        if (this.outgoing) return false;
        return true;
    }
}

