package HORIZON-6-0-SKIDPROTECTION;

public class BlockSoulSand extends Block
{
    private static final String Õ = "CL_00000310";
    
    public BlockSoulSand() {
        super(Material.£à);
        this.HorizonCode_Horizon_È(CreativeTabs.Â);
    }
    
    @Override
    public AxisAlignedBB HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state) {
        final float var4 = 0.125f;
        return new AxisAlignedBB(pos.HorizonCode_Horizon_È(), pos.Â(), pos.Ý(), pos.HorizonCode_Horizon_È() + 1, pos.Â() + 1 - var4, pos.Ý() + 1);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final Entity entityIn) {
        entityIn.ÇŽÉ *= 0.4;
        entityIn.ÇŽÕ *= 0.4;
    }
}
