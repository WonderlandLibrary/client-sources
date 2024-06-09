package HORIZON-6-0-SKIDPROTECTION;

public class Language implements Comparable
{
    private final String HorizonCode_Horizon_È;
    private final String Â;
    private final String Ý;
    private final boolean Ø­áŒŠá;
    private static final String Âµá€ = "CL_00001095";
    
    public Language(final String p_i1303_1_, final String p_i1303_2_, final String p_i1303_3_, final boolean p_i1303_4_) {
        this.HorizonCode_Horizon_È = p_i1303_1_;
        this.Â = p_i1303_2_;
        this.Ý = p_i1303_3_;
        this.Ø­áŒŠá = p_i1303_4_;
    }
    
    public String HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È;
    }
    
    public boolean Â() {
        return this.Ø­áŒŠá;
    }
    
    @Override
    public String toString() {
        return String.format("%s (%s)", this.Ý, this.Â);
    }
    
    @Override
    public boolean equals(final Object p_equals_1_) {
        return this == p_equals_1_ || (p_equals_1_ instanceof Language && this.HorizonCode_Horizon_È.equals(((Language)p_equals_1_).HorizonCode_Horizon_È));
    }
    
    @Override
    public int hashCode() {
        return this.HorizonCode_Horizon_È.hashCode();
    }
    
    public int HorizonCode_Horizon_È(final Language p_compareTo_1_) {
        return this.HorizonCode_Horizon_È.compareTo(p_compareTo_1_.HorizonCode_Horizon_È);
    }
    
    @Override
    public int compareTo(final Object p_compareTo_1_) {
        return this.HorizonCode_Horizon_È((Language)p_compareTo_1_);
    }
}
