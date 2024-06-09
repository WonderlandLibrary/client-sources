package HORIZON-6-0-SKIDPROTECTION;

public class ColorizerFoliage
{
    private static int[] HorizonCode_Horizon_È;
    private static final String Â = "CL_00000135";
    
    static {
        ColorizerFoliage.HorizonCode_Horizon_È = new int[65536];
    }
    
    public static void HorizonCode_Horizon_È(final int[] p_77467_0_) {
        ColorizerFoliage.HorizonCode_Horizon_È = p_77467_0_;
    }
    
    public static int HorizonCode_Horizon_È(final double p_77470_0_, double p_77470_2_) {
        p_77470_2_ *= p_77470_0_;
        final int var4 = (int)((1.0 - p_77470_0_) * 255.0);
        final int var5 = (int)((1.0 - p_77470_2_) * 255.0);
        return ColorizerFoliage.HorizonCode_Horizon_È[var5 << 8 | var4];
    }
    
    public static int HorizonCode_Horizon_È() {
        return 6396257;
    }
    
    public static int Â() {
        return 8431445;
    }
    
    public static int Ý() {
        return 4764952;
    }
}
