package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;
import java.util.Iterator;
import java.util.Collection;

public class WeightedRandom
{
    private static final String HorizonCode_Horizon_È = "CL_00001503";
    
    public static int HorizonCode_Horizon_È(final Collection p_76272_0_) {
        int var1 = 0;
        for (final HorizonCode_Horizon_È var3 : p_76272_0_) {
            var1 += var3.Ý;
        }
        return var1;
    }
    
    public static HorizonCode_Horizon_È HorizonCode_Horizon_È(final Random p_76273_0_, final Collection p_76273_1_, final int p_76273_2_) {
        if (p_76273_2_ <= 0) {
            throw new IllegalArgumentException();
        }
        final int var3 = p_76273_0_.nextInt(p_76273_2_);
        return HorizonCode_Horizon_È(p_76273_1_, var3);
    }
    
    public static HorizonCode_Horizon_È HorizonCode_Horizon_È(final Collection p_180166_0_, int p_180166_1_) {
        for (final HorizonCode_Horizon_È var3 : p_180166_0_) {
            p_180166_1_ -= var3.Ý;
            if (p_180166_1_ < 0) {
                return var3;
            }
        }
        return null;
    }
    
    public static HorizonCode_Horizon_È HorizonCode_Horizon_È(final Random p_76271_0_, final Collection p_76271_1_) {
        return HorizonCode_Horizon_È(p_76271_0_, p_76271_1_, HorizonCode_Horizon_È(p_76271_1_));
    }
    
    public static class HorizonCode_Horizon_È
    {
        protected int Ý;
        private static final String HorizonCode_Horizon_È = "CL_00001504";
        
        public HorizonCode_Horizon_È(final int p_i1556_1_) {
            this.Ý = p_i1556_1_;
        }
    }
}
