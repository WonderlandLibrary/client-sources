package dev.star.event.impl.network;

import net.minecraft.network.Packet;

public class PacketReceiveEvent extends PacketEvent {
    public PacketReceiveEvent(Packet<?> packet) {
        super(packet);
    }
}
