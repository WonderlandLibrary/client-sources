package HORIZON-6-0-SKIDPROTECTION;

public class AnimationFrame
{
    private final int HorizonCode_Horizon_È;
    private final int Â;
    private static final String Ý = "CL_00001104";
    
    public AnimationFrame(final int p_i1307_1_) {
        this(p_i1307_1_, -1);
    }
    
    public AnimationFrame(final int p_i1308_1_, final int p_i1308_2_) {
        this.HorizonCode_Horizon_È = p_i1308_1_;
        this.Â = p_i1308_2_;
    }
    
    public boolean HorizonCode_Horizon_È() {
        return this.Â == -1;
    }
    
    public int Â() {
        return this.Â;
    }
    
    public int Ý() {
        return this.HorizonCode_Horizon_È;
    }
}
