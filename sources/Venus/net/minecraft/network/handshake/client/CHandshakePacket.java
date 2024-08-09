/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.handshake.client;

import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.ProtocolType;
import net.minecraft.network.handshake.IHandshakeNetHandler;
import net.minecraft.util.SharedConstants;

public class CHandshakePacket
implements IPacket<IHandshakeNetHandler> {
    private int protocolVersion;
    private String ip;
    private int port;
    private ProtocolType requestedState;

    public CHandshakePacket() {
    }

    public CHandshakePacket(String string, int n, ProtocolType protocolType) {
        this.protocolVersion = SharedConstants.getVersion().getProtocolVersion();
        this.ip = string;
        this.port = n;
        this.requestedState = protocolType;
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.protocolVersion = packetBuffer.readVarInt();
        this.ip = packetBuffer.readString(255);
        this.port = packetBuffer.readUnsignedShort();
        this.requestedState = ProtocolType.getById(packetBuffer.readVarInt());
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarInt(this.protocolVersion);
        packetBuffer.writeString(this.ip);
        packetBuffer.writeShort(this.port);
        packetBuffer.writeVarInt(this.requestedState.getId());
    }

    @Override
    public void processPacket(IHandshakeNetHandler iHandshakeNetHandler) {
        iHandshakeNetHandler.processHandshake(this);
    }

    public ProtocolType getRequestedState() {
        return this.requestedState;
    }

    public int getProtocolVersion() {
        return this.protocolVersion;
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IHandshakeNetHandler)iNetHandler);
    }
}

