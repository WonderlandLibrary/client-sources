package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;

public class S22PacketMultiBlockChange implements Packet
{
    private ChunkCoordIntPair HorizonCode_Horizon_È;
    private HorizonCode_Horizon_È[] Â;
    private static final String Ý = "CL_00001290";
    
    public S22PacketMultiBlockChange() {
    }
    
    public S22PacketMultiBlockChange(final int p_i45181_1_, final short[] p_i45181_2_, final Chunk p_i45181_3_) {
        this.HorizonCode_Horizon_È = new ChunkCoordIntPair(p_i45181_3_.HorizonCode_Horizon_È, p_i45181_3_.Â);
        this.Â = new HorizonCode_Horizon_È[p_i45181_1_];
        for (int var4 = 0; var4 < this.Â.length; ++var4) {
            this.Â[var4] = new HorizonCode_Horizon_È(p_i45181_2_[var4], p_i45181_3_);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final PacketBuffer data) throws IOException {
        this.HorizonCode_Horizon_È = new ChunkCoordIntPair(data.readInt(), data.readInt());
        this.Â = new HorizonCode_Horizon_È[data.Ø­áŒŠá()];
        for (int var2 = 0; var2 < this.Â.length; ++var2) {
            this.Â[var2] = new HorizonCode_Horizon_È(data.readShort(), (IBlockState)Block.Â.HorizonCode_Horizon_È(data.Ø­áŒŠá()));
        }
    }
    
    @Override
    public void Â(final PacketBuffer data) throws IOException {
        data.writeInt(this.HorizonCode_Horizon_È.HorizonCode_Horizon_È);
        data.writeInt(this.HorizonCode_Horizon_È.Â);
        data.Â(this.Â.length);
        for (final HorizonCode_Horizon_È var5 : this.Â) {
            data.writeShort(var5.Â());
            data.Â(Block.Â.HorizonCode_Horizon_È(var5.Ý()));
        }
    }
    
    public void HorizonCode_Horizon_È(final INetHandlerPlayClient p_180729_1_) {
        p_180729_1_.HorizonCode_Horizon_È(this);
    }
    
    public HorizonCode_Horizon_È[] HorizonCode_Horizon_È() {
        return this.Â;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final INetHandler handler) {
        this.HorizonCode_Horizon_È((INetHandlerPlayClient)handler);
    }
    
    public class HorizonCode_Horizon_È
    {
        private final short Â;
        private final IBlockState Ý;
        private static final String Ø­áŒŠá = "CL_00002302";
        
        public HorizonCode_Horizon_È(final short p_i45984_2_, final IBlockState p_i45984_3_) {
            this.Â = p_i45984_2_;
            this.Ý = p_i45984_3_;
        }
        
        public HorizonCode_Horizon_È(final short p_i45985_2_, final Chunk p_i45985_3_) {
            this.Â = p_i45985_2_;
            this.Ý = p_i45985_3_.Ø­áŒŠá(this.HorizonCode_Horizon_È());
        }
        
        public BlockPos HorizonCode_Horizon_È() {
            return new BlockPos(S22PacketMultiBlockChange.this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(this.Â >> 12 & 0xF, this.Â & 0xFF, this.Â >> 8 & 0xF));
        }
        
        public short Â() {
            return this.Â;
        }
        
        public IBlockState Ý() {
            return this.Ý;
        }
    }
}
