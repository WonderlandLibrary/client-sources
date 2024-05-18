package HORIZON-6-0-SKIDPROTECTION;

public class BlockEventData
{
    private BlockPos HorizonCode_Horizon_È;
    private Block Â;
    private int Ý;
    private int Ø­áŒŠá;
    private static final String Âµá€ = "CL_00000131";
    
    public BlockEventData(final BlockPos p_i45756_1_, final Block p_i45756_2_, final int p_i45756_3_, final int p_i45756_4_) {
        this.HorizonCode_Horizon_È = p_i45756_1_;
        this.Ý = p_i45756_3_;
        this.Ø­áŒŠá = p_i45756_4_;
        this.Â = p_i45756_2_;
    }
    
    public BlockPos HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È;
    }
    
    public int Â() {
        return this.Ý;
    }
    
    public int Ý() {
        return this.Ø­áŒŠá;
    }
    
    public Block Ø­áŒŠá() {
        return this.Â;
    }
    
    @Override
    public boolean equals(final Object p_equals_1_) {
        if (!(p_equals_1_ instanceof BlockEventData)) {
            return false;
        }
        final BlockEventData var2 = (BlockEventData)p_equals_1_;
        return this.HorizonCode_Horizon_È.equals(var2.HorizonCode_Horizon_È) && this.Ý == var2.Ý && this.Ø­áŒŠá == var2.Ø­áŒŠá && this.Â == var2.Â;
    }
    
    @Override
    public String toString() {
        return "TE(" + this.HorizonCode_Horizon_È + ")," + this.Ý + "," + this.Ø­áŒŠá + "," + this.Â;
    }
}
