/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class SPacketUnloadChunk
implements Packet<INetHandlerPlayClient> {
    private int x;
    private int z;

    public SPacketUnloadChunk() {
    }

    public SPacketUnloadChunk(int xIn, int zIn) {
        this.x = xIn;
        this.z = zIn;
    }

    @Override
    public void readPacketData(PacketBuffer buf) throws IOException {
        this.x = buf.readInt();
        this.z = buf.readInt();
    }

    @Override
    public void writePacketData(PacketBuffer buf) throws IOException {
        buf.writeInt(this.x);
        buf.writeInt(this.z);
    }

    @Override
    public void processPacket(INetHandlerPlayClient handler) {
        handler.processChunkUnload(this);
    }

    public int getX() {
        return this.x;
    }

    public int getZ() {
        return this.z;
    }
}

