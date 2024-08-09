/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.status.server;

import java.io.IOException;
import net.minecraft.client.network.status.IClientStatusNetHandler;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;

public class SPongPacket
implements IPacket<IClientStatusNetHandler> {
    private long clientTime;

    public SPongPacket() {
    }

    public SPongPacket(long l) {
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
    public void processPacket(IClientStatusNetHandler iClientStatusNetHandler) {
        iClientStatusNetHandler.handlePong(this);
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IClientStatusNetHandler)iNetHandler);
    }
}

