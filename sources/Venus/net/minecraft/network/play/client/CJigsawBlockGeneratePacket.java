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

public class CJigsawBlockGeneratePacket
implements IPacket<IServerPlayNetHandler> {
    private BlockPos field_240841_a_;
    private int field_240842_b_;
    private boolean field_240843_c_;

    public CJigsawBlockGeneratePacket() {
    }

    public CJigsawBlockGeneratePacket(BlockPos blockPos, int n, boolean bl) {
        this.field_240841_a_ = blockPos;
        this.field_240842_b_ = n;
        this.field_240843_c_ = bl;
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.field_240841_a_ = packetBuffer.readBlockPos();
        this.field_240842_b_ = packetBuffer.readVarInt();
        this.field_240843_c_ = packetBuffer.readBoolean();
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeBlockPos(this.field_240841_a_);
        packetBuffer.writeVarInt(this.field_240842_b_);
        packetBuffer.writeBoolean(this.field_240843_c_);
    }

    @Override
    public void processPacket(IServerPlayNetHandler iServerPlayNetHandler) {
        iServerPlayNetHandler.func_230549_a_(this);
    }

    public BlockPos func_240844_b_() {
        return this.field_240841_a_;
    }

    public int func_240845_c_() {
        return this.field_240842_b_;
    }

    public boolean func_240846_d_() {
        return this.field_240843_c_;
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IServerPlayNetHandler)iNetHandler);
    }
}

