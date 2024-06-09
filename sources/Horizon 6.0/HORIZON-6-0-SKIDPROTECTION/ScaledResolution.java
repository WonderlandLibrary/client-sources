package HORIZON-6-0-SKIDPROTECTION;

public class ScaledResolution
{
    private final double HorizonCode_Horizon_È;
    private final double Â;
    private int Ý;
    private int Ø­áŒŠá;
    private int Âµá€;
    private static final String Ó = "CL_00000666";
    
    public ScaledResolution(final Minecraft mcIn, final int p_i46324_2_, final int p_i46324_3_) {
        this.Ý = p_i46324_2_;
        this.Ø­áŒŠá = p_i46324_3_;
        this.Âµá€ = 1;
        final boolean var4 = mcIn.Âµá€();
        int var5 = mcIn.ŠÄ.ŠÑ¢Ó;
        if (var5 == 0) {
            var5 = 1000;
        }
        while (this.Âµá€ < var5 && this.Ý / (this.Âµá€ + 1) >= 320 && this.Ø­áŒŠá / (this.Âµá€ + 1) >= 240) {
            ++this.Âµá€;
        }
        if (var4 && this.Âµá€ % 2 != 0 && this.Âµá€ != 1) {
            --this.Âµá€;
        }
        this.HorizonCode_Horizon_È = this.Ý / this.Âµá€;
        this.Â = this.Ø­áŒŠá / this.Âµá€;
        this.Ý = MathHelper.Ó(this.HorizonCode_Horizon_È);
        this.Ø­áŒŠá = MathHelper.Ó(this.Â);
    }
    
    public int HorizonCode_Horizon_È() {
        return this.Ý;
    }
    
    public int Â() {
        return this.Ø­áŒŠá;
    }
    
    public double Ý() {
        return this.HorizonCode_Horizon_È;
    }
    
    public double Ø­áŒŠá() {
        return this.Â;
    }
    
    public int Âµá€() {
        return this.Âµá€;
    }
}
