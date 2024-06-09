package HORIZON-6-0-SKIDPROTECTION;

import com.google.common.base.Predicate;

public abstract class BlockDirectional extends Block
{
    public static final PropertyDirection ŠÂµà;
    private static final String Õ = "CL_00000227";
    
    static {
        ŠÂµà = PropertyDirection.HorizonCode_Horizon_È("facing", (Predicate)EnumFacing.Ý.HorizonCode_Horizon_È);
    }
    
    protected BlockDirectional(final Material p_i45401_1_) {
        super(p_i45401_1_);
    }
}
