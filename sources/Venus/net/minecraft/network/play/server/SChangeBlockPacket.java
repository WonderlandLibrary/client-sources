/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class SChangeBlockPacket
implements IPacket<IClientPlayNetHandler> {
    private BlockPos pos;
    private BlockState state;

    public SChangeBlockPacket() {
    }

    public SChangeBlockPacket(BlockPos blockPos, BlockState blockState) {
        this.pos = blockPos;
        this.state = blockState;
    }

    public SChangeBlockPacket(IBlockReader iBlockReader, BlockPos blockPos) {
        this(blockPos, iBlockReader.getBlockState(blockPos));
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.pos = packetBuffer.readBlockPos();
        this.state = Block.BLOCK_STATE_IDS.getByValue(packetBuffer.readVarInt());
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeBlockPos(this.pos);
        packetBuffer.writeVarInt(Block.getStateId(this.state));
    }

    @Override
    public void processPacket(IClientPlayNetHandler iClientPlayNetHandler) {
        iClientPlayNetHandler.handleBlockChange(this);
    }

    public BlockState getState() {
        return this.state;
    }

    public BlockPos getPos() {
        return this.pos;
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IClientPlayNetHandler)iNetHandler);
    }
}

