package HORIZON-6-0-SKIDPROTECTION;

public class PathEntity
{
    private final PathPoint[] HorizonCode_Horizon_È;
    private int Â;
    private int Ý;
    private static final String Ø­áŒŠá = "CL_00000575";
    
    public PathEntity(final PathPoint[] p_i2136_1_) {
        this.HorizonCode_Horizon_È = p_i2136_1_;
        this.Ý = p_i2136_1_.length;
    }
    
    public void HorizonCode_Horizon_È() {
        ++this.Â;
    }
    
    public boolean Â() {
        return this.Â >= this.Ý;
    }
    
    public PathPoint Ý() {
        return (this.Ý > 0) ? this.HorizonCode_Horizon_È[this.Ý - 1] : null;
    }
    
    public PathPoint HorizonCode_Horizon_È(final int p_75877_1_) {
        return this.HorizonCode_Horizon_È[p_75877_1_];
    }
    
    public int Ø­áŒŠá() {
        return this.Ý;
    }
    
    public void Â(final int p_75871_1_) {
        this.Ý = p_75871_1_;
    }
    
    public int Âµá€() {
        return this.Â;
    }
    
    public void Ý(final int p_75872_1_) {
        this.Â = p_75872_1_;
    }
    
    public Vec3 HorizonCode_Horizon_È(final Entity p_75881_1_, final int p_75881_2_) {
        final double var3 = this.HorizonCode_Horizon_È[p_75881_2_].HorizonCode_Horizon_È + (int)(p_75881_1_.áŒŠ + 1.0f) * 0.5;
        final double var4 = this.HorizonCode_Horizon_È[p_75881_2_].Â;
        final double var5 = this.HorizonCode_Horizon_È[p_75881_2_].Ý + (int)(p_75881_1_.áŒŠ + 1.0f) * 0.5;
        return new Vec3(var3, var4, var5);
    }
    
    public Vec3 HorizonCode_Horizon_È(final Entity p_75878_1_) {
        return this.HorizonCode_Horizon_È(p_75878_1_, this.Â);
    }
    
    public boolean HorizonCode_Horizon_È(final PathEntity p_75876_1_) {
        if (p_75876_1_ == null) {
            return false;
        }
        if (p_75876_1_.HorizonCode_Horizon_È.length != this.HorizonCode_Horizon_È.length) {
            return false;
        }
        for (int var2 = 0; var2 < this.HorizonCode_Horizon_È.length; ++var2) {
            if (this.HorizonCode_Horizon_È[var2].HorizonCode_Horizon_È != p_75876_1_.HorizonCode_Horizon_È[var2].HorizonCode_Horizon_È || this.HorizonCode_Horizon_È[var2].Â != p_75876_1_.HorizonCode_Horizon_È[var2].Â || this.HorizonCode_Horizon_È[var2].Ý != p_75876_1_.HorizonCode_Horizon_È[var2].Ý) {
                return false;
            }
        }
        return true;
    }
    
    public boolean HorizonCode_Horizon_È(final Vec3 p_75880_1_) {
        final PathPoint var2 = this.Ý();
        return var2 != null && (var2.HorizonCode_Horizon_È == (int)p_75880_1_.HorizonCode_Horizon_È && var2.Ý == (int)p_75880_1_.Ý);
    }
}
