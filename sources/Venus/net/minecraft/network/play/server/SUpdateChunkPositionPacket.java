/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;

public class SUpdateChunkPositionPacket
implements IPacket<IClientPlayNetHandler> {
    private int field_218756_a;
    private int field_218757_b;

    public SUpdateChunkPositionPacket() {
    }

    public SUpdateChunkPositionPacket(int n, int n2) {
        this.field_218756_a = n;
        this.field_218757_b = n2;
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.field_218756_a = packetBuffer.readVarInt();
        this.field_218757_b = packetBuffer.readVarInt();
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarInt(this.field_218756_a);
        packetBuffer.writeVarInt(this.field_218757_b);
    }

    @Override
    public void processPacket(IClientPlayNetHandler iClientPlayNetHandler) {
        iClientPlayNetHandler.handleChunkPositionPacket(this);
    }

    public int func_218755_b() {
        return this.field_218756_a;
    }

    public int func_218754_c() {
        return this.field_218757_b;
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IClientPlayNetHandler)iNetHandler);
    }
}

