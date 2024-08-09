/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;

public class SAnimateBlockBreakPacket
implements IPacket<IClientPlayNetHandler> {
    private int breakerId;
    private BlockPos position;
    private int progress;

    public SAnimateBlockBreakPacket() {
    }

    public SAnimateBlockBreakPacket(int n, BlockPos blockPos, int n2) {
        this.breakerId = n;
        this.position = blockPos;
        this.progress = n2;
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.breakerId = packetBuffer.readVarInt();
        this.position = packetBuffer.readBlockPos();
        this.progress = packetBuffer.readUnsignedByte();
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarInt(this.breakerId);
        packetBuffer.writeBlockPos(this.position);
        packetBuffer.writeByte(this.progress);
    }

    @Override
    public void processPacket(IClientPlayNetHandler iClientPlayNetHandler) {
        iClientPlayNetHandler.handleBlockBreakAnim(this);
    }

    public int getBreakerId() {
        return this.breakerId;
    }

    public BlockPos getPosition() {
        return this.position;
    }

    public int getProgress() {
        return this.progress;
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IClientPlayNetHandler)iNetHandler);
    }
}

