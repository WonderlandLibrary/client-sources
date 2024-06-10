package me.dinozoid.strife.event.implementations.network;

import me.dinozoid.strife.event.Event;
import net.minecraft.network.Packet;

public class PacketInboundEvent extends Event {

    private final Packet packet;

    public PacketInboundEvent(Packet packet) {
        this.packet = packet;
    }

    public Packet packet() {
        return packet;
    }

}
