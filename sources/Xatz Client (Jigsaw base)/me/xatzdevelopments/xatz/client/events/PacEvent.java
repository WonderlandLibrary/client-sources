package me.xatzdevelopments.xatz.client.events;

import net.minecraft.network.Packet;

public class PacEvent {
    private static Packet packet;
    private boolean outgoing;

    public boolean isPre() {
        return pre;
    }

    public boolean isPost() {
        return !pre;
    }

    private boolean pre;

    public void fire(Packet packet, boolean outgoing) {
        this.packet = packet;
        this.outgoing = outgoing;
        this.pre = true;
    }

    public void fire(Packet packet) {
        this.packet = packet;
        this.pre = false;
    }

    public static Packet getPacket() {
        return packet;
    }

    public void setPacket(Packet packet) {
        this.packet = packet;
    }

    public boolean isOutgoing() {
        return outgoing;
    }

    public boolean isIncoming() {
        return !outgoing;
    }
}
