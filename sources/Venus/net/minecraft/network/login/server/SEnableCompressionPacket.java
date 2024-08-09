/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.login.server;

import java.io.IOException;
import net.minecraft.client.network.login.IClientLoginNetHandler;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;

public class SEnableCompressionPacket
implements IPacket<IClientLoginNetHandler> {
    private int compressionThreshold;

    public SEnableCompressionPacket() {
    }

    public SEnableCompressionPacket(int n) {
        this.compressionThreshold = n;
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.compressionThreshold = packetBuffer.readVarInt();
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarInt(this.compressionThreshold);
    }

    @Override
    public void processPacket(IClientLoginNetHandler iClientLoginNetHandler) {
        iClientLoginNetHandler.handleEnableCompression(this);
    }

    public int getCompressionThreshold() {
        return this.compressionThreshold;
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IClientLoginNetHandler)iNetHandler);
    }
}

