package com.shroomclient.shroomclientnextgen.util;

import java.time.Instant;
import net.minecraft.network.listener.PacketListener;
import net.minecraft.network.packet.Packet;

public class TimedPacket<T extends PacketListener> {

    public final Packet<T> packet;
    private final long time;

    public TimedPacket(Packet<T> packet) {
        this.packet = packet;
        this.time = Instant.now().toEpochMilli();
    }

    public long msPassed() {
        return Instant.now().toEpochMilli() - time;
    }
}
