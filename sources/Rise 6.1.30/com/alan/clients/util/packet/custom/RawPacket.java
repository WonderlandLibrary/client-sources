package com.alan.clients.util.packet.custom;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;

import java.io.IOException;

@AllArgsConstructor
public abstract class RawPacket implements Packet {

    @Getter
    private final int packetID;
    @Getter
    private final EnumConnectionState direction;

    @Override
    public void readPacketData(PacketBuffer buf) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void writePacketData(PacketBuffer buf) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void processPacket(INetHandler handler) {

    }

}