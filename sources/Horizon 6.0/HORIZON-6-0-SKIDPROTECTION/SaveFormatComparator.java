package HORIZON-6-0-SKIDPROTECTION;

public class SaveFormatComparator implements Comparable
{
    private final String HorizonCode_Horizon_È;
    private final String Â;
    private final long Ý;
    private final long Ø­áŒŠá;
    private final boolean Âµá€;
    private final WorldSettings.HorizonCode_Horizon_È Ó;
    private final boolean à;
    private final boolean Ø;
    private static final String áŒŠÆ = "CL_00000601";
    
    public SaveFormatComparator(final String p_i2161_1_, final String p_i2161_2_, final long p_i2161_3_, final long p_i2161_5_, final WorldSettings.HorizonCode_Horizon_È p_i2161_7_, final boolean p_i2161_8_, final boolean p_i2161_9_, final boolean p_i2161_10_) {
        this.HorizonCode_Horizon_È = p_i2161_1_;
        this.Â = p_i2161_2_;
        this.Ý = p_i2161_3_;
        this.Ø­áŒŠá = p_i2161_5_;
        this.Ó = p_i2161_7_;
        this.Âµá€ = p_i2161_8_;
        this.à = p_i2161_9_;
        this.Ø = p_i2161_10_;
    }
    
    public String HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È;
    }
    
    public String Â() {
        return this.Â;
    }
    
    public long Ý() {
        return this.Ø­áŒŠá;
    }
    
    public boolean Ø­áŒŠá() {
        return this.Âµá€;
    }
    
    public long Âµá€() {
        return this.Ý;
    }
    
    public int HorizonCode_Horizon_È(final SaveFormatComparator p_compareTo_1_) {
        return (this.Ý < p_compareTo_1_.Ý) ? 1 : ((this.Ý > p_compareTo_1_.Ý) ? -1 : this.HorizonCode_Horizon_È.compareTo(p_compareTo_1_.HorizonCode_Horizon_È));
    }
    
    public WorldSettings.HorizonCode_Horizon_È Ó() {
        return this.Ó;
    }
    
    public boolean à() {
        return this.à;
    }
    
    public boolean Ø() {
        return this.Ø;
    }
    
    @Override
    public int compareTo(final Object p_compareTo_1_) {
        return this.HorizonCode_Horizon_È((SaveFormatComparator)p_compareTo_1_);
    }
}
