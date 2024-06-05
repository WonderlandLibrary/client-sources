package net.minecraft.network.play.server;

import net.minecraft.block.state.*;
import net.minecraft.world.chunk.*;
import net.minecraft.util.*;

public class BlockUpdateData
{
    private final short field_180091_b;
    private final IBlockState field_180092_c;
    
    public BlockUpdateData(final short p_i45984_2_, final IBlockState p_i45984_3_) {
        this.field_180091_b = p_i45984_2_;
        this.field_180092_c = p_i45984_3_;
    }
    
    public BlockUpdateData(final short p_i45985_2_, final Chunk p_i45985_3_) {
        this.field_180091_b = p_i45985_2_;
        this.field_180092_c = p_i45985_3_.getBlockState(this.func_180090_a());
    }
    
    public BlockPos func_180090_a() {
        return new BlockPos(S22PacketMultiBlockChange.access$000(S22PacketMultiBlockChange.this).getBlock(this.field_180091_b >> 12 & 0xF, this.field_180091_b & 0xFF, this.field_180091_b >> 8 & 0xF));
    }
    
    public short func_180089_b() {
        return this.field_180091_b;
    }
    
    public IBlockState func_180088_c() {
        return this.field_180092_c;
    }
}
