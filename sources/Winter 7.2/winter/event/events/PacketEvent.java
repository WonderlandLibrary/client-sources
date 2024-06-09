/*
 * Decompiled with CFR 0_122.
 */
package winter.event.events;

import net.minecraft.network.Packet;
import winter.event.Event;

public class PacketEvent
extends Event {
    public static Packet packet;
    private boolean outgoing;

    public PacketEvent(Packet packet, boolean outgoing) {
        PacketEvent.packet = packet;
        this.outgoing = outgoing;
    }

    public Packet getPacket() {
        return packet;
    }

    public void setPacket(Packet p2) {
        packet = p2;
    }

    public boolean isOutgoing() {
        return this.outgoing;
    }

    public boolean isIncoming() {
        return !this.outgoing;
    }
}

