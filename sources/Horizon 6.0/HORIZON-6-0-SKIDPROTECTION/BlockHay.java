package HORIZON-6-0-SKIDPROTECTION;

public class BlockHay extends BlockRotatedPillar
{
    private static final String Õ = "CL_00000256";
    
    public BlockHay() {
        super(Material.Â);
        this.Ø(this.á€.Â().HorizonCode_Horizon_È(BlockHay.ŠÂµà, EnumFacing.HorizonCode_Horizon_È.Â));
        this.HorizonCode_Horizon_È(CreativeTabs.Â);
    }
    
    @Override
    public IBlockState Ý(final int meta) {
        EnumFacing.HorizonCode_Horizon_È var2 = EnumFacing.HorizonCode_Horizon_È.Â;
        final int var3 = meta & 0xC;
        if (var3 == 4) {
            var2 = EnumFacing.HorizonCode_Horizon_È.HorizonCode_Horizon_È;
        }
        else if (var3 == 8) {
            var2 = EnumFacing.HorizonCode_Horizon_È.Ý;
        }
        return this.¥à().HorizonCode_Horizon_È(BlockHay.ŠÂµà, var2);
    }
    
    @Override
    public int Ý(final IBlockState state) {
        int var2 = 0;
        final EnumFacing.HorizonCode_Horizon_È var3 = (EnumFacing.HorizonCode_Horizon_È)state.HorizonCode_Horizon_È(BlockHay.ŠÂµà);
        if (var3 == EnumFacing.HorizonCode_Horizon_È.HorizonCode_Horizon_È) {
            var2 |= 0x4;
        }
        else if (var3 == EnumFacing.HorizonCode_Horizon_È.Ý) {
            var2 |= 0x8;
        }
        return var2;
    }
    
    @Override
    protected BlockState à¢() {
        return new BlockState(this, new IProperty[] { BlockHay.ŠÂµà });
    }
    
    @Override
    protected ItemStack Ó(final IBlockState state) {
        return new ItemStack(Item_1028566121.HorizonCode_Horizon_È(this), 1, 0);
    }
    
    @Override
    public IBlockState HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final EnumFacing facing, final float hitX, final float hitY, final float hitZ, final int meta, final EntityLivingBase placer) {
        return super.HorizonCode_Horizon_È(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer).HorizonCode_Horizon_È(BlockHay.ŠÂµà, facing.á());
    }
}
