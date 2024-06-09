package HORIZON-6-0-SKIDPROTECTION;

import com.google.common.base.Predicate;

public class BlockWorldState
{
    private final World HorizonCode_Horizon_È;
    private final BlockPos Â;
    private IBlockState Ý;
    private TileEntity Ø­áŒŠá;
    private boolean Âµá€;
    private static final String Ó = "CL_00002026";
    
    public BlockWorldState(final World worldIn, final BlockPos p_i45659_2_) {
        this.HorizonCode_Horizon_È = worldIn;
        this.Â = p_i45659_2_;
    }
    
    public IBlockState HorizonCode_Horizon_È() {
        if (this.Ý == null && this.HorizonCode_Horizon_È.Ó(this.Â)) {
            this.Ý = this.HorizonCode_Horizon_È.Â(this.Â);
        }
        return this.Ý;
    }
    
    public TileEntity Â() {
        if (this.Ø­áŒŠá == null && !this.Âµá€) {
            this.Ø­áŒŠá = this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(this.Â);
            this.Âµá€ = true;
        }
        return this.Ø­áŒŠá;
    }
    
    public BlockPos Ý() {
        return this.Â;
    }
    
    public static Predicate HorizonCode_Horizon_È(final Predicate p_177510_0_) {
        return (Predicate)new Predicate() {
            private static final String HorizonCode_Horizon_È = "CL_00002025";
            
            public boolean HorizonCode_Horizon_È(final BlockWorldState p_177503_1_) {
                return p_177503_1_ != null && p_177510_0_.apply((Object)p_177503_1_.HorizonCode_Horizon_È());
            }
            
            public boolean apply(final Object p_apply_1_) {
                return this.HorizonCode_Horizon_È((BlockWorldState)p_apply_1_);
            }
        };
    }
}
