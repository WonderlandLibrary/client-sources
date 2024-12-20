/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.math.BlockPos;

public class SPacketBlockBreakAnim
implements Packet<INetHandlerPlayClient> {
    private int breakerId;
    private BlockPos position;
    private int progress;

    public SPacketBlockBreakAnim() {
    }

    public SPacketBlockBreakAnim(int breakerIdIn, BlockPos positionIn, int progressIn) {
        this.breakerId = breakerIdIn;
        this.position = positionIn;
        this.progress = progressIn;
    }

    @Override
    public void readPacketData(PacketBuffer buf) throws IOException {
        this.breakerId = buf.readVarIntFromBuffer();
        this.position = buf.readBlockPos();
        this.progress = buf.readUnsignedByte();
    }

    @Override
    public void writePacketData(PacketBuffer buf) throws IOException {
        buf.writeVarIntToBuffer(this.breakerId);
        buf.writeBlockPos(this.position);
        buf.writeByte(this.progress);
    }

    @Override
    public void processPacket(INetHandlerPlayClient handler) {
        handler.handleBlockBreakAnim(this);
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
}

