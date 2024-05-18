package HORIZON-6-0-SKIDPROTECTION;

public class ColorizerGrass
{
    private static int[] HorizonCode_Horizon_È;
    private static final String Â = "CL_00000138";
    
    static {
        ColorizerGrass.HorizonCode_Horizon_È = new int[65536];
    }
    
    public static void HorizonCode_Horizon_È(final int[] p_77479_0_) {
        ColorizerGrass.HorizonCode_Horizon_È = p_77479_0_;
    }
    
    public static int HorizonCode_Horizon_È(final double p_77480_0_, double p_77480_2_) {
        p_77480_2_ *= p_77480_0_;
        final int var4 = (int)((1.0 - p_77480_0_) * 255.0);
        final int var5 = (int)((1.0 - p_77480_2_) * 255.0);
        final int var6 = var5 << 8 | var4;
        return (var6 > ColorizerGrass.HorizonCode_Horizon_È.length) ? -65281 : ColorizerGrass.HorizonCode_Horizon_È[var6];
    }
}
