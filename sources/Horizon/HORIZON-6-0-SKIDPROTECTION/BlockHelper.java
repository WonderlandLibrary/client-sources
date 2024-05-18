package HORIZON-6-0-SKIDPROTECTION;

import com.google.common.base.Predicate;

public class BlockHelper implements Predicate
{
    private final Block HorizonCode_Horizon_È;
    private static final String Â = "CL_00002020";
    
    private BlockHelper(final Block p_i45654_1_) {
        this.HorizonCode_Horizon_È = p_i45654_1_;
    }
    
    public static BlockHelper HorizonCode_Horizon_È(final Block p_177642_0_) {
        return new BlockHelper(p_177642_0_);
    }
    
    public boolean HorizonCode_Horizon_È(final IBlockState p_177643_1_) {
        return p_177643_1_ != null && p_177643_1_.Ý() == this.HorizonCode_Horizon_È;
    }
    
    public boolean apply(final Object p_apply_1_) {
        return this.HorizonCode_Horizon_È((IBlockState)p_apply_1_);
    }
}
