/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;

public class SOpenHorseWindowPacket
implements IPacket<IClientPlayNetHandler> {
    private int field_218705_a;
    private int field_218706_b;
    private int field_218707_c;

    public SOpenHorseWindowPacket() {
    }

    public SOpenHorseWindowPacket(int n, int n2, int n3) {
        this.field_218705_a = n;
        this.field_218706_b = n2;
        this.field_218707_c = n3;
    }

    @Override
    public void processPacket(IClientPlayNetHandler iClientPlayNetHandler) {
        iClientPlayNetHandler.handleOpenHorseWindow(this);
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.field_218705_a = packetBuffer.readUnsignedByte();
        this.field_218706_b = packetBuffer.readVarInt();
        this.field_218707_c = packetBuffer.readInt();
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeByte(this.field_218705_a);
        packetBuffer.writeVarInt(this.field_218706_b);
        packetBuffer.writeInt(this.field_218707_c);
    }

    public int func_218704_b() {
        return this.field_218705_a;
    }

    public int func_218702_c() {
        return this.field_218706_b;
    }

    public int func_218703_d() {
        return this.field_218707_c;
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IClientPlayNetHandler)iNetHandler);
    }
}

