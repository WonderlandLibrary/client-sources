package me.dinozoid.strife.event.implementations.network;

import me.dinozoid.strife.event.Event;
import net.minecraft.network.Packet;

public class PacketOutboundEvent extends Event {

    private Packet packet;

    public PacketOutboundEvent(Packet packet) {
        this.packet = packet;
    }

    public Packet packet() {
        return packet;
    }

    public void packet(Packet packet) {
        this.packet = packet;
    }
}

