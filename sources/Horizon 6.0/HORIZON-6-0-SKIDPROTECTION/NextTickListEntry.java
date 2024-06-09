package HORIZON-6-0-SKIDPROTECTION;

public class NextTickListEntry implements Comparable
{
    private static long Ø­áŒŠá;
    private final Block Âµá€;
    public final BlockPos HorizonCode_Horizon_È;
    public long Â;
    public int Ý;
    private long Ó;
    private static final String à = "CL_00000156";
    
    public NextTickListEntry(final BlockPos p_i45745_1_, final Block p_i45745_2_) {
        this.Ó = NextTickListEntry.Ø­áŒŠá++;
        this.HorizonCode_Horizon_È = p_i45745_1_;
        this.Âµá€ = p_i45745_2_;
    }
    
    @Override
    public boolean equals(final Object p_equals_1_) {
        if (!(p_equals_1_ instanceof NextTickListEntry)) {
            return false;
        }
        final NextTickListEntry var2 = (NextTickListEntry)p_equals_1_;
        return this.HorizonCode_Horizon_È.equals(var2.HorizonCode_Horizon_È) && Block.HorizonCode_Horizon_È(this.Âµá€, var2.Âµá€);
    }
    
    @Override
    public int hashCode() {
        return this.HorizonCode_Horizon_È.hashCode();
    }
    
    public NextTickListEntry HorizonCode_Horizon_È(final long p_77176_1_) {
        this.Â = p_77176_1_;
        return this;
    }
    
    public void HorizonCode_Horizon_È(final int p_82753_1_) {
        this.Ý = p_82753_1_;
    }
    
    public int HorizonCode_Horizon_È(final NextTickListEntry p_compareTo_1_) {
        return (this.Â < p_compareTo_1_.Â) ? -1 : ((this.Â > p_compareTo_1_.Â) ? 1 : ((this.Ý != p_compareTo_1_.Ý) ? (this.Ý - p_compareTo_1_.Ý) : ((this.Ó < p_compareTo_1_.Ó) ? -1 : ((this.Ó > p_compareTo_1_.Ó) ? 1 : 0))));
    }
    
    @Override
    public String toString() {
        return String.valueOf(Block.HorizonCode_Horizon_È(this.Âµá€)) + ": " + this.HorizonCode_Horizon_È + ", " + this.Â + ", " + this.Ý + ", " + this.Ó;
    }
    
    public Block HorizonCode_Horizon_È() {
        return this.Âµá€;
    }
    
    @Override
    public int compareTo(final Object p_compareTo_1_) {
        return this.HorizonCode_Horizon_È((NextTickListEntry)p_compareTo_1_);
    }
}
