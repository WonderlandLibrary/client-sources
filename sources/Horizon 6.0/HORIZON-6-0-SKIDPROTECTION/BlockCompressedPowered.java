package HORIZON-6-0-SKIDPROTECTION;

public class BlockCompressedPowered extends BlockCompressed
{
    private static final String Õ = "CL_00000287";
    
    public BlockCompressedPowered(final MapColor p_i45416_1_) {
        super(p_i45416_1_);
        this.HorizonCode_Horizon_È(CreativeTabs.Ø­áŒŠá);
    }
    
    @Override
    public boolean áŒŠà() {
        return true;
    }
    
    @Override
    public int HorizonCode_Horizon_È(final IBlockAccess worldIn, final BlockPos pos, final IBlockState state, final EnumFacing side) {
        return 15;
    }
}
