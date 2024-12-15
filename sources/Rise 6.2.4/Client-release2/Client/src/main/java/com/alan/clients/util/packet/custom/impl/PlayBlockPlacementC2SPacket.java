package com.alan.clients.util.packet.custom.impl;

import com.alan.clients.util.packet.custom.RawPacket;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;

import java.io.IOException;

public class PlayBlockPlacementC2SPacket extends RawPacket {

    private final C08PacketPlayerBlockPlacement packet;

    public PlayBlockPlacementC2SPacket(C08PacketPlayerBlockPlacement packet) {
        super(0x2E, EnumConnectionState.PLAY);
        this.packet = packet;
    }

    @Override
    public void writePacketData(PacketBuffer buf) throws IOException {
        buf.writeVarIntToBuffer(0);
        buf.writeBlockPos(packet.getPosition());
        buf.writeVarIntToBuffer(packet.getPlacedBlockDirection());
        buf.writeFloat(packet.facingX);
        buf.writeFloat(packet.facingY);
        buf.writeFloat(packet.facingZ);
        buf.writeBoolean(false);
    }

    @Override
    public void readPacketData(PacketBuffer buf) throws IOException {

    }
}
