package HORIZON-6-0-SKIDPROTECTION;

public abstract class BlockContainer extends Block implements ITileEntityProvider
{
    private static final String Õ = "CL_00000193";
    
    protected BlockContainer(final Material materialIn) {
        super(materialIn);
        this.áŒŠà = true;
    }
    
    @Override
    public int ÂµÈ() {
        return -1;
    }
    
    @Override
    public void Ø­áŒŠá(final World worldIn, final BlockPos pos, final IBlockState state) {
        super.Ø­áŒŠá(worldIn, pos, state);
        worldIn.¥Æ(pos);
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final int eventID, final int eventParam) {
        super.HorizonCode_Horizon_È(worldIn, pos, state, eventID, eventParam);
        final TileEntity var6 = worldIn.HorizonCode_Horizon_È(pos);
        return var6 != null && var6.Ý(eventID, eventParam);
    }
}
