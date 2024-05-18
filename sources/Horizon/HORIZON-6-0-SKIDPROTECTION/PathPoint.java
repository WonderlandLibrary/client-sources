package HORIZON-6-0-SKIDPROTECTION;

public class PathPoint
{
    public final int HorizonCode_Horizon_È;
    public final int Â;
    public final int Ý;
    private final int áˆºÑ¢Õ;
    int Ø­áŒŠá;
    float Âµá€;
    float Ó;
    float à;
    PathPoint Ø;
    public boolean áŒŠÆ;
    private static final String ÂµÈ = "CL_00000574";
    
    public PathPoint(final int p_i2135_1_, final int p_i2135_2_, final int p_i2135_3_) {
        this.Ø­áŒŠá = -1;
        this.HorizonCode_Horizon_È = p_i2135_1_;
        this.Â = p_i2135_2_;
        this.Ý = p_i2135_3_;
        this.áˆºÑ¢Õ = HorizonCode_Horizon_È(p_i2135_1_, p_i2135_2_, p_i2135_3_);
    }
    
    public static int HorizonCode_Horizon_È(final int p_75830_0_, final int p_75830_1_, final int p_75830_2_) {
        return (p_75830_1_ & 0xFF) | (p_75830_0_ & 0x7FFF) << 8 | (p_75830_2_ & 0x7FFF) << 24 | ((p_75830_0_ < 0) ? Integer.MIN_VALUE : 0) | ((p_75830_2_ < 0) ? 32768 : 0);
    }
    
    public float HorizonCode_Horizon_È(final PathPoint p_75829_1_) {
        final float var2 = p_75829_1_.HorizonCode_Horizon_È - this.HorizonCode_Horizon_È;
        final float var3 = p_75829_1_.Â - this.Â;
        final float var4 = p_75829_1_.Ý - this.Ý;
        return MathHelper.Ý(var2 * var2 + var3 * var3 + var4 * var4);
    }
    
    public float Â(final PathPoint p_75832_1_) {
        final float var2 = p_75832_1_.HorizonCode_Horizon_È - this.HorizonCode_Horizon_È;
        final float var3 = p_75832_1_.Â - this.Â;
        final float var4 = p_75832_1_.Ý - this.Ý;
        return var2 * var2 + var3 * var3 + var4 * var4;
    }
    
    @Override
    public boolean equals(final Object p_equals_1_) {
        if (!(p_equals_1_ instanceof PathPoint)) {
            return false;
        }
        final PathPoint var2 = (PathPoint)p_equals_1_;
        return this.áˆºÑ¢Õ == var2.áˆºÑ¢Õ && this.HorizonCode_Horizon_È == var2.HorizonCode_Horizon_È && this.Â == var2.Â && this.Ý == var2.Ý;
    }
    
    @Override
    public int hashCode() {
        return this.áˆºÑ¢Õ;
    }
    
    public boolean HorizonCode_Horizon_È() {
        return this.Ø­áŒŠá >= 0;
    }
    
    @Override
    public String toString() {
        return String.valueOf(this.HorizonCode_Horizon_È) + ", " + this.Â + ", " + this.Ý;
    }
}
