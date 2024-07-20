/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.status.client;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.status.INetHandlerStatusServer;

public class CPacketPing
implements Packet<INetHandlerStatusServer> {
    public long clientTime;

    public CPacketPing() {
    }

    public CPacketPing(long clientTimeIn) {
        this.clientTime = clientTimeIn;
    }

    @Override
    public void readPacketData(PacketBuffer buf) throws IOException {
        this.clientTime = buf.readLong();
    }

    @Override
    public void writePacketData(PacketBuffer buf) throws IOException {
        buf.writeLong(this.clientTime);
    }

    @Override
    public void processPacket(INetHandlerStatusServer handler) {
        handler.processPing(this);
    }

    public long getClientTime() {
        return this.clientTime;
    }
}

