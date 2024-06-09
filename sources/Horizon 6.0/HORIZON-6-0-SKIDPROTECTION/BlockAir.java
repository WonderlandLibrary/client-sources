package HORIZON-6-0-SKIDPROTECTION;

public class BlockAir extends Block
{
    private static final String Õ = "CL_00000190";
    
    protected BlockAir() {
        super(Material.HorizonCode_Horizon_È);
    }
    
    @Override
    public int ÂµÈ() {
        return -1;
    }
    
    @Override
    public AxisAlignedBB HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state) {
        return null;
    }
    
    @Override
    public boolean Å() {
        return false;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final IBlockState state, final boolean p_176209_2_) {
        return false;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final float chance, final int fortune) {
    }
}
