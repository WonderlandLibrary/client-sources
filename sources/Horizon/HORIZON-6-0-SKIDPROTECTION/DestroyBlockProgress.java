package HORIZON-6-0-SKIDPROTECTION;

public class DestroyBlockProgress
{
    private final int HorizonCode_Horizon_È;
    private final BlockPos Â;
    private int Ý;
    private int Ø­áŒŠá;
    private static final String Âµá€ = "CL_00001427";
    
    public DestroyBlockProgress(final int p_i45925_1_, final BlockPos p_i45925_2_) {
        this.HorizonCode_Horizon_È = p_i45925_1_;
        this.Â = p_i45925_2_;
    }
    
    public BlockPos HorizonCode_Horizon_È() {
        return this.Â;
    }
    
    public void HorizonCode_Horizon_È(int damage) {
        if (damage > 10) {
            damage = 10;
        }
        this.Ý = damage;
    }
    
    public int Â() {
        return this.Ý;
    }
    
    public void Â(final int p_82744_1_) {
        this.Ø­áŒŠá = p_82744_1_;
    }
    
    public int Ý() {
        return this.Ø­áŒŠá;
    }
}
