package io.github.liticane.clients.feature.event.impl.other;

import io.github.liticane.clients.feature.event.Event;
import net.minecraft.network.Packet;

public class PacketEvent extends Event {
    private Packet<?> packet;
    private final Type type;

    public PacketEvent(Packet<?> packet, Type type) {
        this.packet = packet;
        this.type = type;
    }

    public Packet<?> getPacket() {
        return packet;
    }

    public void setPacket(Packet<?> packet) {
        this.packet = packet;
    }

    public Type getType() {
        return type;
    }

    public enum Type {
        RECEIVED,
        SENT
    }
}
