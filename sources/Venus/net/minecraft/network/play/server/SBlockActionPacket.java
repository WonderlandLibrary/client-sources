/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.block.Block;
import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;

public class SBlockActionPacket
implements IPacket<IClientPlayNetHandler> {
    private BlockPos blockPosition;
    private int instrument;
    private int pitch;
    private Block block;

    public SBlockActionPacket() {
    }

    public SBlockActionPacket(BlockPos blockPos, Block block, int n, int n2) {
        this.blockPosition = blockPos;
        this.block = block;
        this.instrument = n;
        this.pitch = n2;
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.blockPosition = packetBuffer.readBlockPos();
        this.instrument = packetBuffer.readUnsignedByte();
        this.pitch = packetBuffer.readUnsignedByte();
        this.block = Registry.BLOCK.getByValue(packetBuffer.readVarInt());
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeBlockPos(this.blockPosition);
        packetBuffer.writeByte(this.instrument);
        packetBuffer.writeByte(this.pitch);
        packetBuffer.writeVarInt(Registry.BLOCK.getId(this.block));
    }

    @Override
    public void processPacket(IClientPlayNetHandler iClientPlayNetHandler) {
        iClientPlayNetHandler.handleBlockAction(this);
    }

    public BlockPos getBlockPosition() {
        return this.blockPosition;
    }

    public int getData1() {
        return this.instrument;
    }

    public int getData2() {
        return this.pitch;
    }

    public Block getBlockType() {
        return this.block;
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IClientPlayNetHandler)iNetHandler);
    }
}

