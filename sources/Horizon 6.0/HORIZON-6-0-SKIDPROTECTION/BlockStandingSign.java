package HORIZON-6-0-SKIDPROTECTION;

public class BlockStandingSign extends BlockSign
{
    public static final PropertyInteger Õ;
    private static final String à¢ = "CL_00002060";
    
    static {
        Õ = PropertyInteger.HorizonCode_Horizon_È("rotation", 0, 15);
    }
    
    public BlockStandingSign() {
        this.Ø(this.á€.Â().HorizonCode_Horizon_È(BlockStandingSign.Õ, 0));
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final Block neighborBlock) {
        if (!worldIn.Â(pos.Âµá€()).Ý().Ó().Â()) {
            this.HorizonCode_Horizon_È(worldIn, pos, state, 0);
            worldIn.Ø(pos);
        }
        super.HorizonCode_Horizon_È(worldIn, pos, state, neighborBlock);
    }
    
    @Override
    public IBlockState Ý(final int meta) {
        return this.¥à().HorizonCode_Horizon_È(BlockStandingSign.Õ, meta);
    }
    
    @Override
    public int Ý(final IBlockState state) {
        return (int)state.HorizonCode_Horizon_È(BlockStandingSign.Õ);
    }
    
    @Override
    protected BlockState à¢() {
        return new BlockState(this, new IProperty[] { BlockStandingSign.Õ });
    }
}
