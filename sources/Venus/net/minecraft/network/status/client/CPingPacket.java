/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.status.client;

import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.status.IServerStatusNetHandler;

public class CPingPacket
implements IPacket<IServerStatusNetHandler> {
    private long clientTime;

    public CPingPacket() {
    }

    public CPingPacket(long l) {
        this.clientTime = l;
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.clientTime = packetBuffer.readLong();
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeLong(this.clientTime);
    }

    @Override
    public void processPacket(IServerStatusNetHandler iServerStatusNetHandler) {
        iServerStatusNetHandler.processPing(this);
    }

    public long getClientTime() {
        return this.clientTime;
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IServerStatusNetHandler)iNetHandler);
    }
}

