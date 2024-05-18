/*
 * Decompiled with CFR 0_118.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 */
package net.minecraft.network.play.server;

import io.netty.buffer.ByteBuf;
import java.io.IOException;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ObjectIntIdentityMap;
import net.minecraft.util.Vec3i;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.chunk.Chunk;

public class S22PacketMultiBlockChange
implements Packet {
    private ChunkCoordIntPair field_148925_b;
    private BlockUpdateData[] field_179845_b;
    private static final String __OBFID = "CL_00001290";

    public S22PacketMultiBlockChange() {
    }

    public S22PacketMultiBlockChange(int p_i45181_1_, short[] p_i45181_2_, Chunk p_i45181_3_) {
        this.field_148925_b = new ChunkCoordIntPair(p_i45181_3_.xPosition, p_i45181_3_.zPosition);
        this.field_179845_b = new BlockUpdateData[p_i45181_1_];
        int var4 = 0;
        while (var4 < this.field_179845_b.length) {
            this.field_179845_b[var4] = new BlockUpdateData(this, p_i45181_2_[var4], p_i45181_3_);
            ++var4;
        }
    }

    @Override
    public void readPacketData(PacketBuffer data) throws IOException {
        this.field_148925_b = new ChunkCoordIntPair(data.readInt(), data.readInt());
        this.field_179845_b = new BlockUpdateData[data.readVarIntFromBuffer()];
        int var2 = 0;
        while (var2 < this.field_179845_b.length) {
            this.field_179845_b[var2] = new BlockUpdateData(this, data.readShort(), (IBlockState)Block.BLOCK_STATE_IDS.getByValue(data.readVarIntFromBuffer()));
            ++var2;
        }
    }

    @Override
    public void writePacketData(PacketBuffer data) throws IOException {
        data.writeInt(this.field_148925_b.chunkXPos);
        data.writeInt(this.field_148925_b.chunkZPos);
        data.writeVarIntToBuffer(this.field_179845_b.length);
        BlockUpdateData[] var2 = this.field_179845_b;
        int var3 = var2.length;
        int var4 = 0;
        while (var4 < var3) {
            BlockUpdateData var5 = var2[var4];
            data.writeShort(var5.func_180089_b());
            data.writeVarIntToBuffer(Block.BLOCK_STATE_IDS.get(var5.func_180088_c()));
            ++var4;
        }
    }

    public void func_180729_a(INetHandlerPlayClient p_180729_1_) {
        p_180729_1_.handleMultiBlockChange(this);
    }

    public BlockUpdateData[] func_179844_a() {
        return this.field_179845_b;
    }

    @Override
    public void processPacket(INetHandler handler) {
        this.func_180729_a((INetHandlerPlayClient)handler);
    }

    public class BlockUpdateData {
        private final short field_180091_b;
        private final IBlockState field_180092_c;
        private static final String __OBFID = "CL_00002302";
        final /* synthetic */ S22PacketMultiBlockChange this$0;

        public BlockUpdateData(S22PacketMultiBlockChange s22PacketMultiBlockChange, short p_i45984_2_, IBlockState p_i45984_3_) {
            this.this$0 = s22PacketMultiBlockChange;
            this.field_180091_b = p_i45984_2_;
            this.field_180092_c = p_i45984_3_;
        }

        public BlockUpdateData(S22PacketMultiBlockChange s22PacketMultiBlockChange, short p_i45985_2_, Chunk p_i45985_3_) {
            this.this$0 = s22PacketMultiBlockChange;
            this.field_180091_b = p_i45985_2_;
            this.field_180092_c = p_i45985_3_.getBlockState(this.func_180090_a());
        }

        public BlockPos func_180090_a() {
            return new BlockPos(this.this$0.field_148925_b.getBlock(this.field_180091_b >> 12 & 15, this.field_180091_b & 255, this.field_180091_b >> 8 & 15));
        }

        public short func_180089_b() {
            return this.field_180091_b;
        }

        public IBlockState func_180088_c() {
            return this.field_180092_c;
        }
    }

}

