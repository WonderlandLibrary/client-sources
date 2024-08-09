/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.IServerPlayNetHandler;
import net.minecraft.util.math.BlockPos;

public class CQueryTileEntityNBTPacket
implements IPacket<IServerPlayNetHandler> {
    private int transactionId;
    private BlockPos pos;

    public CQueryTileEntityNBTPacket() {
    }

    public CQueryTileEntityNBTPacket(int n, BlockPos blockPos) {
        this.transactionId = n;
        this.pos = blockPos;
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.transactionId = packetBuffer.readVarInt();
        this.pos = packetBuffer.readBlockPos();
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarInt(this.transactionId);
        packetBuffer.writeBlockPos(this.pos);
    }

    @Override
    public void processPacket(IServerPlayNetHandler iServerPlayNetHandler) {
        iServerPlayNetHandler.processNBTQueryBlockEntity(this);
    }

    public int getTransactionId() {
        return this.transactionId;
    }

    public BlockPos getPosition() {
        return this.pos;
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IServerPlayNetHandler)iNetHandler);
    }
}

