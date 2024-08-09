/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;

public class SUpdateTileEntityPacket
implements IPacket<IClientPlayNetHandler> {
    private BlockPos blockPos;
    private int tileEntityType;
    private CompoundNBT nbt;

    public SUpdateTileEntityPacket() {
    }

    public SUpdateTileEntityPacket(BlockPos blockPos, int n, CompoundNBT compoundNBT) {
        this.blockPos = blockPos;
        this.tileEntityType = n;
        this.nbt = compoundNBT;
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.blockPos = packetBuffer.readBlockPos();
        this.tileEntityType = packetBuffer.readUnsignedByte();
        this.nbt = packetBuffer.readCompoundTag();
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeBlockPos(this.blockPos);
        packetBuffer.writeByte((byte)this.tileEntityType);
        packetBuffer.writeCompoundTag(this.nbt);
    }

    @Override
    public void processPacket(IClientPlayNetHandler iClientPlayNetHandler) {
        iClientPlayNetHandler.handleUpdateTileEntity(this);
    }

    public BlockPos getPos() {
        return this.blockPos;
    }

    public int getTileEntityType() {
        return this.tileEntityType;
    }

    public CompoundNBT getNbtCompound() {
        return this.nbt;
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IClientPlayNetHandler)iNetHandler);
    }
}

