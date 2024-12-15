package com.alan.clients.util.packet.custom.impl;

import com.alan.clients.util.packet.custom.RawPacket;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.PacketBuffer;

import java.io.IOException;

public final class PlayPongC2SPacket extends RawPacket {
    private int parameter;

    public PlayPongC2SPacket(int parameter) {
        super(0, EnumConnectionState.PLAY);
        this.parameter = parameter;
    }

    @Override
    public void readPacketData(final PacketBuffer buf) throws IOException {
        parameter = buf.readInt();
    }

    @Override
    public void writePacketData(final PacketBuffer buf) throws IOException {
        buf.writeInt(parameter);
    }

    @Override
    public int getPacketID() {
        return 0x1D;
    }
}