package HORIZON-6-0-SKIDPROTECTION;

public class BlockRail extends BlockRailBase
{
    public static final PropertyEnum Õ;
    private static final String ŠÂµà = "CL_00000293";
    
    static {
        Õ = PropertyEnum.HorizonCode_Horizon_È("shape", HorizonCode_Horizon_È.class);
    }
    
    protected BlockRail() {
        super(false);
        this.Ø(this.á€.Â().HorizonCode_Horizon_È(BlockRail.Õ, HorizonCode_Horizon_È.HorizonCode_Horizon_È));
    }
    
    @Override
    protected void Â(final World worldIn, final BlockPos p_176561_2_, final IBlockState p_176561_3_, final Block p_176561_4_) {
        if (p_176561_4_.áŒŠà() && new Â(worldIn, p_176561_2_, p_176561_3_).HorizonCode_Horizon_È() == 3) {
            this.HorizonCode_Horizon_È(worldIn, p_176561_2_, p_176561_3_, false);
        }
    }
    
    @Override
    public IProperty È() {
        return BlockRail.Õ;
    }
    
    @Override
    public IBlockState Ý(final int meta) {
        return this.¥à().HorizonCode_Horizon_È(BlockRail.Õ, HorizonCode_Horizon_È.HorizonCode_Horizon_È(meta));
    }
    
    @Override
    public int Ý(final IBlockState state) {
        return ((HorizonCode_Horizon_È)state.HorizonCode_Horizon_È(BlockRail.Õ)).Â();
    }
    
    @Override
    protected BlockState à¢() {
        return new BlockState(this, new IProperty[] { BlockRail.Õ });
    }
}
