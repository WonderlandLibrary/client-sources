package HORIZON-6-0-SKIDPROTECTION;

public class BlockBreakable extends Block
{
    private boolean Õ;
    private static final String à¢ = "CL_00000254";
    
    protected BlockBreakable(final Material p_i45712_1_, final boolean p_i45712_2_) {
        super(p_i45712_1_);
        this.Õ = p_i45712_2_;
    }
    
    @Override
    public boolean Å() {
        return false;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final IBlockAccess worldIn, final BlockPos pos, final EnumFacing side) {
        final IBlockState var4 = worldIn.Â(pos);
        final Block var5 = var4.Ý();
        if (this == Blocks.Ï­Ðƒà || this == Blocks.ÐƒáŒŠÂµÐƒÕ) {
            if (worldIn.Â(pos.HorizonCode_Horizon_È(side.Âµá€())) != var4) {
                return true;
            }
            if (var5 == this) {
                return false;
            }
        }
        return (this.Õ || var5 != this) && super.HorizonCode_Horizon_È(worldIn, pos, side);
    }
}
