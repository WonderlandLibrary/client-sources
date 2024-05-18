package HORIZON-6-0-SKIDPROTECTION;

public class SoundPoolEntry
{
    private final ResourceLocation_1975012498 HorizonCode_Horizon_È;
    private final boolean Â;
    private double Ý;
    private double Ø­áŒŠá;
    private static final String Âµá€ = "CL_00001140";
    
    public SoundPoolEntry(final ResourceLocation_1975012498 p_i45113_1_, final double p_i45113_2_, final double p_i45113_4_, final boolean p_i45113_6_) {
        this.HorizonCode_Horizon_È = p_i45113_1_;
        this.Ý = p_i45113_2_;
        this.Ø­áŒŠá = p_i45113_4_;
        this.Â = p_i45113_6_;
    }
    
    public SoundPoolEntry(final SoundPoolEntry p_i45114_1_) {
        this.HorizonCode_Horizon_È = p_i45114_1_.HorizonCode_Horizon_È;
        this.Ý = p_i45114_1_.Ý;
        this.Ø­áŒŠá = p_i45114_1_.Ø­áŒŠá;
        this.Â = p_i45114_1_.Â;
    }
    
    public ResourceLocation_1975012498 HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È;
    }
    
    public double Â() {
        return this.Ý;
    }
    
    public void HorizonCode_Horizon_È(final double p_148651_1_) {
        this.Ý = p_148651_1_;
    }
    
    public double Ý() {
        return this.Ø­áŒŠá;
    }
    
    public void Â(final double p_148647_1_) {
        this.Ø­áŒŠá = p_148647_1_;
    }
    
    public boolean Ø­áŒŠá() {
        return this.Â;
    }
}
