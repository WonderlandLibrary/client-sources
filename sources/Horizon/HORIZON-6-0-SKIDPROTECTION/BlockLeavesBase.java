package HORIZON-6-0-SKIDPROTECTION;

public class BlockLeavesBase extends Block
{
    protected boolean Ç;
    private static final String Õ = "CL_00000326";
    
    protected BlockLeavesBase(final Material p_i45433_1_, final boolean p_i45433_2_) {
        super(p_i45433_1_);
        this.Ç = p_i45433_2_;
    }
    
    @Override
    public boolean Å() {
        return false;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final IBlockAccess worldIn, final BlockPos pos, final EnumFacing side) {
        return (this.Ç || worldIn.Â(pos).Ý() != this) && super.HorizonCode_Horizon_È(worldIn, pos, side);
    }
}
