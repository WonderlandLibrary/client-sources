package HORIZON-6-0-SKIDPROTECTION;

public class Timer_356573597
{
    float HorizonCode_Horizon_È;
    private double Ó;
    public int Â;
    public float Ý;
    public float Ø­áŒŠá;
    public float Âµá€;
    private long à;
    private long Ø;
    private long áŒŠÆ;
    private double áˆºÑ¢Õ;
    private static final String ÂµÈ = "CL_00000658";
    
    public Timer_356573597(final float p_i1018_1_) {
        this.Ø­áŒŠá = 1.0f;
        this.áˆºÑ¢Õ = 1.0;
        this.HorizonCode_Horizon_È = p_i1018_1_;
        this.à = Minecraft.áƒ();
        this.Ø = System.nanoTime() / 1000000L;
    }
    
    public void HorizonCode_Horizon_È() {
        final long var1 = Minecraft.áƒ();
        final long var2 = var1 - this.à;
        final long var3 = System.nanoTime() / 1000000L;
        final double var4 = var3 / 1000.0;
        if (var2 <= 1000L && var2 >= 0L) {
            this.áŒŠÆ += var2;
            if (this.áŒŠÆ > 1000L) {
                final long var5 = var3 - this.Ø;
                final double var6 = this.áŒŠÆ / var5;
                this.áˆºÑ¢Õ += (var6 - this.áˆºÑ¢Õ) * 0.20000000298023224;
                this.Ø = var3;
                this.áŒŠÆ = 0L;
            }
            if (this.áŒŠÆ < 0L) {
                this.Ø = var3;
            }
        }
        else {
            this.Ó = var4;
        }
        this.à = var1;
        double var7 = (var4 - this.Ó) * this.áˆºÑ¢Õ;
        this.Ó = var4;
        var7 = MathHelper.HorizonCode_Horizon_È(var7, 0.0, 1.0);
        this.Âµá€ += (float)(var7 * this.Ø­áŒŠá * this.HorizonCode_Horizon_È);
        this.Â = (int)this.Âµá€;
        this.Âµá€ -= this.Â;
        if (this.Â > 10) {
            this.Â = 10;
        }
        this.Ý = this.Âµá€;
    }
}
